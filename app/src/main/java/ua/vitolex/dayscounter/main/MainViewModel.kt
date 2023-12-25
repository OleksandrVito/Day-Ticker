package ua.vitolex.dayscounter.main


import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ua.vitolex.dayscounter.R
import ua.vitolex.dayscounter.db.MainDatabase
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.presentation.navigation.Screens
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val mainDatabase: MainDatabase,
) : ViewModel() {
    val events: Flow<List<Event>> = mainDatabase.dao.getEvents()

    private val _title = mutableStateOf("")
    val title: State<String> = _title


    private val _description = mutableStateOf("")
    val description: State<String> = _description

    fun onTitleChange(it: String) {
        _title.value = it
    }

    fun onDescriptionChange(it: String) {
        _description.value = it
    }

    private val _timeToNotification = mutableStateOf(-1)
    val timeToNotification: State<Int> = _timeToNotification

    fun onTimeToNotificationChange(values: String? = null , units: String? = null, timeToNotification: Int? = null) {
        var time = -1
        if (timeToNotification != null) {
            time = timeToNotification
        }
        if (values != null &&  units != null) {
            when (units) {
                "minutes" -> time = values.toInt()
                "hours" ->  time = values.toInt() * 60
                "days" -> time = values.toInt() * 24 * 60
            }
        }


        _timeToNotification.value = time
    }


    fun insertEvent(event: Event) = viewModelScope.launch {
        mainDatabase.dao.insertEvent(event)

    }

    fun deleteEvent(event: Event) = viewModelScope.launch {
        mainDatabase.dao.deleteEvent(event)
    }

    fun editEvent(navController: NavController, id: Int) {
        navController.navigate(Screens.AddEditEventScreen.rout + "?edit=true&id=${id}")
    }

    suspend fun getEventById(id: Int): Event? {
        return mainDatabase.dao.getEventById(id)
    }

    val calendar = Calendar.getInstance()
    private val _year = mutableStateOf(calendar.get(Calendar.YEAR))
    val year: State<Int> = _year

    private val _month = mutableStateOf(calendar.get(Calendar.MONTH))
    val month: State<Int> = _month

    private val _dayOfMonth = mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH))
    val dayOfMonth: State<Int> = _dayOfMonth

    private val _hour = mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY))
    val hour: State<Int> = _hour

    private val _minute = mutableStateOf(calendar.get(Calendar.MINUTE))
    val minute: State<Int> = _minute


    fun setDateTime(year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int) {
        _year.value = year
        _month.value = month
        _dayOfMonth.value = dayOfMonth
        _hour.value = hour
        _minute.value = minute
    }


    data class Time(val days: Int, val hour: Int, val minute: Int, val second: Int)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDifference(
        currentDateTime: LocalDateTime,
        date: LocalDate,
        time: LocalTime,
    ): Time {
        val difference = Duration.between(currentDateTime, LocalDateTime.of(date, time)).toMillis()
        val seconds = (difference / 1000) % 60
        val minutes = (difference / (1000 * 60)) % 60
        val hours = (difference / (1000 * 60 * 60)) % 24
        val days = (difference / (1000 * 60 * 60 * 24))


        return Time(days.toInt(), hours.toInt(), minutes.toInt(), seconds.toInt())
    }

    //    validation
    private var _errorState = mutableStateOf(false)
    val errorState: State<Boolean> = _errorState


    fun validateTitle(title: String) {
        if (title.isEmpty()) _errorState.value = true else _errorState.value = false
    }


    fun getTextForMessage (notification:String,  context: Context): String {
        try {
            val notificationInt = notification.toInt()
            val list1 = listOf<Int>(1,21,31,41,51,61,71,81,91)
            val list2 = listOf<Int>(2,3,4,22,23,24,32,33,34,42,43,44,52,53,54,62,63,64,72,73,74,82,83,84,92,93,94)
            val text =
                if (notificationInt % 1440 == 0) {
                    val days = notificationInt / 1440
                    if ( days in list1 )  "${notificationInt / 1440} ${context.resources.getString(R.string.day1)}"
                    else if ( days in list2 ) "${notificationInt / 1440} ${context.resources.getString(R.string.day2)}"
                    else "${notificationInt / 1440} ${context.resources.getString(R.string.day5)}"
                } else  if (notificationInt % 60 == 0) {
                    val hours = notificationInt / 60
                    if ( hours in list1 )  "${notificationInt / 60} ${context.resources.getString(R.string.hour1)}"
                    else if ( hours in list2 ) "${notificationInt / 60} ${context.resources.getString(R.string.hour2)}"
                    else "${notificationInt / 60} ${context.resources.getString(R.string.hour5)}"
                } else {
                    val minutes = notificationInt
                    if ( minutes in list1 )  "${notificationInt} ${context.resources.getString(R.string.minute1)}"
                    else if ( minutes in list2 ) "${notificationInt} ${context.resources.getString(R.string.minute2)}"
                    else "${notificationInt} ${context.resources.getString(R.string.minute5)}"
                }
            return text
        } catch (e: NumberFormatException) { null }

        return notification
    }
}

