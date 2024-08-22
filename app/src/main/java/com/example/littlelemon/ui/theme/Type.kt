package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R

// Set of Material typography styles to start with
val Typography = Typography(

    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Display Title
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.markazitext_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 64.sp
    ),
    // Sub Title
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.markazitext_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp
    ),
    // Lead Text
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.karla_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    // Section Title (ALL UPPERCASE)
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.karla_regular)),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),
    // Section Categories
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.karla_regular)),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    ),
    // Card Title
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.karla_regular)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    // Paragraph
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.karla_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = (16 * 1.5).sp
    ),
    // Highlight
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.karla_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)