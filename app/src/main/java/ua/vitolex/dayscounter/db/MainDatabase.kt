package ua.vitolex.dayscounter.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.vitolex.dayscounter.domain.model.Event

@Database(
    entities = [Event::class],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {
    abstract val dao: Dao
}
