package ua.vitolex.dayscounter.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val time: String,
    val date: String
)

