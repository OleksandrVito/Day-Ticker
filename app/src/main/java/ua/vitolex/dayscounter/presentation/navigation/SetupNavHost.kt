package ua.vitolex.dayscounter.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import ua.vitolex.dayscounter.main.MainViewModel
import ua.vitolex.dayscounter.presentation.screens.AddEditEventScreen
import ua.vitolex.dayscounter.presentation.screens.MainScreen
import ua.vitolex.dayscounter.presentation.screens.SplashScreen


sealed class Screens(val rout: String) {
    object SplashScreen : Screens(rout = "splash_screen")
    object MainScreen : Screens(rout = "main_screen")
    object AddEditEventScreen : Screens(rout = "add_edit_event_screen")

}


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {

    AnimatedNavHost(navController = navController, startDestination = Screens.SplashScreen.rout) {
        composable(
            route = Screens.SplashScreen.rout,
        ) {
            SplashScreen(navController = navController)

        }

        composable(
            route = Screens.MainScreen.rout,
            enterTransition = {
                fadeIn(animationSpec = tween(2000))
            },
        ) {
            MainScreen(
                navController = navController,
                viewModel = mainViewModel,
            )
        }

        composable(
            route = Screens.AddEditEventScreen.rout + "?edit={edit}&id={id}",
            enterTransition = {
                fadeIn(animationSpec = tween(2000))
            },
            arguments = listOf(
                navArgument(name = "edit") {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument(name = "id") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            ),
        ) { backStackEntry ->
            AddEditEventScreen(
                navController,
                backStackEntry.arguments?.getBoolean("edit") ?: false,
                backStackEntry.arguments?.getInt("id") ?: 0,
            )
        }
    }
}