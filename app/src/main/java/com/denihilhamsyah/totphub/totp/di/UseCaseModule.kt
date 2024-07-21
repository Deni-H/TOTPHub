package com.denihilhamsyah.totphub.totp.di

import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidAccountNameUseCase
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidSecretLabelUseCase
import com.denihilhamsyah.totphub.totp.domain.use_case.IsValidSecretUseCase
import com.denihilhamsyah.totphub.totp.domain.use_case.ParseTOTPQrUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideIsValidSecretUseCase(): IsValidSecretUseCase = IsValidSecretUseCase()

    @Provides
    @Singleton
    fun provideIsValidSecretLabelUseCase(): IsValidSecretLabelUseCase = IsValidSecretLabelUseCase()

    @Provides
    @Singleton
    fun provideIsValidAccountNameUseCase(): IsValidAccountNameUseCase = IsValidAccountNameUseCase()

    @Provides
    @Singleton
    fun provideParseTOTPQrUseCase(): ParseTOTPQrUseCase = ParseTOTPQrUseCase()
}