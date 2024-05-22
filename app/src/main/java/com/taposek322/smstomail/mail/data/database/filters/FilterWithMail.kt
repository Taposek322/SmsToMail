package com.taposek322.smstomail.mail.data.database.filters

import androidx.room.Embedded
import androidx.room.Relation
import com.taposek322.smstomail.mail.data.database.filters.FiltersData
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData

data class FilterWithMail(
    @Embedded
    var filter:FiltersData,

    @Relation(
        parentColumn = "smtpID",
        entityColumn = "id"
    )
    var mail:SmtpData,
 )
