package com.denihilhamsyah.totphub.qr.domain.error

import com.denihilhamsyah.totphub.core.domain.error.Error

sealed interface ScanQrError: Error {
    enum class InstallModuleError: ScanQrError {
        NETWORK_FAILURE,
        INSUFFICIENT_STORAGE,
        INVALID_MODULE,
        INSTALLATION_TIMEOUT,
        UNKNOWN_ERROR
    }
    enum class ScanError: ScanQrError {
        NO_RESULT,
        MODULE_NOT_INSTALLED,
        UNKNOWN_ERROR
    }
}