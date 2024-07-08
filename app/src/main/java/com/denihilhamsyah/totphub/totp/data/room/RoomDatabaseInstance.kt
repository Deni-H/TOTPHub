package com.denihilhamsyah.totphub.totp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SecretDetailsEntity::class],
    version = 1
)
abstract class RoomDatabaseInstance: RoomDatabase() {
    abstract val db: RoomDatabaseDao
}