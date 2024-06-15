package com.denihilhamsyah.totphub.code.domain.repository

interface TOTPRepository {

    fun generateTOTP(secret: String, counter: Long): String
}