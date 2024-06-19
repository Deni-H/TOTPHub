package com.denihilhamsyah.totphub.totp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class SecretDetailsEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val secret: String,
    val secretLabel: String,
    val accountName : String
) {
    fun toSecretDetails(): SecretDetails {
        return SecretDetails(
            id = this.id,
            secret = this.secret,
            secretLabel = this.secretLabel,
            accountName = this.accountName,
            timestamp = UUID.fromString(this.id).timestamp()
        )
    }
}