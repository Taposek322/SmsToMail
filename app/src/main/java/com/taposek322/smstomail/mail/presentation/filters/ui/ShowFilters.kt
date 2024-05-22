package com.taposek322.smstomail.mail.presentation.filters.ui

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
import com.taposek322.smstomail.mail.presentation.filters.viewmodel.FilterShowViewModel
import com.taposek322.smstomail.mail.presentation.smtp.viewmodel.MailShowViewModel

@Composable
fun ShowFilters(
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: FilterShowViewModel,
    navController: NavController
){
    Column {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("${NavRoutes.CreateFilter}/-1") }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = context.getString(R.string.filters_add_button)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {innerPadding->
            FiltersList(
                context = context,
                viewModel = viewModel,
                modifier = modifier.padding(innerPadding),
                navController = navController
            )
        }

    }
}
