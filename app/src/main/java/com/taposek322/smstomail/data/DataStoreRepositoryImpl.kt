package com.taposek322.smstomail.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.taposek322.smstomail.domain.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

import javax.inject.Inject


class DataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>):DataStoreRepository {


   private val serviceState: Flow<Boolean> = dataStore.data.map {
       it[ServiceStateKey]?:false
   }.distinctUntilChanged()

    override suspend fun getServiceState(): Flow<Boolean> {
        return serviceState
    }

    override suspend fun setServiceState(state: Boolean) {
        dataStore.edit {
            it[ServiceStateKey] = state
        }
    }

    companion object{
        private val ServiceStateKey = booleanPreferencesKey("serviceStateKey")
    }
}