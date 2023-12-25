package ua.vitolex.dayscounter.main

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import ua.vitolex.dayscounter.alarm.StartUpReceiver
import ua.vitolex.dayscounter.presentation.navigation.SetupNavHost
import ua.vitolex.dayscounter.ui.theme.DaysCounterTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
        val receiver = ComponentName(applicationContext, StartUpReceiver::class.java)
        //coment

        applicationContext.packageManager?.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        setContent {
            val navController = rememberAnimatedNavController()
            val mainViewModel: MainViewModel = hiltViewModel()
            DaysCounterTheme()
            {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    SetupNavHost(
                        navController = navController,
                        mainViewModel = mainViewModel,
                    )
                }
            }
        }
    }
}
