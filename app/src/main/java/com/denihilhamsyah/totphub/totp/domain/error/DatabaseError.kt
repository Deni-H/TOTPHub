package com.denihilhamsyah.totphub.totp.domain.error


sealed interface DatabaseError: Error {
    enum class Local: DatabaseError {
        ALREADY_EXISTS,
        NOT_FOUND,
        UNKNOWN_ERROR
    }
}