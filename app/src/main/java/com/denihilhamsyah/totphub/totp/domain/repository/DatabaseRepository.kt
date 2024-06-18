package com.denihilhamsyah.totphub.totp.domain.repository

import arrow.core.Either
import com.denihilhamsyah.totphub.totp.common.error.DatabaseError
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    val secrets: Flow<List<SecretDetails>>

    suspend fun getSecretById(secretId: String): Either<DatabaseError, SecretDetails>

    suspend fun addSecret(
        secretDetails: SecretDetails
    ): Either<DatabaseError, SecretDetails>

    suspend fun editSecret(
        secretDetails: SecretDetails
    ): Either<DatabaseError, SecretDetails>

    suspend fun deleteSecret(secretId: String): Either<DatabaseError, Unit>
}