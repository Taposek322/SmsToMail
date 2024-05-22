package com.taposek322.smstomail.mail.presentation.filters.ui

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
import com.taposek322.smstomail.mail.presentation.filters.viewmodel.FilterShowViewModel
import com.taposek322.smstomail.presentation.navigation.NavRoutes
import com.taposek322.smstomail.mail.presentation.smtp.viewmodel.MailShowViewModel
import com.taposek322.smstomail.mail.presentation.ui.SwipeToDelete

@Composable
fun FiltersList(
    context: Context,
    viewModel: FilterShowViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
){
    val filtersWithMail by viewModel.filters.collectAsState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(items = filtersWithMail, key = {it.filter.id}){data->
            SwipeToDelete(context = context, item = filtersWithMail, onDelete ={viewModel.deleteFilter(data.filter)} ) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("${NavRoutes.CreateFilter}/${data.filter.id}")
                        }

                ) {
                    FilterContainer(context = context, filterWithMail = data)
                }
            }
        }
    }
}