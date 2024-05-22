package com.taposek322.smstomail.mail.data.database.smtp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SmtpData(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    val host:String,
    val port:String,
    val username: String,
    val password: String,
    val authenticationRequired: Boolean,
    val defaultMail:Boolean
)
