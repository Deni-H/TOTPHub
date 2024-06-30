package com.denihilhamsyah.totphub.totp.domain.use_case

import com.denihilhamsyah.totphub.totp.domain.error.TextFieldError
import com.denihilhamsyah.totphub.totp.domain.util.Result

class IsValidAccountNameUseCase {
    operator fun invoke(accountName: String): Result<Unit, TextFieldError.AccountName> {
        if (accountName.isEmpty()) return Result.Error(TextFieldError.AccountName.EMPTY)
        return Result.Success(Unit)
    }
}