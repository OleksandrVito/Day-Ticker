package ua.vitolex.dayscounter.presentation.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ua.vitolex.dayscounter.R
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.main.MainViewModel
import ua.vitolex.dayscounter.presentation.components.BannerAdView
import ua.vitolex.dayscounter.presentation.components.CustomTextField
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyGray
import ua.vitolex.dayscounter.ui.theme.MyLightGray
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.MyWhite
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditEventScreen(
    navController: NavHostController,
    edit: Boolean,
    id: Int,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    var date by remember {
        mutableStateOf(
            LocalDate.of(
                viewModel.year.value,
                viewModel.month.value + 1,
                viewModel.dayOfMonth.value
            )
        )
    }
    val datePicker = DatePickerDialog(
        context,
        R.style.MyDatePickerDialogTheme,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            date = LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
        }, viewModel.year.value, viewModel.month.value, viewModel.dayOfMonth.value
    )

    var time by remember {
        mutableStateOf(
            LocalTime.of(
                viewModel.hour.value,
                viewModel.minute.value
            )
        )
    }
    val timePicker = TimePickerDialog(
        context,
        R.style.MyDatePickerDialogTheme,
        { _, selectedHour: Int, selectedMinute: Int ->
            time = LocalTime.of(selectedHour, selectedMinute)
        }, viewModel.hour.value, viewModel.minute.value, true
    )
    val formatDate = DateTimeFormatter.ofPattern("dd.MM.yy")
    val formatTime = DateTimeFormatter.ofPattern("HH:mm")

    LaunchedEffect(key1 = true) {
        if (edit) {
            val event = viewModel.getEventById(id)
            if (event != null) {
                viewModel.onTitleChange(event.title)
                viewModel.onDescriptionChange(event.description ?: "")
                date = LocalDate.parse(event.date)
                time = LocalTime.parse(event.time)
                viewModel.setDateTime(
                    date.year,
                    date.monthValue - 1,
                    date.dayOfMonth,
                    time.hour,
                    time.minute
                )
            }

        } else {
            viewModel.onTitleChange("")
            viewModel.onDescriptionChange("")
        }
    }

    var enabledActionButton by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (
                            edit
                        ) stringResource(id = R.string.edit_event) else stringResource(id = R.string.add_event),
                        color = MyWhite,
                        fontFamily = exo,
                        fontWeight = FontWeight(500),
                        fontSize = 26.scaledSp()
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MyRed
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MyWhite
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.validateTitle(viewModel.title.value)
                            if (!viewModel.errorState.value) {
                                viewModel.insertEvent(
                                    Event(
                                        id = id,
                                        title = viewModel.title.value,
                                        description = viewModel.description.value,
                                        time = time.toString(),
                                        date = date.toString(),
                                    )
                                )
                                enabledActionButton = false
                                navController.popBackStack()
                            }
                        },
                        enabled = enabledActionButton,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save",
                            tint = MyWhite
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 65.dp)
                .background(MyWhite)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(12.dp)
            ) {
                item() {
                    Column {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            text = stringResource(id = R.string.title_placeholder),
                            fontSize = 14.scaledSp(),
                            fontFamily = exo,
                            fontWeight = FontWeight.Light,
                            color = MyBlack
                        )
                        CustomTextField(
                            onValueChanged = {
                                viewModel.onTitleChange(it)
                                viewModel.validateTitle(viewModel.title.value)
                            },
                            value = viewModel.title.value,
                            error = stringResource(id = R.string.title_is_required),
                            isError = viewModel.errorState.value,
                        )
                    }

                }
                item() {
                    Column {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            text = stringResource(id = R.string.description_placeholder),
                            fontSize = 14.scaledSp(),
                            fontFamily = exo,
                            color = MyBlack
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),

                            value = viewModel.description.value,
                            onValueChange = {
                                viewModel.onDescriptionChange(it)
                            },
                            placeholder = {
                                Text(
                                    "...",
                                    fontSize = 22.scaledSp(),
                                    fontFamily = exo,
                                    fontWeight = FontWeight.Light,
                                    color = Color.DarkGray
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Default
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MyLightGray,
                                unfocusedContainerColor = MyLightGray,
//                                errorContainerColor = MyRed
                                focusedIndicatorColor = MyGray,
                                unfocusedIndicatorColor = MyGray,
                                cursorColor = MyGray,
                            ),
                            textStyle = TextStyle(
                                fontSize = 22.scaledSp(),
                                fontFamily = exo,
                                fontWeight = FontWeight.Normal,
                                color = MyBlack
                            )
                        )
                    }

                }
                item() {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    datePicker.show()
                                }) {
                                    Image(
                                        painter = painterResource(id = R.drawable.icon_calendar),
                                        contentDescription = "Date",
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                                Text(
                                    date.format(formatDate),
                                    fontSize = 18.scaledSp(),
                                    fontFamily = exo,
                                    color = MyBlack,
                                    modifier = Modifier
                                        .clickable {
                                            datePicker.show()
                                        }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    time.format(formatTime),
                                    fontSize = 18.scaledSp(),
                                    fontFamily = exo,
                                    color = MyBlack,
                                    modifier = Modifier
                                        .clickable {
                                            timePicker.show()
                                        }
                                )
                                IconButton(onClick = {
                                    timePicker.show()
                                }) {
                                    Image(
                                        painter = painterResource(id = R.drawable.icon_clock),
                                        contentDescription = "Date",
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(modifier = Modifier.height(60.dp), contentAlignment = Alignment.Center){
                        BannerAdView(stringResource(id = R.string.bannerId1))
                    }
                }
            }
        }
    }
}
