package com.taposek322.smstomail

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taposek322.smstomail.mail.presentation.filters.ui.CreateFilter
import com.taposek322.smstomail.mail.presentation.filters.ui.ShowFilters
import com.taposek322.smstomail.mail.presentation.filters.viewmodel.FilterShowViewModel
import com.taposek322.smstomail.mail.presentation.smtp.ui.CreateMail
import com.taposek322.smstomail.mail.presentation.smtp.ui.ShowMailSettings
import com.taposek322.smstomail.mail.presentation.smtp.viewmodel.MailShowViewModel
import com.taposek322.smstomail.presentation.navigation.BottomNavigationBar
import com.taposek322.smstomail.presentation.navigation.NavRoutes
import com.taposek322.smstomail.presentation.ui.theme.SMStoMailTheme
import com.taposek322.smstomail.sms.domain.navigation.NavigationElem
import com.taposek322.smstomail.sms.presentation.ui.StartService
import com.taposek322.smstomail.sms.presentation.viewmodel.ServiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
private const val STATEKEY = "statekey"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var serviceIntent = MutableStateFlow<Intent?>(null)

    private val serviceViewModel: ServiceViewModel by viewModels<ServiceViewModel>()
    private val mailShowViewModel: MailShowViewModel by viewModels<MailShowViewModel>()
    private val filterShowViewModel:FilterShowViewModel by viewModels<FilterShowViewModel>()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {int->
            Log.d("MainActivity","onNewIntent.Action: ${int.action}")
            serviceIntent.update {
                int
            }
        }
    }


    override fun onDestroy() {
        serviceViewModel.setServiceState()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity","Bundle is null: ${savedInstanceState == null}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS),0)
        }

        val navigationList = listOf(
            NavigationElem(
                title = getString(R.string.service_bar),
                selectedIcon = Icons.Filled.Send,
                unselectedIcon = Icons.Outlined.Send,
                graphRoute = NavRoutes.Service
            ),
            NavigationElem(
                title = getString(R.string.filters_bar),
                selectedIcon = Icons.Filled.List,
                unselectedIcon = Icons.Outlined.List,
                graphRoute = NavRoutes.Filters
            ),
            NavigationElem(
                title = getString(R.string.email_bars),
                selectedIcon = Icons.Filled.Email,
                unselectedIcon = Icons.Outlined.Email,
                graphRoute = NavRoutes.MailSettings
            )
        )


        setContent {

            val navController = rememberNavController()

            val int = serviceIntent.collectAsState()

            SMStoMailTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController,navigationList)
                    },
                ) {innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(bottom = innerPadding.calculateBottomPadding())
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        NavHost(navController = navController, startDestination = NavRoutes.Service ){

                            navigation(route = NavRoutes.Service, startDestination = NavRoutes.StartService){

                                composable(route = NavRoutes.StartService){  StartService(context = applicationContext, viewModel = serviceViewModel) }

                            }

                            navigation(route = NavRoutes.Filters, startDestination = NavRoutes.ShowFilters){

                                composable(
                                    route = NavRoutes.ShowFilters
                                ){
                                    ShowFilters(
                                        context = applicationContext,
                                        navController = navController,
                                        viewModel = filterShowViewModel
                                    )
                                }
                                composable(
                                    route = "${NavRoutes.CreateFilter}/{${NavRoutes.FilterParam}}",
                                    arguments = listOf(navArgument(NavRoutes.FilterParam){ type = NavType.IntType})
                                ) {backStackEntry->
                                    val filterId = backStackEntry.arguments?.getInt(NavRoutes.FilterParam)?:-1
                                    CreateFilter(
                                        context = applicationContext,
                                        filterId = filterId,
                                        navController = navController
                                    )
                                }
                            }
                            navigation(route = NavRoutes.MailSettings, startDestination = NavRoutes.ShowMails){

                                composable(
                                    route = NavRoutes.ShowMails){
                                    ShowMailSettings(context = applicationContext,
                                        viewModel = mailShowViewModel,
                                        navController = navController)
                                }

                                composable(
                                    route = "${NavRoutes.CreateMail}/{${NavRoutes.MailParam}}",
                                    arguments = listOf(navArgument(NavRoutes.MailParam){
                                        type = NavType.IntType
                                    })
                                ) { backStackEntry ->

                                    val mailId =
                                        backStackEntry.arguments?.getInt(NavRoutes.MailParam) ?: -1

                                    CreateMail(
                                        context = applicationContext,
                                        mailId = mailId,
                                        navController = navController
                                    )
                                }
                            }
                        }

                        int.value?.let {
                            val route = it.getStringExtra(NavRoutes.IntentRoute)
                            route?.let {r->
                                navController.navigate(r)
                            }
                        }
                    }
                }
            }
        }
    }
}

