package com.denihilhamsyah.totphub.code.data

import arrow.core.Either
import com.denihilhamsyah.totphub.code.common.error.DatabaseError
import com.denihilhamsyah.totphub.code.common.error.toDatabaseError
import com.denihilhamsyah.totphub.code.domain.model.SecretDetails
import com.denihilhamsyah.totphub.code.domain.model.SecretDetailsDao
import com.denihilhamsyah.totphub.code.domain.model.toSecretDetails
import com.denihilhamsyah.totphub.code.domain.repository.DatabaseRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class RealmRepositoryImpl(
    private val realm: Realm
) : DatabaseRepository {

    override val secrets: Flow<List<SecretDetails>>
        get() = realm.query<SecretDetailsDao>()
            .asFlow()
            .map { it.list.map { secretDetailsDao ->
                secretDetailsDao.toSecretDetails()
            } }

    override suspend fun getSecretById(secretId: String): Either<DatabaseError, SecretDetails> {
        return Either.catch {
            realm.query<SecretDetailsDao>("_id == $0", ObjectId(secretId))
                .find()
                .first()
                .toSecretDetails()
        }.mapLeft { it.toDatabaseError() }
    }

    override suspend fun addSecret(
        secretDetails: SecretDetails
    ): Either<DatabaseError, SecretDetails> {
        return Either.catch {
            realm.write {
                val secretDetailsDao = SecretDetailsDao()
                    .apply {
                        secret = secretDetails.secret
                        secretLabel = secretDetails.secretLabel
                        accountName = secretDetails.accountName
                    }
                copyToRealm(secretDetailsDao).toSecretDetails()
            }
        }.mapLeft { it.toDatabaseError() }
    }

    override suspend fun editSecret(secretDetails: SecretDetails): Either<DatabaseError, SecretDetails> {
        return Either.catch {
            realm.write {
                query<SecretDetailsDao>("id == $0", ObjectId(secretDetails.id))
                    .find()
                    .first()
                    .apply {
                        secret = secretDetails.secret
                        secretLabel = secretDetails.secretLabel
                        accountName = secretDetails.accountName
                    }.toSecretDetails()
            }
        }.mapLeft { it.toDatabaseError() }
    }

    override suspend fun deleteSecret(secretId: String): Either<DatabaseError, Unit> {
        return Either.catch {
            realm.write {
                val secretDetailsDao = query<SecretDetailsDao>("_id == $0", ObjectId(secretId))
                    .find()
                    .first()
                delete(secretDetailsDao)
            }
        }.mapLeft { it.toDatabaseError() }
    }
}