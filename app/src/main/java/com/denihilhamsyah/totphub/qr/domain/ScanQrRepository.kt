package com.denihilhamsyah.totphub.qr.domain

import kotlinx.coroutines.flow.Flow

interface ScanQrRepository {

    val installModuleState: Flow<InstallModuleState>

    suspend fun scanQr(): String

    suspend fun isModuleInstalled(): Boolean

    suspend fun installModule()
}

data class InstallModuleState(
    val progress: Long = 0L,
    val downloadState: DownloadState
)

enum class DownloadState(val stateCode: Int) {
    STATE_CANCELED(0),
    STATE_COMPLETED(1),
    STATE_DOWNLOADING(2),
    STATE_DOWNLOAD_PAUSED(3),
    STATE_FAILED(4),
    STATE_INSTALLING(5),
    STATE_PENDING(6),
    STATE_UNKNOWN(7)
}