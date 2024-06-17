package com.denihilhamsyah.totphub.code.domain.repository

import com.denihilhamsyah.totphub.code.domain.model.SecretDetails

interface DatabaseRepository {
    fun saveSecret(secretDetails: SecretDetails)

    fun getSecrets()
}