package ua.vitolex.dayscounter.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import ua.vitolex.dayscounter.R
import ua.vitolex.dayscounter.app.AppPreferences
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.main.MainViewModel
import ua.vitolex.dayscounter.presentation.components.BannerAdView
import ua.vitolex.dayscounter.presentation.components.DeleteDialog
import ua.vitolex.dayscounter.presentation.components.EventItem
import ua.vitolex.dayscounter.presentation.navigation.Screens
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyGray
import ua.vitolex.dayscounter.ui.theme.MyLightGray
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.MyWhite
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    var reverseSorted by remember {
        mutableStateOf(AppPreferences.getSort())
    }

    val temp = viewModel.events.collectAsState(initial = emptyList()).value
    val events = if (!reverseSorted) {
        temp.sortedBy {
            val date = LocalDate.parse(it.date)
            val time = LocalTime.parse(it.time)
            LocalDateTime.of(date, time)
        }
    } else {
        temp.sortedBy {
            val date = LocalDate.parse(it.date)
            val time = LocalTime.parse(it.time)
            LocalDateTime.of(date, time)
        }.reversed()
    }

    var currentDateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000)
            currentDateTime = LocalDateTime.now()
        }
    }


    var selectedEventId by remember {
        mutableStateOf(AppPreferences.getSelectedEventId())
    }
//    LaunchedEffect(key1 = true) {
//        val event = viewModel.getEventById(selectedEventId)
//        if (event != null) {
//            selectedEvent = event
//        }
//    }

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
                ),
                actions = {
                    Row {
                        IconButton(
                            onClick = {
                                reverseSorted = !reverseSorted
                                AppPreferences.setSort(reverseSorted)
                            },
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.sort),
                                contentDescription = "Sort",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = {
                                navController.navigate(Screens.AlarmScreen.rout)
                            },
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.alarm),
                                contentDescription = "Alarm",
                                tint = Color.White
                            )
                        }
                    }
                }
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
                .padding(top = 65.dp),
            contentAlignment =  if (events.size > 0) Alignment.TopCenter else Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(12.dp),

            ) {
                if (events.size > 0) {
                    events.forEachIndexed { index, element ->
                        if ((index + 1) % 2 != 0) {
                            item {
//                            if (selectedEventId == element.id)
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
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier.heightIn(min = 60.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    BannerAdView(stringResource(id = R.string.bannerId1))
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                } else {
                   item {
                       Text(text = stringResource(R.string.no_event_yet_start_adding),
                           fontSize = 28.scaledSp(),
                           fontFamily = exo,
                           color = MyGray,
                           textAlign = TextAlign.Center,
                           modifier = Modifier.fillMaxSize())
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