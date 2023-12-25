package ua.vitolex.dayscounter.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ua.vitolex.dayscounter.R
import ua.vitolex.dayscounter.app.AppPreferences
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.main.MainViewModel
import ua.vitolex.dayscounter.presentation.components.BannerAdView
import ua.vitolex.dayscounter.presentation.components.EventSelectDialog
import ua.vitolex.dayscounter.presentation.components.NumberPickerDialog
import ua.vitolex.dayscounter.presentation.components.rememberPickerState
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyLightRed
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.MyWhite
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp
import ua.vitolex.notetaker.alarm.AlarmItem
import ua.vitolex.notetaker.alarm.AlarmScheduler
import ua.vitolex.notetaker.alarm.AlarmSchedulerImpl
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(navController: NavHostController, viewModel: MainViewModel) {
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
    val context = LocalContext.current
    val alarmScheduler: AlarmScheduler = AlarmSchedulerImpl(context)
    var alarmItem: AlarmItem? = null

    val openEventSelectDialog = remember {
        mutableStateOf(false)
    }

    val values = remember { (1..99).map { it.toString() } }
    val valuesPickerState = rememberPickerState()
    val units = remember { listOf("minutes", "hours", "days") }
    val unitsPickerState = rememberPickerState()
    val openDialog = remember {
        mutableStateOf(false)
    }

    var selectedEventId by remember {
        mutableStateOf(AppPreferences.getSelectedEventId())
    }
    var selectedEvent by remember {
        mutableStateOf(
            Event(
                -1,
                "",
                "",
                LocalTime.now().toString(),
                LocalDate.now().toString()
            )
        )
    }
    var alarmTime by remember {
        mutableStateOf(AppPreferences.getAlarmTime())
    }

    val formatDateTime = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
    val currentDateTime = LocalDateTime.now()

    var visibilityCard by remember {
        mutableStateOf(
            0f
        )
    }

    var visibilityButton by remember {
        mutableStateOf(
            0f
        )
    }

    LaunchedEffect(key1 = true) {

        val event = viewModel.getEventById(selectedEventId)
        if (event != null) {
            visibilityCard = 1f
            visibilityButton = 1f
            selectedEvent = event
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.alarm),
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
                    IconButton(
                        onClick = {
                            val notificationTitle = "${selectedEvent.title} ${
                                context.resources.getString(
                                    R.string.`in`
                                )
                            } ${
                                viewModel.getTextForMessage(
                                    valuesPickerState.selectedItem,
                                    context
                                )
                            }"
                            alarmItem = AlarmItem(
                                alarmTime = LocalDateTime.parse(alarmTime),
                                message = notificationTitle,
                                description = selectedEvent.description
                            )
                            if (currentDateTime < LocalDateTime.parse(alarmTime) && LocalDateTime.parse(
                                    alarmTime
                                ) < LocalDateTime.of(
                                    LocalDate.parse(selectedEvent.date),
                                    LocalTime.parse(selectedEvent.time),
                                )
                            ) {
                                alarmScheduler.cancel()
                                alarmItem?.let(alarmScheduler::schedule)
                                AppPreferences.setAlarmTime(alarmTime)
                                AppPreferences.setNotificationTitle(notificationTitle)
                                AppPreferences.setNotificationMessage(selectedEvent.description)
                            } else {
                                // тут можна добавити якесь попередження що час некоректний
                            }
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                    }
                }

            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MyWhite)
                .padding(top = 65.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(MyWhite),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                item { Image(
                    painter = painterResource(id = R.drawable.icon_notification),
                    contentDescription = "Date",
                    modifier = Modifier.size(96.dp)
                )
                    Spacer(modifier = Modifier.height(40.dp)) }
                item { Text(
                    text = stringResource(R.string.set_a_reminder),
                    color = MyBlack,
                    fontFamily = exo,
                    fontWeight = FontWeight(500),
                    fontSize = 20.scaledSp(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                    Spacer(modifier = Modifier.height(40.dp)) }
                item { Button(
                    onClick = {
                        openEventSelectDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(50.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MyRed,
                        contentColor = MyWhite,
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp,
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.click_here_to_select_an_event),
                        color = MyWhite,
                        fontFamily = exo,
                        fontWeight = FontWeight(500),
                        fontSize = 16.scaledSp(),
                        textAlign = TextAlign.Center
                    )
                }
                    Spacer(modifier = Modifier.height(20.dp)) }
                item { Button(
                    onClick = {
                        openDialog.value = true

                    },
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(50.dp)
                        .alpha(visibilityButton),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MyRed,
                        contentColor = MyWhite,
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp,
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.click_here_to_set_the_time),
                        color = MyWhite,
                        fontFamily = exo,
                        fontSize = 16.scaledSp(),
                        textAlign = TextAlign.Center
                    )
                }
                    Spacer(modifier = Modifier.height(20.dp))}
                item {   Card(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(80.dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .alpha(visibilityCard),
                    colors = CardDefaults.cardColors(
                        containerColor = MyLightRed
                    ),
                    shape = RoundedCornerShape(4.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                    ) {
                        Text(
                            text = "${selectedEvent.title}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MyBlack,
                            fontFamily = exo,
                            fontSize = 18.scaledSp(),
                            textAlign = TextAlign.Left
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            val date = LocalDate.parse(selectedEvent.date)
                            val time = LocalTime.parse(selectedEvent.time)
                            Text(
                                text = stringResource(R.string.date_and_time_of_the_event)+ LocalDateTime.of(date, time).format(formatDateTime),
                                maxLines = 1,
                                fontSize = 12.scaledSp(),
                                fontFamily = exo,
                                overflow = TextOverflow.Ellipsis,
                                color = if (currentDateTime > LocalDateTime.of(
                                        date,
                                        time
                                    )
                                ) MyRed else MyBlack
                            )

                            Text(
                                text = stringResource(R.string.date_and_time_reminder) + LocalDateTime.parse(alarmTime).format(formatDateTime),
                                maxLines = 1,
                                fontSize = 12.scaledSp(),
                                fontFamily = exo,
                                overflow = TextOverflow.Ellipsis,
                                color = if (currentDateTime > LocalDateTime.parse(alarmTime) || LocalDateTime.parse(
                                        alarmTime
                                    ) > LocalDateTime.of(
                                        date,
                                        time
                                    )
                                ) MyRed else MyBlack,
                            )
                        }
                    }
                }
                    Spacer(modifier = Modifier.height(10.dp))}
            }

            Box(
                modifier = Modifier.heightIn(min = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                BannerAdView(stringResource(id = R.string.bannerId3))
            }
            EventSelectDialog(
                openEventSelectDialog = openEventSelectDialog,
                events = events,
                action = { event ->
                    selectedEvent = event
                    visibilityButton = 1f
                },
            )
            NumberPickerDialog(
                openDialog = openDialog,
                values = values,
                valuesPickerState = valuesPickerState,
                units = units,
                unitsPickerState = unitsPickerState
            ) {
                visibilityCard = 1f
                val date = LocalDate.parse(selectedEvent.date)
                val time = LocalTime.parse(selectedEvent.time)


                if (unitsPickerState.selectedItem == "days") {
                    alarmTime = LocalDateTime.of(date, time).minusMinutes(
                        valuesPickerState.selectedItem.toLong() * 24 * 60
                    ).toString()
                } else if (unitsPickerState.selectedItem == "hours")
                    alarmTime = LocalDateTime.of(date, time).minusMinutes(
                        valuesPickerState.selectedItem.toLong() * 60
                    ).toString()
                else alarmTime = LocalDateTime.of(date, time).minusMinutes(
                    valuesPickerState.selectedItem.toLong()
                ).toString()
            }

        }
    }
}