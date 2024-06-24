package com.denihilhamsyah.totphub.totp.presentation

import com.denihilhamsyah.totphub.totp.presentation.component.UiText

sealed interface TOTPEvent {
    data class OnSecretAdded(val uiText: UiText) : TOTPEvent
    data class OnOperationFailed(val uiText: UiText) : TOTPEvent
}