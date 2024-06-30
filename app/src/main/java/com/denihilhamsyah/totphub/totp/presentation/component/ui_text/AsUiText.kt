package com.denihilhamsyah.totphub.totp.presentation.component.ui_text

import com.denihilhamsyah.totphub.R
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