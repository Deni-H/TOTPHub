package com.denihilhamsyah.totphub.totp.domain.use_case

import com.denihilhamsyah.totphub.totp.domain.error.TextFieldError
import com.denihilhamsyah.totphub.totp.domain.util.Result

class IsValidSecretLabelUseCase {
    operator fun invoke(secretLabel: String): Result<Unit, TextFieldError.SecretLabel> {
        if (secretLabel.isEmpty()) return Result.Error(TextFieldError.SecretLabel.EMPTY)
        return Result.Success(Unit)
    }
}