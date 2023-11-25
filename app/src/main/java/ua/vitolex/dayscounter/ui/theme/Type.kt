package ua.vitolex.dayscounter.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ua.vitolex.dayscounter.R


val exo = FontFamily(
    Font(
        R.font.exo2_light,
        weight = FontWeight.Light
    ),
    Font(
        R.font.exo2_medium,
        weight = FontWeight.Medium
    ),
    Font(
        R.font.exo2_regular,
        weight = FontWeight.Normal
    ),
)
val cairo = FontFamily(
    Font(
        R.font.cairo_medium,
        weight = FontWeight.Medium
    ),
    Font(
        R.font.cairo_bold,
        weight = FontWeight.Bold
    ),

)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)