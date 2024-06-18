package com.denihilhamsyah.totphub.code.di

import com.denihilhamsyah.totphub.code.data.RealmRepositoryImpl
import com.denihilhamsyah.totphub.code.data.TOTPRepositoryImpl
import com.denihilhamsyah.totphub.code.domain.repository.DatabaseRepository
import com.denihilhamsyah.totphub.code.domain.repository.TOTPRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
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
    fun provideDatabaseRepository(realm: Realm): DatabaseRepository {
        return RealmRepositoryImpl(realm)
    }
}