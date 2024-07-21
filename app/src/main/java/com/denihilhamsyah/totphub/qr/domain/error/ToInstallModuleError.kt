package com.denihilhamsyah.totphub.qr.domain.error

import com.google.mlkit.common.MlKitException


fun Throwable.toInstallModuleError(): ScanQrError.InstallModuleError {
    return when(this) {
        is MlKitException -> when(errorCode) {
            MlKitException.NETWORK_ISSUE -> ScanQrError.InstallModuleError.NETWORK_FAILURE
            MlKitException.CANCELLED -> ScanQrError.InstallModuleError.INSUFFICIENT_STORAGE
            MlKitException.CODE_SCANNER_UNAVAILABLE -> ScanQrError.InstallModuleError.INVALID_MODULE
            MlKitException.INTERNAL -> ScanQrError.InstallModuleError.INSTALLATION_TIMEOUT
            else -> ScanQrError.InstallModuleError.UNKNOWN_ERROR
        }
        else -> ScanQrError.InstallModuleError.UNKNOWN_ERROR
    }
}