package com.denihilhamsyah.totphub.totp.domain.error

import com.denihilhamsyah.totphub.core.domain.error.Error


sealed interface DatabaseError: Error {
    enum class Local: DatabaseError {
        ALREADY_EXISTS,
        NOT_FOUND,
        UNKNOWN_ERROR
    }
}