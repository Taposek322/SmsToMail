package com.taposek322.smstomail.sms.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taposek322.smstomail.sms.data.db.smsStorage.SmsDatabase
import com.taposek322.smstomail.sms.data.db.smsStorage.SmsDatabaseObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class SmsDatastoreModule {

    @Provides
    fun bindSmsDatabase(application: Application):SmsDatabase{
        return Room.databaseBuilder(application,SmsDatabase::class.java,SmsDatabaseObject.databaseName).build()
    }
}