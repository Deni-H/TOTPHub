package com.denihilhamsyah.totphub.totp.presentation

import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.UiText

sealed interface TOTPEvent {
    data class OnSecretAdded(val uiText: UiText) : TOTPEvent
    data class OnModuleInstalled(val uiText: UiText) : TOTPEvent
    data object OnDownloadingModule : TOTPEvent
    data class OnInstallModuleFailed(val uiText: UiText) : TOTPEvent
    data class OnOperationFailed(val uiText: UiText) : TOTPEvent
}