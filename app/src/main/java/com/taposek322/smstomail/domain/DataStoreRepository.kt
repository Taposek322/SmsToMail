package com.taposek322.smstomail.domain

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun getServiceState(): Flow<Boolean>

    suspend fun setServiceState(state:Boolean)
}