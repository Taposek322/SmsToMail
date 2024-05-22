package com.taposek322.smstomail.sms.data.db.smsStorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SmsDao {
    @Insert
    fun setSms(sms:Sms)

    @Query("SELECT * FROM sms")
    fun getSms():List<Sms>?
}