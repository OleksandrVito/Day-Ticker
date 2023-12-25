package ua.vitolex.notetaker.alarm

import java.time.LocalDateTime

data class AlarmItem(
    val alarmTime : LocalDateTime,
    val message : String,
    val description : String
)