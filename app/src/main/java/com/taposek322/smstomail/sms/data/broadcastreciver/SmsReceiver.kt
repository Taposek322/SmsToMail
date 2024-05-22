package com.taposek322.smstomail.sms.data.broadcastreciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Telephony
import android.telephony.TelephonyManager
import android.util.Log
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.taposek322.smstomail.mail.data.database.filters.FilterWithMail
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData
import com.taposek322.smstomail.mail.domain.repository.MailRepository
import com.taposek322.smstomail.sms.domain.repository.SmsRepository
import com.taposek322.smstomail.sms.domain.sms.SmsData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver:BroadcastReceiver() {

    @Inject
    lateinit var mailRepository: MailRepository


    @Inject
    lateinit var smsRepository: SmsRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)){
            val smsMessage = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (sms in smsMessage)
            {
                val smsData = SmsData(
                    sender = sms.originatingAddress,
                    message = sms.messageBody
                )

                val filters = MutableStateFlow<List<FilterWithMail>>(emptyList())
                CoroutineScope(Dispatchers.IO).launch{
                    mailRepository.selectAllFiltersTable().collect{filtersWithMail->
                        filters.update {
                            filtersWithMail
                        }
                    }
                }

                val defaultMail = MutableStateFlow<SmtpData?>(null)
                CoroutineScope(Dispatchers.IO).launch {
                    mailRepository.getDefaultFromSmtpTable().collect{smtpData->
                        defaultMail.update {
                            smtpData
                        }
                    }
                }

                val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val currentNetwork = connectivityManager.activeNetwork
                if (currentNetwork != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val smsList = smsRepository.getSms()
                        smsList?.let {
                            it.forEach { smsDataStored ->
                                send(smsData = smsDataStored,filters = filters.value,defaultMail = defaultMail.value)
                            }
                        }
                        send(smsData = smsData,filters = filters.value,defaultMail = defaultMail.value)
                    }
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        smsRepository.storeSms(smsData)
                    }
                }
            }
        }
    }

    private fun send(smsData: SmsData, filters: List<FilterWithMail>,defaultMail: SmtpData?){

        val filter = filters.find { filtersWithMail ->
            smsData.sender == filtersWithMail.filter.source
        }

        val mailData = filter?.mail?:defaultMail

        mailData?.let {smtpData->
            CoroutineScope(Dispatchers.IO).launch {
                mailRepository.sendMail(smtpData, smsData)
            }
        }

    }
}