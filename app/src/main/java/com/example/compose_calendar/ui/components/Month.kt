package com.example.compose_calendar.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Month(modifier: Modifier = Modifier, name: String) {
    AnimatedContent(targetState = name,
        label = "month name",
        transitionSpec = {
            slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
        }) {
        Text(it, style = MaterialTheme.typography.headlineLarge)
    }
}

@Preview
@Composable
fun MonthPreview() {
    Month(name = "test")
}