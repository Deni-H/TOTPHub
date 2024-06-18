package com.denihilhamsyah.totphub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.denihilhamsyah.totphub.R

val poppins = FontFamily(
    Font(R.font.poppins_black),
    Font(R.font.poppins_black_italic),
    Font(R.font.poppins_bold_italic),
    Font(R.font.poppins_extra_bold),
    Font(R.font.poppins_extra_bold_italic),
    Font(R.font.poppins_extra_light),
    Font(R.font.poppins_extra_light_italic),
    Font(R.font.poppins_italic),
    Font(R.font.poppins_light),
    Font(R.font.poppins_light_italic),
    Font(R.font.poppins_medium_italic),
    Font(R.font.poppins_semi_bold),
    Font(R.font.poppins_semi_bold_italic),
    Font(R.font.poppins_thin),
    Font(R.font.poppins_thin_italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 25.89.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 9.89.sp
    )
)