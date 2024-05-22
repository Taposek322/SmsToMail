package com.taposek322.smstomail

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.taposek322.smstomail.sms.presentation.service.ServiceObject
import dagger.hilt.android.HiltAndroidApp
import java.util.prefs.Preferences

@HiltAndroidApp
class SMStoMailApplication:Application(){
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_MIN
            val channel = NotificationChannel(ServiceObject.channelId,name,importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}