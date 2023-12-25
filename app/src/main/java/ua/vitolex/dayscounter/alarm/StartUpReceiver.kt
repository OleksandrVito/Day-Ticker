package ua.vitolex.dayscounter.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import ua.vitolex.dayscounter.app.AppPreferences
import ua.vitolex.notetaker.alarm.AlarmItem
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class StartUpReceiver : BroadcastReceiver() {
    @SuppressLint("ScheduleExactAlarm")
    @RequiresApi(Build.VERSION_CODES.S)
    @Override
    override fun onReceive(context: Context, intent: Intent?) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent?.getAction())) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                val alarmItem = AlarmItem(
                    alarmTime = LocalDateTime.parse(AppPreferences.getAlarmTime()),
                    message = AppPreferences.getNotificationTitle(),
                    description = AppPreferences.getNotificationMessage()
                )
                val intent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra("EXTRA_MESSAGE", alarmItem.message)
                    putExtra("EXTRA_DESCRIPTION", alarmItem.description)
                }
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmItem.alarmTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000L,
                    PendingIntent.getBroadcast(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                )
        }
    }
}