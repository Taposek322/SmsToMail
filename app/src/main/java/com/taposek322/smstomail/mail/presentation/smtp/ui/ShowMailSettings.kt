package com.taposek322.smstomail.mail.presentation.smtp.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.taposek322.smstomail.presentation.navigation.NavRoutes
import com.taposek322.smstomail.R
import com.taposek322.smstomail.mail.presentation.smtp.viewmodel.MailShowViewModel

@Composable
fun ShowMailSettings(
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: MailShowViewModel,
    navController: NavController
){
    Column {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("${NavRoutes.CreateMail}/-1") }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = context.getString(R.string.smtp_add_button_description)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {innerPadding->
            MailsList(
                context = context,
                viewModel = viewModel,
                modifier = modifier.padding(innerPadding),
                navController = navController
            )
        }

    }
}
