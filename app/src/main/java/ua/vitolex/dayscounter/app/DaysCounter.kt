package ua.vitolex.dayscounter.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.Configuration
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import io.paperdb.Paper
import ua.vitolex.dayscounter.R
import javax.inject.Inject


@HiltAndroidApp
class DaysCounter : Application(){

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Paper.init(this)

//        val channelId = "alarm_id"
//        val channelName = "alarm_name"
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channel = NotificationChannel(
//            channelId,
//            channelName,
//            NotificationManager.IMPORTANCE_HIGH
//        )
//        notificationManager.createNotificationChannel(channel)

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          val channelId = "alarm_id"
          val channelName = "alarm_name"

          val channel = NotificationChannel(
              channelId,
              channelName,
              NotificationManager.IMPORTANCE_HIGH
          )
          val customSoundUri = Uri.parse("android.resource://" + this.packageName + "/" + R.raw.strong)

          val vibration = longArrayOf(500, 200, 500, 200, 500)

          val attributes = AudioAttributes.Builder()
              .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
              .setUsage(AudioAttributes.USAGE_NOTIFICATION)
              .build()
          channel.setSound(customSoundUri, attributes)
          channel.enableVibration(true)
          channel.vibrationPattern = vibration

          val notificationManager =
              getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
          notificationManager.createNotificationChannel(channel)
        }
    }
}