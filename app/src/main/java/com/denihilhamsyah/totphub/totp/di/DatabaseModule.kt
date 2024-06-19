package com.denihilhamsyah.totphub.totp.di

import android.content.Context
import androidx.room.Room
import com.denihilhamsyah.totphub.totp.data.room.RoomDatabaseInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabaseInstance {
        return Room.databaseBuilder(
            context = context,
            klass = RoomDatabaseInstance::class.java,
            name = "secret_db"
        ).build()
    }
}