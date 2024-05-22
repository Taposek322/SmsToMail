package com.taposek322.smstomail.mail.presentation.smtp.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taposek322.smstomail.R
import com.taposek322.smstomail.mail.data.database.smtp.SmtpData

@Composable
fun MailContainer(context: Context, smtpData: SmtpData, modifier: Modifier = Modifier){
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically),
                    text = context.getString(R.string.smtp_server_name)
                )
                Text(
                    text = smtpData.host,
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically),
                    text = context.getString(R.string.smtp_port_name)
                )
                Text(
                    text = smtpData.port,
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically),
                    text = context.getString(R.string.smtp_username_name)
                )
                Text(
                    text = smtpData.username,
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically),
                    text = context.getString(R.string.smtp_auth_required)
                )
                Checkbox(
                    checked = smtpData.authenticationRequired,
                    onCheckedChange = {},
                    enabled = false,
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
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
                    checked = smtpData.defaultMail,
                    onCheckedChange = {},
                    enabled = false,
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically)
                )
            }

        }
    }
}
