package com.denihilhamsyah.totphub.totp.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoomDatabaseDao {

    @get:Query("SELECT * FROM SecretDetailsEntity")
    val secrets: PagingSource<Int, SecretDetailsEntity>

    @Query("SELECT * FROM SecretDetailsEntity WHERE id = :secretId")
    suspend fun getSecretById(secretId: String): SecretDetailsEntity

    @Insert
    suspend fun addSecret(secretDetailsEntity: SecretDetailsEntity)

    @Update
    suspend fun editSecret(secretDetailsEntity: SecretDetailsEntity)

    @Delete
    suspend fun deleteSecret(secretDetailsEntity: SecretDetailsEntity)
}