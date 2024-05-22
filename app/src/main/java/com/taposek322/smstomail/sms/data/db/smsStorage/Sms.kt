package com.taposek322.smstomail.sms.data.db.smsStorage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sms(
    val sender:String?,
    val message:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
