package com.curtjrees.recipes.androidApp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun WithDelay(
    delayMs: Long,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayMs)
        visible = true
    }

    if (visible) content()
}

fun List<Any>.isLastIndex(index: Int): Boolean = index == (size - 1)