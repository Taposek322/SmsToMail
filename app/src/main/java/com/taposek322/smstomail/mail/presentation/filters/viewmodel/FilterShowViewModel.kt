package com.taposek322.smstomail.mail.presentation.filters.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData
import com.taposek322.smstomail.mail.data.database.filters.FilterWithMail
import com.taposek322.smstomail.mail.data.database.filters.FiltersData
import com.taposek322.smstomail.mail.domain.repository.MailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterShowViewModel @Inject constructor(private val mailRepository: MailRepository):ViewModel() {

    private val _filters: MutableStateFlow<List<FilterWithMail>> = MutableStateFlow(emptyList())
    val filters = _filters.asStateFlow()

    init {
        getFiltersFromTable()
    }

    private fun getFiltersFromTable(){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.selectAllFiltersTable().collect{data->
                _filters.update {
                    data
                }
            }
        }
    }

    fun deleteFilter(filterData: FiltersData){
        viewModelScope.launch(Dispatchers.IO) {
            mailRepository.deleteFromFiltersTable(filterData)
        }
    }

}