package com.denihilhamsyah.totphub.totp.common.error

enum class DatabaseError {
    ALREADY_EXISTS,
    NOT_FOUND,
    UNKNOWN_ERROR;
}

fun Throwable.toDatabaseError(): DatabaseError {
    return when(this) {
        is NoSuchElementException -> DatabaseError.NOT_FOUND
        is IllegalArgumentException -> DatabaseError.ALREADY_EXISTS
        else -> DatabaseError.UNKNOWN_ERROR
    }
}