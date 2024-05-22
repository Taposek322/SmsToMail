package com.taposek322.smstomail.sms.presentation.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.provider.Telephony
import android.util.Log
import androidx.core.app.NotificationCompat
import com.taposek322.smstomail.MainActivity
import com.taposek322.smstomail.R
import com.taposek322.smstomail.presentation.navigation.NavRoutes
import com.taposek322.smstomail.sms.data.broadcastreciver.SmsReceiver
import com.taposek322.smstomail.sms.domain.repository.SmsRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SmsListenerService:Service() {

    @Inject
    lateinit var smsRepository: SmsRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action.toString()){
            ServiceActions.Start.toString()-> startService()
            ServiceActions.Stop.toString()-> {
                stopService()
                stopSelf()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService()
    }

    private fun startService(){

        smsRepository.startBroadcastReceiver(this)

        val intent = Intent(this,MainActivity::class.java)
            .also { it.action = Intent.ACTION_MAIN }
            .putExtra(NavRoutes.IntentRoute,NavRoutes.Service)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, ServiceObject.channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_text))
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1,notification)
    }

    private fun stopService(){
        smsRepository.stopBroadcastReceiver(this)
    }
}