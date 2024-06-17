package com.denihilhamsyah.totphub.code.domain.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class SecretDetails : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var secret: String = ""
    var secretLabel : String = ""
    var accountName : String = ""
}