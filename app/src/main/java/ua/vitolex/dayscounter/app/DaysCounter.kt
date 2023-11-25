package ua.vitolex.dayscounter.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DaysCounter : Application(){

    override fun onCreate() {
        super.onCreate()
    }
}