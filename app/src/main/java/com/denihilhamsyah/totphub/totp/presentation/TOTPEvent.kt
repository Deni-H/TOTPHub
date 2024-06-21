package com.denihilhamsyah.totphub.totp.presentation

sealed interface TOTPEvent {
    data class OnSecretAdded(val uiText: UiText) : TOTPEvent
    data class OnOperationFailed(val uiText: UiText) : TOTPEvent
}