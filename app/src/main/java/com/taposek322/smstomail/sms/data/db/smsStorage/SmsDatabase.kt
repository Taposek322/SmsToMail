package com.taposek322.smstomail.sms.data.db.smsStorage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Sms::class], version = 1)
abstract class SmsDatabase:RoomDatabase() {

    abstract fun smsDatabaseDao(): SmsDao
}