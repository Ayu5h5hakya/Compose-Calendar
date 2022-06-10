package com.example.compose_calendar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.compose_calendar.R

val albertFamily = FontFamily(
    Font(R.font.albertsans_bold, FontWeight.Bold),
    Font(R.font.albertsans_light, FontWeight.Light),
    Font(R.font.albertsans_regular, FontWeight.Normal),
    Font(R.font.albertsans_medium, FontWeight.Medium),
)

val Typography = Typography(

    headlineLarge = TextStyle(
        fontFamily = albertFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),

    headlineSmall = TextStyle(
        fontFamily = albertFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = albertFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,

    ),

    titleMedium = TextStyle(
        fontFamily = albertFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = albertFamily,
        fontWeight = FontWeight.Bold,
    )
)