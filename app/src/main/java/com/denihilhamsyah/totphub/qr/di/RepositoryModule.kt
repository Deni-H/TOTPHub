package com.denihilhamsyah.totphub.qr.di

import com.denihilhamsyah.totphub.qr.data.MlKitBarcodeScanner
import com.denihilhamsyah.totphub.qr.domain.ScanQrRepository
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideScanQrRepository(
        scanner: GmsBarcodeScanner,
        installClient: ModuleInstallClient
    ): ScanQrRepository = MlKitBarcodeScanner(scanner, installClient)
}