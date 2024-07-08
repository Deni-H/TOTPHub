package com.denihilhamsyah.totphub.totp.domain.use_case

import com.denihilhamsyah.totphub.totp.domain.error.TextFieldError
import com.denihilhamsyah.totphub.totp.domain.util.Result
import org.apache.commons.codec.binary.Base32

class IsValidSecretUseCase {
    operator fun invoke(secret: String): Result<Unit, TextFieldError.Secret> {
        val minKeyBytes = 10
        val base32Pattern = "^[A-Z2-7]+=*\$".toRegex()

        if (secret.isEmpty()) return Result.Error(TextFieldError.Secret.EMPTY)

        if (!base32Pattern.matches(secret)) {
            return Result.Error(TextFieldError.Secret.INVALID_CHARACTERS)
        }

        val decoded = Base32().decode(secret)
        if (decoded.size < minKeyBytes) return Result.Error(TextFieldError.Secret.TO_SHORT)

        return Result.Success(Unit)
    }
}