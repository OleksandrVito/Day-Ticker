package ua.vitolex.dayscounter.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import ua.vitolex.dayscounter.R
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.main.MainViewModel
import ua.vitolex.dayscounter.presentation.components.BannerAdView
import ua.vitolex.dayscounter.presentation.components.DeleteDialog
import ua.vitolex.dayscounter.presentation.components.EventItem
import ua.vitolex.dayscounter.presentation.navigation.Screens
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.MyWhite
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {

    val events = viewModel.events.collectAsState(initial = emptyList())

    var currentDateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000)
            currentDateTime = LocalDateTime.now()
        }
    }

    val openDialog = remember {
        mutableStateOf(false)
    }

    val eventToDelete = remember {
        mutableStateOf(listOf<Event>())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MyWhite,
                        fontFamily = exo,
                        fontWeight = FontWeight(500),
                        fontSize = 26.scaledSp()
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MyRed
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.AddEditEventScreen.rout + "?edit=false")
            }, containerColor = MyRed) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    tint = MyBlack
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 65.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(12.dp)
            ) {
                events.value.forEachIndexed { index, element ->
                    if (index%2==0) {
                        item{
                            EventItem(
                                event = element,
                                viewModel = viewModel,
                                navController = navController,
                                currentDateTime = currentDateTime,
                                openDialog = openDialog,
                                eventToDelete = eventToDelete
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(modifier = Modifier.height(60.dp), contentAlignment = Alignment.Center){
                                BannerAdView("ca-app-pub-3940256099942544/6300978111")
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    } else {
                        item {
                            EventItem(
                                event = element,
                                viewModel = viewModel,
                                navController = navController,
                                currentDateTime = currentDateTime,
                                openDialog = openDialog,
                                eventToDelete = eventToDelete
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                }
            }
            DeleteDialog(
                openDialog = openDialog,
                action = {
                    eventToDelete.value.forEach {
                        viewModel.deleteEvent(it)
                    }
                },
                eventToDelete = eventToDelete
            )
        }
    }
}