package com.denihilhamsyah.totphub.totp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDatabaseDao {

    @get:Query("SELECT * FROM SecretDetailsEntity")
    val secrets: Flow<List<SecretDetailsEntity>>

    @Query("SELECT * FROM SecretDetailsEntity WHERE id = :secretId")
    suspend fun getSecretById(secretId: String): SecretDetailsEntity

    @Insert
    suspend fun addSecret(secretDetailsEntity: SecretDetailsEntity)

    @Update
    suspend fun editSecret(secretDetailsEntity: SecretDetailsEntity)

    @Delete
    suspend fun deleteSecret(secretDetailsEntity: SecretDetailsEntity)
}