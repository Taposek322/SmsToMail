package com.taposek322.smstomail.mail.presentation.smtp.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.taposek322.smstomail.presentation.navigation.NavRoutes
import com.taposek322.smstomail.mail.presentation.smtp.viewmodel.MailShowViewModel
import com.taposek322.smstomail.mail.presentation.ui.SwipeToDelete

@Composable
fun MailsList(
    context: Context,
    viewModel: MailShowViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
){
    val emails by viewModel.emails.collectAsState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(items = emails, key = {it.id}){smtpData->
            SwipeToDelete(context = context, item = smtpData, onDelete ={viewModel.deleteSmtp(smtpData)} ) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("${NavRoutes.CreateMail}/${smtpData.id}")
                        }

                ) {
                    MailContainer(context = context, smtpData = it)
                }
            }
        }
    }
}