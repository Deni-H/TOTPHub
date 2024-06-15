package com.denihilhamsyah.totphub.code.data.local

import com.denihilhamsyah.totphub.code.data.exception.CodeGenerationException
import com.denihilhamsyah.totphub.code.domain.repository.TOTPRepository
import org.apache.commons.codec.binary.Base32
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.pow

class TOTPRepositoryImpl : TOTPRepository {

    override fun generateTOTP(secret: String, counter: Long): String {
        return try {
            val hash = generateHash(secret, counter)
            getDigitsFromHash(hash)
        } catch (e: Exception) {
            throw CodeGenerationException("Failed to generate code. See nested exception.", e)
        }
    }

    @Throws(InvalidKeyException::class, NoSuchAlgorithmException::class)
    private fun generateHash(key: String, counter: Long): ByteArray {
        val data = ByteArray(8)
        var value = counter
        for (i in 7 downTo 0) {
            data[i] = (value and 0xFF).toByte()
            value = value shr 8
        }

        // Create a HMAC-SHA1 signing key from the shared key
        val codec = Base32()
        val decodedKey = codec.decode(key)
        val signKey = SecretKeySpec(decodedKey, ALGORITHM)
        val mac = Mac.getInstance(ALGORITHM)
        mac.init(signKey)

        // Create a hash of the counter value
        return mac.doFinal(data)
    }

    private fun getDigitsFromHash(hash: ByteArray): String {
        val offset = hash[hash.size - 1].toInt() and 0xF

        var truncatedHash: Long = 0
        for (i in 0..3) {
            truncatedHash = (truncatedHash shl 8) or (hash[offset + i].toInt() and 0xFF).toLong()
        }

        truncatedHash = truncatedHash and 0x7FFFFFFF
        truncatedHash %= 10.0.pow(DIGITS_LENGTH.toDouble()).toLong()

        // Left pad with 0s for a n-digit code
        return String.format("%0${DIGITS_LENGTH}d", truncatedHash)
    }

    companion object {
        private const val ALGORITHM = "HmacSHA1"
        private const val DIGITS_LENGTH = 6
    }
}