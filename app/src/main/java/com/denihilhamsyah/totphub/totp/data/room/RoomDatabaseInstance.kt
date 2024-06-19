package com.denihilhamsyah.totphub.totp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetailsEntity

@Database(
    entities = [SecretDetailsEntity::class],
    version = 1
)
abstract class RoomDatabaseInstance: RoomDatabase() {
    abstract val db: RoomDatabaseDao
}