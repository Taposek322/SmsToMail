package com.taposek322.smstomail.mail.presentation.smtp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData
import com.taposek322.smstomail.mail.domain.repository.MailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MailCreateViewModel @Inject constructor(private val mailRepository: MailRepository):ViewModel() {

    init {
        isDefaultMailExist()
    }

    private var _defaultMailID:Int? = null
    val defaultMailID
        get() = _defaultMailID

    private var _searchComplete = false
    val searchComplete
        get() = _searchComplete

    private val _email: MutableStateFlow<SmtpData> = MutableStateFlow(
        SmtpData(
            host = "",
            port = "",
            username = "",
            password = "",
            authenticationRequired = false,
            defaultMail = false)
    )
    val email = _email.asStateFlow()

    fun updateHost(host:String){
        _email.update {
            it.copy(host = host)
        }
    }

    fun updatePort(port:String){
        _email.update {
            it.copy(port = port)
        }
    }

    fun updateUsername(username:String){
        _email.update {
            it.copy(username = username)
        }
    }

    fun updatePassword(password:String){
        _email.update {
            it.copy(password = password)
        }
    }

    fun updateAuthRequired(authRequired: Boolean){
        _email.update {
            it.copy(authenticationRequired = authRequired)
        }
    }

    fun updateDefaultMail(defaultMail:Boolean){
        _email.update {
            it.copy(defaultMail = defaultMail)
        }
    }

    fun getSmtpData(id:Int){
       viewModelScope.launch(Dispatchers.IO) {
            mailRepository.getSmtpData(id).collect{smtpData->
               _email.update {
                   Log.d("CreateMail","SmtpData: $smtpData")
                   smtpData
               }
            }
        }
        _searchComplete = true
    }

   private fun isDefaultMailExist(){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.getDefaultFromSmtpTable().collect{
                it?.let {
                    _defaultMailID = it.id
                }
            }
        }
    }

    fun insertSmtp(){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.insertInSmtpTable(email.value)
        }
    }

    fun updateSmtp(){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.updateSmtpTable(email.value)
        }
    }
}