package com.denihilhamsyah.totphub.totp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.qr.domain.InstallModuleState
import com.denihilhamsyah.totphub.qr.domain.ScanQrRepository
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import com.denihilhamsyah.totphub.totp.domain.repository.DatabaseRepository
import com.denihilhamsyah.totphub.totp.domain.repository.TOTPRepository
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidAccountNameUseCase
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidSecretLabelUseCase
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidSecretUseCase
import com.denihilhamsyah.totphub.totp.domain.use_case.ParseTOTPQrUseCase
import com.denihilhamsyah.totphub.totp.domain.util.Result
import com.denihilhamsyah.totphub.totp.presentation.component.text_field.TextFieldState
import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.UiText
import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TOTPViewModel @Inject constructor(
    private val totpRepository: TOTPRepository,
    private val databaseRepository: DatabaseRepository,
    private val scanQrRepository: ScanQrRepository,
    private val isValidSecretUseCase: IsValidSecretUseCase,
    private val isValidSecretLabelUseCase: IsValidSecretLabelUseCase,
    private val isValidAccountNameUseCase: IsValidAccountNameUseCase,
    private val parseTOTPQrUseCase: ParseTOTPQrUseCase
) : ViewModel() {

    private val totpEventChannel = Channel<TOTPEvent>()
    val totpEvent = totpEventChannel.receiveAsFlow()

    private val _totpState = MutableStateFlow(TOTPState())
    val totpState = _totpState.asStateFlow()

    val secrets = databaseRepository.secrets(
        onChange = ::onSecretChange
    ).cachedIn(viewModelScope)

    private val _totpCodes = MutableStateFlow<MutableMap<String, String>>(mutableMapOf())
    val totpCodes = _totpCodes.asStateFlow()

    private val _secretFieldState = MutableStateFlow(TextFieldState())
    val secretFieldState = _secretFieldState.asStateFlow()

    private val _secretLabelFieldState = MutableStateFlow(TextFieldState())
    val secretLabelFieldState = _secretLabelFieldState.asStateFlow()

    private val _accountNameFieldState = MutableStateFlow(TextFieldState())
    val accountNameFieldState = _accountNameFieldState.asStateFlow()

    private val _remainingCountDown = MutableStateFlow(0L)
    val remainingCountDown = _remainingCountDown.asStateFlow()

    val installModuleState = scanQrRepository.installModuleState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = InstallModuleState()
    )

    init {
        startClock()
    }

    fun scanQr() {
        viewModelScope.launch {
            val isModuleInstalled = scanQrRepository.isModuleInstalled()
            if (isModuleInstalled) {
                scanQrRepository.scanQr()
                    .onRight {
                        when (val result = parseTOTPQrUseCase(it)) {
                            is Result.Error -> {
                                val errorMessage = result.error.asUiText()
                                totpEventChannel.send(TOTPEvent.OnOperationFailed(errorMessage))
                            }
                            is Result.Success -> databaseRepository.addSecret(result.data)
                        }
                    }
                    .onLeft {
                        val errorMessage = it.asUiText()
                        totpEventChannel.send(TOTPEvent.OnOperationFailed(errorMessage))
                    }
            } else {
                totpEventChannel.send(TOTPEvent.OnDownloadingModule)
                scanQrRepository.installModule()
                    .onRight { totpEventChannel.send(TOTPEvent.OnModuleInstalled(UiText.StringResource(R.string.module_installed))) }
                    .onLeft {
                        val errorMessage = it.asUiText()
                        totpEventChannel.send(TOTPEvent.OnInstallModuleFailed(errorMessage))
                    }
            }
        }
    }

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

    private fun clearAddDialogFieldState() {
        _accountNameFieldState.value = TextFieldState()
        _secretLabelFieldState.value = TextFieldState()
        _secretFieldState.value = TextFieldState()
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
            clearAddDialogFieldState()
            _totpState.value = totpState.value.copy(isLoading = false)
        }
    }

    private fun onSecretChange(secretDetails: SecretDetails) {
        _totpCodes.update { currentTotpCodes ->
            val totp = totpRepository.generateTOTP(secretDetails.secret, createCounter())
            currentTotpCodes.apply {
                this[secretDetails.secret] = totp
            }
        }
    }

    private fun startClock() {
        viewModelScope.launch {
            while (true) {
                val currentTimeMillis = System.currentTimeMillis()
                val millisUntilNextInterval = TIME_STEP_MILLISECONDS - (currentTimeMillis % TIME_STEP_MILLISECONDS)

                // Generate new TOTP code at the start of each interval
                if (millisUntilNextInterval > _remainingCountDown.value) generateTOTP()

                // Update the remaining countdown
                _remainingCountDown.value = millisUntilNextInterval
                delay(TEN_MILLISECOND)
            }
        }
    }

    private fun generateTOTP() {
        val currentTotpCodes = _totpCodes.value
        currentTotpCodes.keys.forEach { secret ->
            val totp = totpRepository.generateTOTP(secret, createCounter())
            currentTotpCodes[secret] = totp
        }
        _totpCodes.value = currentTotpCodes
    }

    private fun createCounter() = System.currentTimeMillis() / TIME_STEP_MILLISECONDS

    companion object {
        private const val TEN_MILLISECOND = 10L
        private const val TIME_STEP_SECONDS = 30L
        private const val TIME_STEP_MILLISECONDS = TIME_STEP_SECONDS * 1000L
    }
}