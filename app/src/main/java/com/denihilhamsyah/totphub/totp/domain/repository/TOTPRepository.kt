package com.denihilhamsyah.totphub.totp.domain.repository

interface TOTPRepository {

    fun generateTOTP(secret: String, counter: Long): String
}