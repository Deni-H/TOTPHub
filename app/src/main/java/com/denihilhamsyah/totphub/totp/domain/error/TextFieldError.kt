package com.denihilhamsyah.totphub.totp.domain.error

sealed interface TextFieldError: Error {
    enum class Secret: TextFieldError {
        EMPTY,
        TO_SHORT,
        INVALID_CHARACTERS
    }
}