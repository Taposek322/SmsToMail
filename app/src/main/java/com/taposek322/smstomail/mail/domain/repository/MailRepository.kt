package com.taposek322.smstomail.mail.domain.repository

import com.taposek322.smstomail.mail.data.database.filters.FilterWithMail
import com.taposek322.smstomail.mail.data.database.filters.FiltersData
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData
import com.taposek322.smstomail.sms.domain.sms.SmsData
import kotlinx.coroutines.flow.Flow

interface MailRepository {
    suspend fun insertInFiltersTable(filterData: FiltersData)

    suspend fun deleteFromFiltersTable(filterData: FiltersData)

    suspend fun updateFiltersTable(filterData: FiltersData)

    suspend fun selectAllFiltersTable(): Flow<List<FilterWithMail>>

    suspend fun getFiltersData(id:Int): Flow<FiltersData>

    suspend fun insertInSmtpTable(smtpData: SmtpData)

    suspend fun deleteFromSmtpTable(smtpData: SmtpData)

    suspend fun updateSmtpTable(smtpData: SmtpData)

    suspend fun selectAllFromSmtpTable(): Flow<List<SmtpData>>

    suspend fun getDefaultFromSmtpTable(): Flow<SmtpData?>

    suspend fun getSmtpData(id:Int): Flow<SmtpData>

    suspend fun sendMail(smtpData: SmtpData, smsData: SmsData)
}