package com.taposek322.smstomail.sms.domain.repository

import android.content.Context
import com.taposek322.smstomail.sms.domain.sms.SmsData

interface SmsRepository {

    fun startBroadcastReceiver(context: Context)

    fun stopBroadcastReceiver(context: Context)

    suspend fun storeSms(smsData: SmsData)

    suspend fun getSms():List<SmsData>?
}