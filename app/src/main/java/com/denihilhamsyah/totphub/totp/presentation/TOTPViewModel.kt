package com.denihilhamsyah.totphub.totp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import com.denihilhamsyah.totphub.totp.domain.repository.DatabaseRepository
import com.denihilhamsyah.totphub.totp.domain.repository.TOTPRepository
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidAccountNameUseCase
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidSecretLabelUseCase
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidSecretUseCase
import com.denihilhamsyah.totphub.totp.domain.util.Result
import com.denihilhamsyah.totphub.totp.presentation.component.text_field.TextFieldState
import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.UiText
import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TOTPViewModel @Inject constructor(
    private val totpRepository: TOTPRepository,
    private val databaseRepository: DatabaseRepository,
    private val isValidSecretUseCase: IsValidSecretUseCase,
    private val isValidSecretLabelUseCase: IsValidSecretLabelUseCase,
    private val isValidAccountNameUseCase: IsValidAccountNameUseCase
) : ViewModel() {

    private val totpEventChannel = Channel<TOTPEvent>()
    val totpEvent = totpEventChannel.receiveAsFlow()

    private val _totpState = MutableStateFlow(TOTPState())
    val totpState = _totpState.asStateFlow()

    val secrets = databaseRepository.secrets.cachedIn(viewModelScope)

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

    fun onSecretLabelFieldChange(secretLabel: String) {
        when (val result = isValidSecretLabelUseCase(secretLabel)) {
            is Result.Error -> _secretLabelFieldState.value = secretLabelFieldState.value.copy(error = result.error.asUiText())
            is Result.Success -> _secretLabelFieldState.value = secretLabelFieldState.value.copy(error = null)
        }
        _secretLabelFieldState.value = secretLabelFieldState.value.copy(text = secretLabel)
    }

    fun onAccountNameFieldChange(accountName: String) {
        when (val result = isValidAccountNameUseCase(accountName)) {
            is Result.Error -> _accountNameFieldState.value = accountNameFieldState.value.copy(error = result.error.asUiText())
            is Result.Success -> _accountNameFieldState.value = accountNameFieldState.value.copy(error = null)
        }
        _accountNameFieldState.value = accountNameFieldState.value.copy(text = accountName)
    }

    fun onAddSecretDialogClick() {
        onSecretFieldChange(secretFieldState.value.text)
        onSecretLabelFieldChange(secretLabelFieldState.value.text)
        onAccountNameFieldChange(accountNameFieldState.value.text)

        if (
            secretFieldState.value.error == null &&
            secretLabelFieldState.value.error == null &&
            accountNameFieldState.value.error == null
        ) addSecret()
    }

    private fun addSecret() {
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