package com.taposek322.smstomail.mail.data.repository

import android.util.Log
import com.taposek322.smstomail.mail.data.database.MailDatabase
import com.taposek322.smstomail.mail.data.database.filters.FilterWithMail
import com.taposek322.smstomail.mail.data.database.filters.FiltersData
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData
import com.taposek322.smstomail.mail.domain.repository.MailRepository
import com.taposek322.smstomail.sms.domain.sms.SmsData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

private const val TAG = "MailRepositoryImpl"

class MailRepositoryImpl @Inject constructor(private val db:MailDatabase): MailRepository {

    override suspend fun insertInFiltersTable(filterData: FiltersData)  = db.filtersDao().insertIn(filterData)

    override suspend fun deleteFromFiltersTable(filterData: FiltersData)  = db.filtersDao().delete(filterData)

    override suspend fun updateFiltersTable(filterData: FiltersData)  = db.filtersDao().updateData(filterData)

    override suspend fun selectAllFiltersTable(): Flow<List<FilterWithMail>> = db.filtersDao().selectAll()

    override suspend fun getFiltersData(id: Int): Flow<FiltersData> = db.filtersDao().getFiltersData(id = id)

    override suspend fun insertInSmtpTable(smtpData: SmtpData)  = db.smtpDao().insertIn(smtpData)

    override suspend fun deleteFromSmtpTable(smtpData: SmtpData)  = db.smtpDao().delete(smtpData)

    override suspend fun updateSmtpTable(smtpData: SmtpData)  = db.smtpDao().updateData(smtpData)

    override suspend fun selectAllFromSmtpTable(): Flow<List<SmtpData>> = db.smtpDao().selectAll()

    override suspend fun getDefaultFromSmtpTable(): Flow<SmtpData?> = db.smtpDao().getDefault()

    override suspend fun getSmtpData(id: Int): Flow<SmtpData> = db.smtpDao().getMailData(id = id)

    override suspend fun sendMail(smtpData: SmtpData, smsData: SmsData) {
        try {

            val prop = System.getProperties().also {

                it["mail.smtp.host"] = smtpData.host
                it["mail.smtp.port"] = smtpData.port
                it["mail.smtp.starttls.enable"] = "true"
                if(smtpData.authenticationRequired) {
                    it["mail.smtp.auth"] = "true"
                }
            }

            val session =if (smtpData.authenticationRequired) {
                Session.getInstance(prop, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(
                            smtpData.username,
                            smtpData.password
                        )
                    }
                })
            }else{
                Session.getInstance(prop)
            }
            try {

                val message = MimeMessage(session).also {
                    it.setFrom(InternetAddress(smtpData.username))
                    it.addRecipients(Message.RecipientType.TO, smtpData.username)
                    it.subject = "SmsToMail notification. Sms from ${smsData.sender}"
                    it.setText("Sms from ${smsData.sender}.\nText:\n${smsData.message}")
                }

                val transport = session.getTransport("smtp")
                transport.connect()
                Transport.send(message)
                transport.close()
                Log.d(TAG,"Email send")

            } catch (ex: Exception) {
                Log.e(
                    TAG,
                    ex.message ?: "Error happened"
                )
            }
        }catch (ex:Exception){
            Log.e(TAG,ex.message?:"Error in func sendMail in MailRepositoryImpl")
        }
    }
}