package com.taposek322.smstomail.sms.domain.navigation

import android.graphics.drawable.VectorDrawable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationElem(
    val title:String,
    val selectedIcon: ImageVector,
    val unselectedIcon:ImageVector,
    val graphRoute:String
)
