package com.denihilhamsyah.totphub.totp.data

import com.denihilhamsyah.totphub.totp.domain.error.DatabaseError

fun Throwable.toDatabaseError(): DatabaseError.Local {
    return when(this) {
        is NoSuchElementException -> DatabaseError.Local.NOT_FOUND
        is IllegalArgumentException -> DatabaseError.Local.ALREADY_EXISTS
        else -> DatabaseError.Local.UNKNOWN_ERROR
    }
}