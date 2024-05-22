package com.taposek322.smstomail.mail.presentation.smtp.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.taposek322.smstomail.presentation.navigation.NavRoutes
import com.taposek322.smstomail.R
import com.taposek322.smstomail.mail.presentation.smtp.viewmodel.MailCreateViewModel

private const val TAG = "CreateMail"

@Composable
fun CreateMail(
    context: Context,
    mailId:Int,
    navController: NavController,
    modifier: Modifier = Modifier
)
{

    val viewModel:MailCreateViewModel = hiltViewModel()

    val email = viewModel.email.collectAsState()

    val verticalStroller = rememberScrollState()

    if(mailId!=-1 && !viewModel.searchComplete) {
        viewModel.getSmtpData(mailId)
    }

    var hostError by remember{
        mutableStateOf(false)
    }

    var portError by remember{
        mutableStateOf(false)
    }

    var usernameError by remember{
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .verticalScroll(verticalStroller, enabled = true)
            .padding(8.dp)
    ) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Text(
                modifier = modifier
                    .padding(2.dp),
                text = context.getString(R.string.smtp_server_name)
            )
            OutlinedTextField(
                value = email.value.host,
                onValueChange = {viewModel.updateHost(it)},
                isError = hostError,
                supportingText = {
                    if(hostError){
                        Text(
                            text = context.getString(R.string.mail_host_field_error),
                            modifier = modifier,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if(hostError){
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = context.getString(R.string.mail_host_field_error),
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
                text = context.getString(R.string.smtp_port_name)
            )

            OutlinedTextField(
                value = email.value.port,
                onValueChange = {viewModel.updatePort(it)},
                isError = portError,
                supportingText = {
                    if(portError){
                        Text(
                            text = context.getString(R.string.mail_port_field_error),
                            modifier = modifier,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if(portError){
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = context.getString(R.string.mail_port_field_error),
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
                text = context.getString(R.string.smtp_username_name)
            )
            OutlinedTextField(
                value = email.value.username,
                onValueChange = {viewModel.updateUsername(it)},
                isError = usernameError,
                supportingText = {
                    if(usernameError){
                        Text(
                            text = context.getString(R.string.mail_username_field_error),
                            modifier = modifier,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if(usernameError){
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = context.getString(R.string.mail_username_field_error),
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
                text = context.getString(R.string.smtp_password_name)
            )
            OutlinedTextField(
                value = email.value.password,
                onValueChange = {viewModel.updatePassword(it)},
                modifier = modifier
                    .padding(2.dp)
                    .fillMaxWidth()
            )
        }

        Row(
            modifier = modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = context.getString(R.string.smtp_auth_required),
                modifier = modifier
                    .padding(2.dp)
                    .align(Alignment.CenterVertically)
            )
            Checkbox(
                checked = email.value.authenticationRequired,
                onCheckedChange = {viewModel.updateAuthRequired(it)},
                modifier = modifier
                    .padding(2.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        if((viewModel.defaultMailID==null) || (email.value.id == viewModel.defaultMailID)){
            Row(
                modifier = modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = context.getString(R.string.smtp_default_mail),
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically)
                )
                Checkbox(
                    checked = email.value.defaultMail,
                    onCheckedChange = { viewModel.updateDefaultMail(it) },
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Button(
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                if (email.value.host.isNotEmpty() && email.value.port.isNotEmpty() &&
                    email.value.username.isNotEmpty())
                {
                    if(hostError){
                        hostError = false
                    }

                    if(portError){
                        portError = false
                    }

                    if(usernameError){
                        usernameError = false
                    }

                    if (mailId != -1) {
                        viewModel.updateSmtp()
                    } else {
                        viewModel.insertSmtp()
                    }
                    navController.navigate(NavRoutes.ShowMails) {
                        popUpTo(NavRoutes.ShowMails) {
                            inclusive = true
                        }
                    }
                }else{
                    hostError = email.value.host.isEmpty()

                    portError = email.value.port.isEmpty()

                    usernameError = email.value.username.isEmpty()
                }
            }
        ) 
        {
           Text(
               text = context.getString(R.string.mail_create_button),
               modifier = modifier
           )
        }
    }
}


@Preview
@Composable
private fun CreateMailPreview(){
    val navController = rememberNavController()
    CreateMail(context = LocalContext.current, mailId = -1, navController = navController)
}




