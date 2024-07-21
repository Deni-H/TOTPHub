package com.denihilhamsyah.totphub.qr.domain.error

import com.google.mlkit.common.MlKitException

fun Throwable.toScanQrError(): ScanQrError.ScanError {
    return when(this) {
        is NoSuchElementException -> ScanQrError.ScanError.NO_RESULT
        is MlKitException -> when(errorCode) {
            MlKitException.CODE_SCANNER_UNAVAILABLE -> ScanQrError.ScanError.MODULE_NOT_INSTALLED
            else -> ScanQrError.ScanError.UNKNOWN_ERROR
        }
        else -> ScanQrError.ScanError.UNKNOWN_ERROR
    }
}