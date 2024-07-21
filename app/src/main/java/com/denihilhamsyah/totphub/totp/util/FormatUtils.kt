package com.denihilhamsyah.totphub.totp.util

import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow

object FormatUtils {

    fun formatBytes(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()

        return String.format(
            locale = Locale.getDefault(),
            format = "%.1f %s",
            bytes / 1024.0.pow(digitGroups.toDouble()), units[digitGroups]
        )
    }
}