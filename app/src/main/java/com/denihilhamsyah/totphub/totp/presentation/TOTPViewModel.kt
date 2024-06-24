package com.denihilhamsyah.totphub.totp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import com.denihilhamsyah.totphub.totp.domain.repository.DatabaseRepository
import com.denihilhamsyah.totphub.totp.domain.repository.TOTPRepository
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidSecretUseCase
import com.denihilhamsyah.totphub.totp.domain.util.Result
import com.denihilhamsyah.totphub.totp.presentation.component.text_field.TextFieldState
import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.UiText
import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TOTPViewModel @Inject constructor(
    private val totpRepository: TOTPRepository,
    private val databaseRepository: DatabaseRepository,
    private val isValidSecretUseCase: IsValidSecretUseCase
) : ViewModel() {

    private val totpEventChannel = Channel<TOTPEvent>()
    val totpEvent = totpEventChannel.receiveAsFlow()

    private val _totpState = MutableStateFlow(TOTPState())
    val totpState = _totpState.asStateFlow()

    val secrets = databaseRepository.secrets.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    private val _secretFieldState = MutableStateFlow(TextFieldState())
    val secretFieldState = _secretFieldState.asStateFlow()

    private val _secretLabelFieldState = MutableStateFlow(TextFieldState())
    val secretLabelFieldState = _secretLabelFieldState.asStateFlow()

    private val _accountNameFieldState = MutableStateFlow(TextFieldState())
    val accountNameFieldState = _accountNameFieldState.asStateFlow()

    fun onSecretFieldChange(secret: String) {
        when (val result = isValidSecretUseCase(secret)) {
            is Result.Error -> _secretFieldState.value = secretFieldState.value.copy(error = result.error.asUiText())
            is Result.Success -> _secretFieldState.value = secretFieldState.value.copy(error = null)
        }
        _secretFieldState.value = secretFieldState.value.copy(text = secret)
    }

    fun addSecret() {
        viewModelScope.launch {
            _totpState.value = totpState.value.copy(isLoading = true)
            databaseRepository.addSecret(SecretDetails(
                secret = secretFieldState.value.text,
                secretLabel = secretLabelFieldState.value.text,
                accountName = accountNameFieldState.value.text
            ))
                .onRight {
                    val successMessage = UiText.StringResource(R.string.secret_added)
                    totpEventChannel.send(TOTPEvent.OnSecretAdded(successMessage))
                }
                .onLeft {
                    val errorMessage = it.asUiText()
                    totpEventChannel.send(TOTPEvent.OnOperationFailed(errorMessage))
                }
            _totpState.value = totpState.value.copy(isLoading = false)
        }
    }
}