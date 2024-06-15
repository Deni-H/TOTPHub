package com.denihilhamsyah.totphub.code.di

import com.denihilhamsyah.totphub.code.data.local.TOTPRepositoryImpl
import com.denihilhamsyah.totphub.code.domain.repository.TOTPRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): TOTPRepository {
        return TOTPRepositoryImpl()
    }
}