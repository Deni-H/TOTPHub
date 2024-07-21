package com.denihilhamsyah.totphub.totp.domain.error

import com.denihilhamsyah.totphub.core.domain.error.Error

sealed interface ParseQrError: Error {
    enum class TOTPError: ParseQrError {
        SECRET_NOT_FOUND,
        SECRET_LABEL_NOT_FOUND,
        ACCOUNT_NAME_NOT_FOUND,
        INVALID_FORMAT,
        UNKNOWN_ERROR
    }
}