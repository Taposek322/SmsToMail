package com.taposek322.smstomail.sms.presentation.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.taposek322.smstomail.R
import com.taposek322.smstomail.sms.presentation.viewmodel.ServiceViewModel

@Composable
fun StartService(
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel,
){
    val state = viewModel.serviceState.collectAsState()

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        if(!state.value){
            Button(
                modifier = modifier,
                onClick = {
                viewModel.startSmsService(context)
                viewModel.updateServiceState(true)
            }) {
                Text(
                    modifier = modifier
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    text = context.getString(R.string.start_service)
                )
            }
        }else{
            Button(
                modifier = modifier,
                onClick = {
                    viewModel.stopSmsService(context)
                    viewModel.updateServiceState(false)
                }) {
                    Text(
                        modifier = modifier
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        text = context.getString(R.string.stop_service)
                    )
            }
        }
    }
}