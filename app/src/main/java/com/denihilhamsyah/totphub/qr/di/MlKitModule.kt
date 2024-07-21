package com.denihilhamsyah.totphub.qr.di

import android.content.Context
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MlKitModule {

    @Provides
    @Singleton
    fun provideModuleInstallClient(
        @ApplicationContext context: Context
    ): ModuleInstallClient = ModuleInstall.getClient(context)

    @Provides
    @Singleton
    fun provideScanBarcodeOption(): GmsBarcodeScannerOptions =
        GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAutoZoom()
            .build()

    @Provides
    @Singleton
    fun provideGmsBarcodeScanner(
        @ApplicationContext context: Context,
        options: GmsBarcodeScannerOptions
    ): GmsBarcodeScanner = GmsBarcodeScanning.getClient(context, options)
}