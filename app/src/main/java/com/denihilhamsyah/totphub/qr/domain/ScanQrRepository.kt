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
    val progress: Long = 0L,
    val downloadState: DownloadState = DownloadState.STATE_UNKNOWN
)

enum class DownloadState(val value: Int) {
    STATE_CANCELED(3),
    STATE_COMPLETED(4),
    STATE_DOWNLOADING(2),
    STATE_DOWNLOAD_PAUSED(7),
    STATE_FAILED(5),
    STATE_INSTALLING(6),
    STATE_PENDING(1),
    STATE_UNKNOWN(0);

    companion object {
        fun from(value: Int): DownloadState = entries.first { it.value == value }
    }
}