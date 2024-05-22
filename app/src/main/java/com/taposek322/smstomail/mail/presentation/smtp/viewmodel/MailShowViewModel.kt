package com.taposek322.smstomail.mail.presentation.smtp.viewmodel

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
class MailShowViewModel @Inject constructor(private val mailRepository: MailRepository):ViewModel() {

    private val _emails: MutableStateFlow<List<SmtpData>> = MutableStateFlow(emptyList())
    val emails = _emails.asStateFlow()

    init {
        getSmtpFromTable()
    }

    private fun getSmtpFromTable(){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.selectAllFromSmtpTable().collect{data->
                _emails.update {
                    data
                }
            }
        }
    }

    fun deleteSmtp(smtpData: SmtpData){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.deleteFromSmtpTable(smtpData)
        }
    }

}