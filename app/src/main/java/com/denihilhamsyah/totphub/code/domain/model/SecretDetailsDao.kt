package com.denihilhamsyah.totphub.code.domain.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class SecretDetailsDao : RealmObject {
    @PrimaryKey var id: ObjectId = ObjectId()
    var secret: String = ""
    var secretLabel : String = ""
    var accountName : String = ""
}

fun SecretDetailsDao.toSecretDetails(): SecretDetails {
    return SecretDetails(
        id = this.id.toHexString(),
        secret = this.secret,
        secretLabel = this.secretLabel,
        accountName = this.accountName,
        timestamp = this.id.timestamp
    )
}