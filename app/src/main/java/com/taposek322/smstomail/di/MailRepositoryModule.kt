package com.taposek322.smstomail.di

import com.taposek322.smstomail.mail.data.repository.MailRepositoryImpl
import com.taposek322.smstomail.mail.domain.repository.MailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MailRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMailRepository(mailRepositoryImpl: MailRepositoryImpl): MailRepository

}