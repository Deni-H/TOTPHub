package com.denihilhamsyah.totphub.totp.data

import arrow.core.Either
import com.denihilhamsyah.totphub.totp.data.room.RoomDatabaseDao
import com.denihilhamsyah.totphub.totp.domain.error.DatabaseError
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import com.denihilhamsyah.totphub.totp.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseRepositoryImpl(
    private val db: RoomDatabaseDao
) : DatabaseRepository {

    override val secrets: Flow<List<SecretDetails>>
        get() = db.secrets.map { secrets ->
            secrets.map { secretDetailsEntity ->
                secretDetailsEntity.toSecretDetails()
            }
        }

    override suspend fun getSecretById(secretId: String): Either<DatabaseError, SecretDetails> {
        return Either
            .catch { db.getSecretById(secretId).toSecretDetails() }
            .mapLeft { it.toDatabaseError() }
    }

    override suspend fun addSecret(secretDetails: SecretDetails): Either<DatabaseError, Unit> {
        return Either
            .catch { db.addSecret(secretDetails.toSecretDetailsEntity()) }
            .mapLeft { it.toDatabaseError() }
    }

    override suspend fun editSecret(secretDetails: SecretDetails): Either<DatabaseError, Unit> {
        return Either
            .catch { db.editSecret(secretDetails.toSecretDetailsEntity()) }
        .mapLeft { it.toDatabaseError() }
    }

    override suspend fun deleteSecret(secretDetails: SecretDetails): Either<DatabaseError, Unit> {
        return Either
            .catch { db.deleteSecret(secretDetails.toSecretDetailsEntity()) }
            .mapLeft { it.toDatabaseError() }
    }
}