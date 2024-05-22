package com.taposek322.smstomail.mail.presentation.filters.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taposek322.smstomail.presentation.navigation.NavRoutes
import com.taposek322.smstomail.R
import com.taposek322.smstomail.mail.presentation.filters.viewmodel.FilterCreateViewModel


private const val TAG = "CreateFilter"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFilter(
    context: Context,
    filterId:Int,
    navController: NavController,
    modifier: Modifier = Modifier
)
{

    val viewModel:FilterCreateViewModel = hiltViewModel()

    val filter = viewModel.filter.collectAsState()

    val mails = viewModel.mails.collectAsState()


    var expanded by rememberSaveable {
        mutableStateOf(false)
    }


    if(filterId!=-1 && !viewModel.searchCompleted) {
        viewModel.getFilterData(filterId)
    }

    var sourceError by rememberSaveable {
        mutableStateOf(false)
    }


    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Text(
                modifier = modifier
                    .padding(2.dp),
                text = context.getString(R.string.filter_source)
            )
            OutlinedTextField(
                value = filter.value.source,
                onValueChange = {viewModel.updateSource(it)},
                isError = sourceError,
                supportingText = {
                                 if(sourceError){
                                     Text(
                                         text = context.getString(R.string.filter_source_field_error),
                                         modifier = modifier,
                                         color = MaterialTheme.colorScheme.error
                                     )
                                 }
                },
                trailingIcon = {
                               if(sourceError){
                                   Icon(
                                       imageVector = Icons.Filled.Info,
                                       contentDescription = context.getString(R.string.filter_source_field_error),
                                       tint = MaterialTheme.colorScheme.error
                                   )
                               }
                },
                modifier = modifier
                    .padding(2.dp)
                    .fillMaxWidth()
            )
        }

        Column(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Text(
                modifier = modifier
                    .padding(2.dp),
                text = context.getString(R.string.filters_mailUsername)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    Log.d(TAG,"In expanded")
                    if(mails.value.isNotEmpty()) {
                        Log.d(TAG,"In expanded if")
                        expanded = !expanded
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                TextField(
                    readOnly = true,
                    value = if(mails.value.isNotEmpty())
                    {
                        mails.value.find { it.id == filter.value.smtpID }?.username?:mails.value.first().username
                    }else
                    {
                        context.getString(R.string.filter_create_no_mails)
                    },
                    onValueChange = { },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                    modifier = modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    mails.value.forEach{smtpData->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = smtpData.username,
                                    modifier = modifier
                                        .padding(2.dp)
                                )
                            },
                            onClick = {
                                viewModel.updateMailId(smtpData.id)
                                expanded = false
                            },
                            modifier = modifier
                        )
                    }
                }
            }

        }


        Button(
            enabled = (filter.value.smtpID!=-1),
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                if(filter.value.source.isNotEmpty()) {
                    sourceError = false
                    if (filterId != -1) {
                        viewModel.updateFilter()
                    } else {
                        viewModel.insertFilter()
                    }
                    navController.navigate(NavRoutes.ShowFilters) {
                        popUpTo(NavRoutes.ShowFilters) {
                            inclusive = true
                        }
                    }
                }else{
                    sourceError = true
                }
            }
        ) 
        {
           Text(
               text = context.getString(R.string.filter_create_button),
               modifier = modifier
           )
        }
    }
}




