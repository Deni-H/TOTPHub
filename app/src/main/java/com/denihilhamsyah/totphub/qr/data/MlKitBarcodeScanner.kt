package com.denihilhamsyah.totphub.qr.data

import arrow.core.Either
import com.denihilhamsyah.totphub.qr.domain.DownloadProgress
import com.denihilhamsyah.totphub.qr.domain.DownloadState
import com.denihilhamsyah.totphub.qr.domain.InstallModuleState
import com.denihilhamsyah.totphub.qr.domain.ScanQrRepository
import com.denihilhamsyah.totphub.qr.domain.error.ScanQrError
import com.denihilhamsyah.totphub.qr.domain.error.toInstallModuleError
import com.denihilhamsyah.totphub.qr.domain.error.toScanQrError
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_CANCELED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_COMPLETED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_FAILED
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class MlKitBarcodeScanner(
    private val scanner: GmsBarcodeScanner,
    private val installClient: ModuleInstallClient,
): ScanQrRepository {

    private val listener = ModuleInstallProgressListener()

    private val _installModuleState = MutableStateFlow(InstallModuleState())
    override val installModuleState: Flow<InstallModuleState>
        get() = _installModuleState.asStateFlow()

    override suspend fun scanQr(): Either<ScanQrError.ScanError, String> {
        return Either.catch {
            val barcode = scanner.startScan().await()
            barcode.rawValue ?: throw NoSuchElementException()
        }.mapLeft {
            it.printStackTrace()
            it.toScanQrError()
        }
    }

    override suspend fun isModuleInstalled(): Boolean {
        val moduleAvailability = installClient
            .areModulesAvailable(scanner)
            .await()

        return moduleAvailability.areModulesAvailable()
    }

    override suspend fun installModule(): Either<ScanQrError.InstallModuleError, Unit> {
        return Either.catch {
            val moduleInstallRequest = ModuleInstallRequest
                .newBuilder()
                .addApi(scanner)
                .setListener(listener)
                .build()

            installClient
                .installModules(moduleInstallRequest)
                .await()

            return@catch
        }.mapLeft {
            it.printStackTrace()
            it.toInstallModuleError()
        }
    }

    inner class ModuleInstallProgressListener : InstallStatusListener {
        override fun onInstallStatusUpdated(update: ModuleInstallStatusUpdate) {
            update.progressInfo?.let {
                // Updating the progress
                _installModuleState.value = _installModuleState.value.copy(downloadProgress = DownloadProgress(
                    totalBytesToDownload = it.totalBytesToDownload,
                    bytesDownloaded = it.bytesDownloaded
                ))
            }

            // Updating the download state
            _installModuleState.value = _installModuleState.value.copy(
                downloadState = DownloadState.from(update.installState)
            )

            // Unregister listener and reset the State
            if (isTerminateState(update.installState)) {
                installClient.unregisterListener(this)
                _installModuleState.value = InstallModuleState()
            }
        }

        private fun isTerminateState(@ModuleInstallStatusUpdate.InstallState state: Int): Boolean {
            return state == STATE_CANCELED || state == STATE_COMPLETED || state == STATE_FAILED
        }
    }
}