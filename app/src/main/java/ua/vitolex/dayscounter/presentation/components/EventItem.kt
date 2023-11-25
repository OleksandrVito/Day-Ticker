package ua.vitolex.dayscounter.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.main.MainViewModel
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyBlue
import ua.vitolex.dayscounter.ui.theme.MyLightGray
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.MyWhite
import ua.vitolex.dayscounter.ui.theme.cairo
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventItem(
    event: Event,
    viewModel: MainViewModel,
    navController: NavHostController,
    currentDateTime: LocalDateTime,
    openDialog: MutableState<Boolean>,
    eventToDelete: MutableState<List<Event>>,
) {
    val date = LocalDate.parse(event.date)
    val time = LocalTime.parse(event.time)
    LocalDateTime.of(date, time)

    val formatDate = DateTimeFormatter.ofPattern("dd.MM.yy")
    val formatTime = DateTimeFormatter.ofPattern("HH:mm")

    var color by remember {
        mutableStateOf(MyBlack)
    }

    var bgColor by remember {
        mutableStateOf(MyBlack)
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(500)
            color = if (color == MyBlack) MyWhite else MyBlack
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .height(30.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${event.title}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.scaledSp(),
                    fontFamily = exo,
                    fontWeight = FontWeight.Medium,
                    color = MyBlack,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
                IconButton(
                    onClick = { viewModel.editEvent(navController, event.id) },
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit note",
                        tint = MyBlue
                    )
                }
                IconButton(
                    onClick = {
                        eventToDelete.value = mutableListOf(event)
//                        viewModel.deleteEvent(event)
                        openDialog.value = true},
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note",
                        tint = MyBlack,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                thickness = 2.dp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (event.description.isNotEmpty()){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(MyLightGray, RoundedCornerShape(4.dp))
                    .padding(8.dp),
                    contentAlignment = Alignment.TopStart) {
                    Text(
                        text = event.description,
                        maxLines = 10,
                        fontSize = 16.scaledSp(),
                        fontFamily = exo,
                        overflow = TextOverflow.Ellipsis,
                        color = MyBlack,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Text(
                        text = date.format(formatDate),
                        maxLines = 1,
                        fontSize = 14.scaledSp(),
                        fontFamily = exo,
                        overflow = TextOverflow.Ellipsis,
                        color = MyBlack,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(contentAlignment = Alignment.TopEnd) {
                    Text(
                        text = time.format(formatTime),
                        maxLines = 1,
                        fontSize = 14.scaledSp(),
                        fontFamily = exo,
                        overflow = TextOverflow.Ellipsis,
                        color = MyBlack,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val time = viewModel.getDifference(currentDateTime, date, time)

                if (time.days <= 0 && time.hour <= 0 && time.minute <= 0 && time.second <= 0) {
                    bgColor = MyRed; color = MyRed
                } else MyBlack

                Box(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 4.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        text = "Залишилось ",
                        fontSize = 18.scaledSp(),
                        fontFamily = exo,
                        color= MyBlack,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Box(
                    modifier = Modifier
                        .background(bgColor, RoundedCornerShape(2.dp))
                        .padding(horizontal = 6.dp)
                ) {
                    Text(
                        text =  if (time.days < 0) "0" else "${time.days}",
                        fontSize = 16.scaledSp(),
                        fontFamily = cairo,
                        color = MyWhite,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Dotes(color)
                Box(
                    modifier = Modifier
                        .background(bgColor, RoundedCornerShape(2.dp))
                        .padding(horizontal = 6.dp)
                ) {
                    Text(
                        text = if (time.hour < 0) "00" else if (time.hour < 10) "0${time.hour}" else "${time.hour}",
                        fontSize = 16.scaledSp(),
                        fontFamily = cairo,
                        color = MyWhite,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Dotes(color)
                Box(
                    modifier = Modifier
                        .background(bgColor, RoundedCornerShape(2.dp))
                        .padding(horizontal = 6.dp)
                ) {
                    Text(
                        text = if (time.minute < 0) "00" else if (time.minute < 10) "0${time.minute}" else "${time.minute}",
                        fontSize = 16.scaledSp(),
                        fontFamily = cairo,
                        color = MyWhite,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Dotes(color)
                Box(
                    modifier = Modifier
                        .background(bgColor, RoundedCornerShape(2.dp))
                        .padding(horizontal = 6.dp)
                ) {
                    Text(
                        text =
                        if (time.second < 0) "00" else if (time.second < 10) "0${time.second}" else "${time.second}",
                        fontSize = 16.scaledSp(),
                        fontFamily = cairo,
                        color = MyWhite,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}