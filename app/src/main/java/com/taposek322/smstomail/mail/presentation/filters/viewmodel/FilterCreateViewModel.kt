package com.taposek322.smstomail.mail.presentation.filters.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taposek322.smstomail.mail.data.database.filters.FiltersData
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData
import com.taposek322.smstomail.mail.domain.repository.MailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FilterCreateViewModel @Inject constructor(private val mailRepository: MailRepository):ViewModel() {

    init {
        getMails()
    }

    private var _searchCompleted = false
    val searchCompleted
        get() = _searchCompleted

    private val _mails: MutableStateFlow<List<SmtpData>> = MutableStateFlow(emptyList())
    val mails = _mails.asStateFlow()


    private val _filter: MutableStateFlow<FiltersData> = MutableStateFlow(
        FiltersData(
            source = "",
            smtpID = -1
            )
    )
    val filter = _filter.asStateFlow()

    fun updateSource(source:String){
        _filter.update {
            it.copy(source = source)
        }
    }

    fun updateMailId(smtpId:Int){
        _filter.update {
            it.copy(smtpID = smtpId)
        }
    }


    fun getFilterData(id:Int){
       viewModelScope.launch(Dispatchers.IO) {
            mailRepository.getFiltersData(id).collect{data->
               _filter.update {
                   data
               }
            }
       }
        _searchCompleted = true
    }

    fun insertFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.insertInFiltersTable(filter.value)
        }
    }

    fun updateFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.updateFiltersTable(filter.value)
        }
    }

    private fun getMails(){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.selectAllFromSmtpTable().collect{data->
                _mails.update {
                    data
                }
                if(mails.value.isNotEmpty()) {
                    _filter.update {
                        _filter.value.copy(smtpID = _mails.value.first().id)
                    }
                }
            }
        }
    }
}