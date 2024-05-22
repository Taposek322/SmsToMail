package com.taposek322.smstomail.sms.data.repository

import android.content.Context
import android.content.IntentFilter
import android.provider.Telephony
import com.taposek322.smstomail.sms.data.broadcastreciver.SmsReceiver
import com.taposek322.smstomail.sms.data.db.smsStorage.Sms
import com.taposek322.smstomail.sms.data.db.smsStorage.SmsDatabase
import com.taposek322.smstomail.sms.domain.repository.SmsRepository
import com.taposek322.smstomail.sms.domain.sms.SmsData
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(
    private val smsDatabase: SmsDatabase):SmsRepository  {

    private var smsReceiver: SmsReceiver? = null

    override fun startBroadcastReceiver(context:Context) {
        smsReceiver = SmsReceiver()
        val smsFilter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        context.registerReceiver(smsReceiver,smsFilter)
    }

    override fun stopBroadcastReceiver(context: Context) {
        smsReceiver?.let {
            context.unregisterReceiver(smsReceiver)
            smsReceiver = null
        }
    }


    override suspend fun storeSms(smsData: SmsData) = smsDatabase.smsDatabaseDao().setSms(
        Sms(smsData.sender,smsData.message)
    )

    override suspend fun getSms(): List<SmsData>?{
        val smsList = smsDatabase.smsDatabaseDao().getSms()?.map { sms->
            SmsData(sms.sender,sms.message)
        }
        smsList?.let {
            smsDatabase.clearAllTables()
        }
        return smsList
    }
}