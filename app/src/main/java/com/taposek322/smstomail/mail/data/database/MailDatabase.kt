package com.taposek322.smstomail.mail.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taposek322.smstomail.mail.data.database.filters.FiltersDao
import com.taposek322.smstomail.mail.data.database.filters.FiltersData
import com.taposek322.smstomail.mail.data.database.smtp.SmtpDatabaseDao
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData

@Database(entities = [FiltersData::class,SmtpData::class], version = 1)
    abstract class MailDatabase: RoomDatabase() {
        abstract fun smtpDao() : SmtpDatabaseDao
        abstract fun filtersDao() : FiltersDao
}