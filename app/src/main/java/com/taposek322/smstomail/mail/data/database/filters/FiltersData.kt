package com.taposek322.smstomail.mail.data.database.filters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData
import java.util.UUID

@Entity(
    foreignKeys = arrayOf( ForeignKey(
            entity = SmtpData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("smtpID"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class FiltersData(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,

    val source:String,

    @ColumnInfo(name = "smtpID", index = true)
    val smtpID:Int
)