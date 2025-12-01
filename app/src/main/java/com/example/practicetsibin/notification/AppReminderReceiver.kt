package com.example.practicetsibin.notification

import com.example.practicetsibin.MainActivity
import com.example.practicetsibin.R
import com.example.practicetsibin.profile.notification.ReminderReceiver

class AppReminderReceiver : ReminderReceiver() {
    override fun getMainActivityClass(): Class<*> = MainActivity::class.java
    override fun getNotificationIconResId(): Int = R.drawable.ic_notification
}

