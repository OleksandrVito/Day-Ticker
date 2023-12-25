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
        fontFamily = exo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = exo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
//    displayLarge = TextStyle(
//            fontFamily = exo,
//    fontWeight = FontWeight.Normal,
//    fontSize = 16.sp
//)
//    ,
//    displayMedium = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//    ,
//    displaySmall = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//    ,
//    headlineLarge = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//    ,
//    headlineMedium = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//    ,
//    headlineSmall = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    ),
//    titleLarge = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    ),
//    titleMedium = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//    ,
//    titleSmall = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//
//    ,
//    bodySmall = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//    ,
//    labelLarge = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//    ,
//    labelMedium = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//    ,
//    labelSmall = TextStyle(
//        fontFamily = exo,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )


)