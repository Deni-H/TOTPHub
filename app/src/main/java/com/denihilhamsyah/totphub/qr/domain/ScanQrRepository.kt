package com.denihilhamsyah.totphub.qr.domain

import arrow.core.Either
import com.denihilhamsyah.totphub.qr.domain.error.ScanQrError
import kotlinx.coroutines.flow.Flow

interface ScanQrRepository {

    val installModuleState: Flow<InstallModuleState>

    suspend fun scanQr(): Either<ScanQrError.ScanError, String>

    suspend fun isModuleInstalled(): Boolean

    suspend fun installModule(): Either<ScanQrError.InstallModuleError, Unit>
}

data class InstallModuleState(
    val downloadProgress: DownloadProgress = DownloadProgress(),
    val downloadState: DownloadState = DownloadState.UNKNOWN
)

data class DownloadProgress(
    val totalBytesToDownload: Long = 0L,
    val bytesDownloaded: Long = 0L
)

enum class DownloadState(val value: Int) {
    CANCELED(3),
    COMPLETED(4),
    DOWNLOADING(2),
    DOWNLOAD_PAUSED(7),
    FAILED(5),
    INSTALLING(6),
    PENDING(1),
    UNKNOWN(0);

    companion object {
        fun from(value: Int): DownloadState = entries.first { it.value == value }
    }
}