package com.denihilhamsyah.totphub.totp.domain.model

data class SecretDetails(
    val id: String = "",
    val secret: String,
    val secretLabel: String,
    val accountName : String,
    val timestamp: Long = 0L
) {
    fun toSecretDetailsEntity(): SecretDetailsEntity {
        return SecretDetailsEntity(
            secret = this.secret,
            secretLabel = this.secretLabel,
            accountName = this.accountName
        )
    }
}