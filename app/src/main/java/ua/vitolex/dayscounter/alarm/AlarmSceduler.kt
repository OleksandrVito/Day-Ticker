package ua.vitolex.notetaker.alarm

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel()
}