package com.denihilhamsyah.totphub.totp.domain.model

import com.denihilhamsyah.totphub.totp.data.room.SecretDetailsEntity

data class SecretDetails(
    val id: String = "",
    val secret: String,
    val secretLabel: String,
    val accountName : String,
    val timestamp: Long = 0L,
    val totp: String = "-",
    val countdown: Int = 0
) {
    fun toSecretDetailsEntity(): SecretDetailsEntity {
        return SecretDetailsEntity(
            secret = this.secret,
            secretLabel = this.secretLabel,
            accountName = this.accountName
        )
    }
}