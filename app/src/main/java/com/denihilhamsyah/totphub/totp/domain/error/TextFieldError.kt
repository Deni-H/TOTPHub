package com.denihilhamsyah.totphub.totp.domain.error

sealed interface TextFieldError: Error {
    enum class Secret: TextFieldError {
        EMPTY,
        TO_SHORT,
        INVALID_CHARACTERS
    }
    enum class SecretLabel: TextFieldError {
        EMPTY
    }
    enum class AccountName: TextFieldError {
        EMPTY
    }
}