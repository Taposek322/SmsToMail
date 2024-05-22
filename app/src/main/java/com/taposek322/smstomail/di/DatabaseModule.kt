package com.taposek322.smstomail.di

import android.app.Application
import androidx.room.Room
import com.taposek322.smstomail.mail.data.database.MailDatabase
import com.taposek322.smstomail.mail.data.database.MailDatabaseObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun bindDatabase(application: Application): MailDatabase {
        return Room.databaseBuilder(application, MailDatabase::class.java,
            MailDatabaseObject.databaseName).build()
    }

}