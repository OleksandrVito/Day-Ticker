package ua.vitolex.dayscounter.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.vitolex.dayscounter.R
import ua.vitolex.dayscounter.app.AppPreferences
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.main.MainViewModel
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
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EventSelectDialog(
    openEventSelectDialog: MutableState<Boolean>,
    events: List<Event>,
    action: (Event) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
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
    var tempSelectedEvent by remember {
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

    LaunchedEffect(key1 = true) {
            val event = viewModel.getEventById(selectedEventId)
            if (event != null) {
                tempSelectedEvent = event
            }
    }

    if (openEventSelectDialog.value) {
        AlertDialog(
            onDismissRequest = { openEventSelectDialog.value = false },
            modifier = Modifier.shadow(
                20.dp, shape = RoundedCornerShape(4.dp), ambientColor = Color.Gray,
                spotColor = Color.Gray
            ),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .background(MyWhite, RoundedCornerShape(4.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(MyLightGray)
                        .padding(start = 14.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = stringResource(R.string.event_selection), color = MyBlack,
                        fontFamily = exo,
                        fontSize = 20.scaledSp(),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 0.dp)
                    )
                }
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(MyWhite, RoundedCornerShape(4.dp))
                        .padding(start = 14.dp, end = 14.dp)
                ) {
                    if (events.size>0){
                        events.forEachIndexed { index, element ->
                            val date = LocalDate.parse(element.date)
                            val time = LocalTime.parse(element.time)
                            val elementDateTime = LocalDateTime.of(date, time)

                            val formatDate = DateTimeFormatter.ofPattern("dd.MM.yy")
                            val formatTime = DateTimeFormatter.ofPattern("HH:mm")

                            val currentDateTime =  LocalDateTime.now()
                            if (elementDateTime > currentDateTime) {
                                item {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                tempSelectedEvent = element
                                                AppPreferences.setSelectedEventId(element.id)
                                            }
                                            .background(Color.White, RoundedCornerShape(4.dp)),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (tempSelectedEvent == element) MyRed else Color.White
                                        ),
                                        shape = RoundedCornerShape(6.dp),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 4.dp,
                                            pressedElevation = 0.dp,
                                        )
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(6.dp)
                                        ) {
                                            Text(
                                                text = "${element.title}",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                color = MyBlack,
                                                fontFamily = exo,
                                                fontSize = 18.scaledSp(),
                                                textAlign = TextAlign.Left
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                Text(
                                                    text = date.format(formatDate),
                                                    maxLines = 1,
                                                    fontSize = 12.scaledSp(),
                                                    fontFamily = exo,
                                                    overflow = TextOverflow.Ellipsis,
                                                    color = MyBlack,
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = time.format(formatTime),
                                                    maxLines = 1,
                                                    fontSize = 12.scaledSp(),
                                                    fontFamily = exo,
                                                    overflow = TextOverflow.Ellipsis,
                                                    color = MyBlack,
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }

                        }
                    } else {
                        item {
                            Text(text = stringResource(R.string.no_event_yet_start_adding),
                                fontSize = 22.scaledSp(),
                                fontFamily = exo,
                                color = MyGray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxSize())
                        }
                    }

                }
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(MyLightGray)
                        .padding(end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Right) {
                    TextButton(onClick = {
                        openEventSelectDialog.value = false
                        selectedEvent = Event(
                            -1,
                            "",
                            "",
                            LocalTime.now().toString(),
                            LocalDate.now().toString()
                        )
                    }) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = MyRed,
                            fontFamily = exo,
                            fontSize = 16.scaledSp()
                        )
                    }
                    TextButton(onClick = {
                        selectedEvent = tempSelectedEvent
                        action.invoke(selectedEvent)
                        openEventSelectDialog.value = false
                    }) {
                        Text(
                            text = stringResource(R.string.ok),
                            color = MyRed,
                            fontFamily = exo,
                            fontSize = 16.scaledSp()
                        )
                    }
                }
            }

        }
    }
}

