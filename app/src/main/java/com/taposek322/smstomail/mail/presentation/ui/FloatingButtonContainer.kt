package com.taposek322.smstomail.mail.presentation.ui

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.taposek322.smstomail.R

@Composable
fun FloatingButtonContainer(
    context: Context,
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Surface {
        content()
        FloatingActionButton(
            modifier = modifier,
                //.align(Alignment.Bottom),
            onClick = { onClick()
            }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = context.getString(R.string.smtp_add_button_description))
        }
    }
}