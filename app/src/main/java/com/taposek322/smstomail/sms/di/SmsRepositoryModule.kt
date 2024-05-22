package com.taposek322.smstomail.sms.di

import com.taposek322.smstomail.sms.data.repository.SmsRepositoryImpl
import com.taposek322.smstomail.sms.domain.repository.SmsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SmsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSmsRepository(smsRepositoryImpl: SmsRepositoryImpl):SmsRepository
}