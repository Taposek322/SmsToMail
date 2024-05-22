package com.taposek322.smstomail.mail.data.database.smtp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SmtpDatabaseDao {

    @Insert
    fun insertIn(smtpData: SmtpData)

    @Update
    fun updateData(smtpData: SmtpData)

    @Delete
    fun delete(smtpData: SmtpData)

    @Query("SELECT * FROM smtpdata ORDER BY defaultMail desc")
    fun selectAll(): Flow<List<SmtpData>>

    @Query("SELECT * from smtpdata where id = :id")
    fun getMailData(id:Int):Flow<SmtpData>

    @Query("SELECT * FROM smtpdata WHERE defaultMail = 1")
    fun getDefault():Flow<SmtpData?>
}