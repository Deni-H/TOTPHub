package com.denihilhamsyah.totphub.code.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denihilhamsyah.totphub.code.domain.repository.TOTPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
    private val totpRepository: TOTPRepository
) : ViewModel() {

    private val _totpSecret = MutableStateFlow<String?>(null)
    val totpSecret = _totpSecret.asStateFlow()

    private val _totpCode = MutableStateFlow<String?>(null)
    val totpCode = _totpCode.asStateFlow()

    private val _remainingTimeStep = MutableStateFlow(0L)
    val remainingTimeStep = _remainingTimeStep.asStateFlow()

    init {
        startGenerateTOTPCode()
    }

    fun onTOTPSecretChanged(secret: String) {
        _totpSecret.value = secret
        generateTOTPCode()
    }

    private fun startGenerateTOTPCode() {
        viewModelScope.launch {
            while (true) {
                val currentTimeMillis = System.currentTimeMillis()
                val millisUntilNextInterval = TIME_STEP_MILLISECONDS - (currentTimeMillis % TIME_STEP_MILLISECONDS)

                // Generate new TOTP code at the start of each interval
                if (millisUntilNextInterval > _remainingTimeStep.value) {
                    generateTOTPCode()
                }

                // Update the remaining time step
                _remainingTimeStep.value = millisUntilNextInterval
                delay(ONE_MILLISECOND)
            }
        }
    }

    private fun generateTOTPCode() {
        _totpSecret.value?.let { secret ->
            val counter = System.currentTimeMillis() / TIME_STEP_MILLISECONDS
            try {
                _totpCode.value = totpRepository.generateTOTP(secret, counter)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val TAG = "CodeViewModel"
        private const val ONE_MILLISECOND = 10L
        private const val TIME_STEP_SECONDS = 30L
        private const val TIME_STEP_MILLISECONDS = TIME_STEP_SECONDS * 1000L
    }
}