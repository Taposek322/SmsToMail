package com.taposek322.smstomail.presentation.navigation

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.taposek322.smstomail.sms.domain.navigation.NavigationElem

@Composable
fun BottomNavigationBar(navController: NavController,navigationList:List<NavigationElem>){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        navigationList.forEach{ item->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any{
                    it.route == item.graphRoute
                } == true,
                onClick = {
                    navController.navigate(item.graphRoute){
                        Log.d("bottomnav","StartDestination: ${navController.graph.findStartDestination().route}")
                        popUpTo(navController.graph.findStartDestination().id){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector =  if (currentDestination?.hierarchy?.any{it.route == item.graphRoute} == true) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                })
        }
    }
}