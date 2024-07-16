package com.denihilhamsyah.totphub.totp.presentation.component.ui_text

import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.qr.domain.DownloadState
import com.denihilhamsyah.totphub.totp.domain.error.DatabaseError
import com.denihilhamsyah.totphub.totp.domain.error.TextFieldError

fun DatabaseError.asUiText(): UiText {
    return when (this) {
        DatabaseError.Local.ALREADY_EXISTS -> UiText.StringResource(R.string.secret_already_exists)
        DatabaseError.Local.NOT_FOUND -> UiText.StringResource(R.string.secret_not_found)
        DatabaseError.Local.UNKNOWN_ERROR -> UiText.StringResource(R.string.an_error_occurred)
    }
}

fun TextFieldError.asUiText(): UiText {
    return when (this) {
        TextFieldError.Secret.EMPTY -> UiText.StringResource(R.string.cannot_be_empty)
        TextFieldError.Secret.TO_SHORT -> UiText.StringResource(R.string.too_short)
        TextFieldError.Secret.INVALID_CHARACTERS -> UiText.StringResource(R.string.invalid_characters)
        TextFieldError.AccountName.EMPTY -> UiText.StringResource(R.string.cannot_be_empty)
        TextFieldError.SecretLabel.EMPTY -> UiText.StringResource(R.string.cannot_be_empty)
    }
}

fun DownloadState.asUiText(): UiText {
    return when(this) {
        DownloadState.CANCELED -> UiText.StringResource(R.string.canceled)
        DownloadState.COMPLETED -> UiText.StringResource(R.string.completed)
        DownloadState.DOWNLOADING -> UiText.StringResource(R.string.downloading)
        DownloadState.DOWNLOAD_PAUSED -> UiText.StringResource(R.string.download_paused)
        DownloadState.FAILED -> UiText.StringResource(R.string.failed)
        DownloadState.INSTALLING -> UiText.StringResource(R.string.installing)
        DownloadState.PENDING -> UiText.StringResource(R.string.pending)
        DownloadState.UNKNOWN -> UiText.StringResource(R.string.unknown)
    }
}