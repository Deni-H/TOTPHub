package com.denihilhamsyah.totphub.totp.di

import com.denihilhamsyah.totphub.totp.domain.model.SecretDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        return Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(SecretDetailsDao::class)
            )
        )
    }
}