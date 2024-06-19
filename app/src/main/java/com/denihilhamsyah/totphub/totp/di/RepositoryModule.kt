package com.denihilhamsyah.totphub.totp.di

import com.denihilhamsyah.totphub.totp.data.DatabaseRepositoryImpl
import com.denihilhamsyah.totphub.totp.data.TOTPRepositoryImpl
import com.denihilhamsyah.totphub.totp.data.room.RoomDatabaseInstance
import com.denihilhamsyah.totphub.totp.domain.repository.DatabaseRepository
import com.denihilhamsyah.totphub.totp.domain.repository.TOTPRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTOTPRepository(): TOTPRepository {
        return TOTPRepositoryImpl()
    }

    @Provides
    @Singleton
    @Named("room")
    fun provideRoomDatabaseRepository(
        roomDatabaseInstance: RoomDatabaseInstance
    ): DatabaseRepository = DatabaseRepositoryImpl(roomDatabaseInstance.db)
}