package com.taposek322.smstomail.mail.presentation.filters.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taposek322.smstomail.R
import com.taposek322.smstomail.mail.data.database.filters.FilterWithMail

@Composable
fun FilterContainer(context: Context, filterWithMail: FilterWithMail, modifier: Modifier = Modifier){
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
                    text = context.getString(R.string.filter_source)
                )
                Text(
                    text = filterWithMail.filter.source,
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
                    text = context.getString(R.string.filters_mailUsername)
                )
                Text(
                    text = filterWithMail.mail.username,
                    modifier = modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}
