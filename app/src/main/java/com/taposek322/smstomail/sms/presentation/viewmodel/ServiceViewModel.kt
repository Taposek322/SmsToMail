package com.taposek322.smstomail.sms.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.taposek322.smstomail.domain.DataStoreRepository
import com.taposek322.smstomail.sms.domain.repository.SmsRepository
import com.taposek322.smstomail.sms.presentation.service.ServiceActions
import com.taposek322.smstomail.sms.presentation.service.SmsListenerService
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository):ViewModel() {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreRepository.getServiceState().collect { state ->
                _serviceState.update {
                    state
                }
            }
        }
    }

    private var _serviceState = MutableStateFlow(false)
    val serviceState
        get() = _serviceState

    fun startSmsService(context: Context){
        Intent(context, SmsListenerService::class.java).also {
            it.action = ServiceActions.Start.toString()
            context.startService(it)
        }
    }

    fun stopSmsService(context: Context){
        Intent(context, SmsListenerService::class.java).also {
            it.action = ServiceActions.Stop.toString()
            context.startService(it)
        }
    }

    fun updateServiceState(state:Boolean){
        _serviceState.update {
            state
        }
    }

    fun setServiceState(){
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreRepository.setServiceState(serviceState.value)
        }
    }
}