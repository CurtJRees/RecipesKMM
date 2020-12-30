package com.curtjrees.recipes.androidApp.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.onActive
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun SquareLoadingIndicator() {
    var state by remember { mutableStateOf("to") }

    val transition = updateTransition(targetState = state)
    val rotation = transition.animateFloat(
        transitionSpec = {
            repeatable(animation = tween(delayMillis = 0, durationMillis = 1500, easing = LinearEasing), iterations = 1000, repeatMode = RepeatMode.Restart) //Workaround for infiniteRepeatable not supported by latest version of Transition API
        },
        targetValueByState = {
            if(it == "to") 0f else 360f
        }
    )

    val cornerRadius = transition.animateDp(
        transitionSpec = {
            repeatable(animation = tween(delayMillis = 0, durationMillis = 660, easing = LinearEasing), iterations = 1000, repeatMode = RepeatMode.Reverse) //Workaround for infiniteRepeatable not supported by latest version of Transition API
        },
        targetValueByState = {
            if(it == "to") 20.dp else 2.dp
        }
    )

    Box(
        modifier = Modifier
            .rotate(rotation.value)
            .preferredSize(40.dp)
            .clip(RoundedCornerShape(cornerRadius.value))
            .background(MaterialTheme.colors.primary)
    )

    onActive {
        state = "from"
    }
}