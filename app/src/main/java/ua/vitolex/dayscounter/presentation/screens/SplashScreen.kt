package ua.vitolex.dayscounter.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import ua.vitolex.dayscounter.main.MainViewModel
import ua.vitolex.dayscounter.presentation.navigation.Screens
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.MyWhite
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp


@Composable
fun SplashScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("animation.json")
    )
    var animSpec = LottieClipSpec.Progress(
        0f,
        0.5705f
    )
    LaunchedEffect(key1 = true) {
        delay(2640L)
        navController.popBackStack()
        navController.navigate(Screens.MainScreen.rout)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MyWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(0.96f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .requiredHeight(220.dp)
                    .requiredWidth(220.dp),
                isPlaying = true,
                iterations = 1,
                reverseOnRepeat = true,
                speed = 1.2f,
                clipSpec = animSpec,
//            modifier =Modifier.scale(0.6f),
            )
            Text(text = "Day Ticker",
                fontFamily = exo,
                fontWeight = FontWeight(600),
                color = MyBlack,
                fontSize = 36.scaledSp(),
            )
        }
        Text(text = "from VITOLEX",
            fontFamily = exo,
            fontWeight = FontWeight(500),
            fontSize = 16.scaledSp(),
            color = MyBlack,
            modifier = Modifier
//                .padding(20.dp)
//                .height(40.dp)
                .weight(1f))


    }
}