package com.denihilhamsyah.totphub.totp.domain.use_case

import android.net.Uri
import com.denihilhamsyah.totphub.totp.domain.error.ParseQrError
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import com.denihilhamsyah.totphub.totp.domain.util.Result

class ParseTOTPQrUseCase {

    companion object {
        private const val SCHEME = "otpauth"
    }

    operator fun invoke(value: String): Result<SecretDetails, ParseQrError.TOTPError> {
        try {
            val uri = Uri.parse(value)

            if (uri.scheme != SCHEME) return Result.Error(ParseQrError.TOTPError.INVALID_FORMAT)

            val accountName = uri.path?.replaceFirst("/", "") ?: return Result.Error(ParseQrError.TOTPError.ACCOUNT_NAME_NOT_FOUND)
            val secret = uri.getQueryParameter("secret") ?: return Result.Error(ParseQrError.TOTPError.SECRET_NOT_FOUND)
            val secretLabel = uri.getQueryParameter("issuer") ?: return Result.Error(ParseQrError.TOTPError.SECRET_LABEL_NOT_FOUND)

            val secretDetails = SecretDetails(
                accountName = accountName,
                secret = secret,
                secretLabel = secretLabel
            )
            return Result.Success(secretDetails)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(ParseQrError.TOTPError.UNKNOWN_ERROR)
        }
    }
}