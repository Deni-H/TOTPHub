package com.denihilhamsyah.totphub.code.domain.model

data class SecretDetails(
    val id: String = "",
    val secret: String,
    val secretLabel: String,
    val accountName : String,
    val timestamp: Int = 0
)