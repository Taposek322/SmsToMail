package com.taposek322.smstomail.mail.presentation.ui

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taposek322.smstomail.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun<T> SwipeToDelete(
    context: Context,
    item:T,
    onDelete: (T)->Unit,
    modifier: Modifier = Modifier,
    animationDuration: Int = 500,
    content: @Composable (T)->Unit,
){
    var isRemoved by remember{
        mutableStateOf(false)
    }
    val dismissState = rememberDismissState(
        confirmValueChange = {value ->
            if(value == DismissValue.DismissedToStart){
                isRemoved = true
                true
            }else{
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved){
        if(isRemoved){
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        Box(
            modifier = modifier
                .padding(16.dp)
        ) {
            SwipeToDismiss(
                state = dismissState,
                background = {
                    DeleteBackground(
                        dismissState,
                        modifier = modifier,
                        context = context
                    )
                },
                dismissContent = {
                    content(item)
                },
                directions = setOf(DismissDirection.EndToStart)
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: DismissState,
    context: Context,
    modifier: Modifier = Modifier,
){
    val color = if(swipeDismissState.dismissDirection == DismissDirection.EndToStart){
        Color.Red
    }else{
        Color.Transparent
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = context.getString(R.string.delete_swipe),
            tint = Color.White
        )
    }
}