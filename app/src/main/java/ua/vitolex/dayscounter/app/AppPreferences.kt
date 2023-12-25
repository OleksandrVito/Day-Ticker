package ua.vitolex.dayscounter.app

import android.os.Build
import androidx.annotation.RequiresApi
import io.paperdb.Paper
import java.time.LocalDateTime

object AppPreferences {

    fun setSort(sort: Boolean) {
        Paper.book().write("sort", sort)
    }

    fun getSort(): Boolean {
        return Paper.book().read("sort", false)!!
    }

    fun setSelectedEventId(selectedId: Int) {
        Paper.book().write("selectedId", selectedId)
    }

    fun getSelectedEventId(): Int {
        return Paper.book().read("selectedId", -1)!!
    }

    fun setAlarmTime(alarmTime: String) {
        Paper.book().write("alarmTime", alarmTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAlarmTime(): String {
        return Paper.book().read("alarmTime", LocalDateTime.now().toString())!!
    }

    fun setNotificationTitle(notificationTitle: String) {
        Paper.book().write("notificationTitle", notificationTitle)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNotificationTitle(): String {
        return Paper.book().read("notificationTitle", LocalDateTime.now().toString())!!
    }

    fun setNotificationMessage(notificationMessage: String) {
        Paper.book().write("notificationMessage", notificationMessage)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNotificationMessage(): String {
        return Paper.book().read("notificationMessage", LocalDateTime.now().toString())!!
    }

}