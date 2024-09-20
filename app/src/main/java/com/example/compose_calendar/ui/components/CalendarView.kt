package com.example.compose_calendar.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalendarView(modifier: Modifier = Modifier) {
    Column (modifier = modifier){
        Row(
            modifier= Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround) {
            Text("M")
            Text("T")
            Text("W")
            Text("T")
            Text("F")
            Text("S")
            Text("S")

        }
        MonthPager()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MonthPager(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = {12})
    HorizontalPager(modifier = modifier, state = pagerState) { page ->
        Text("page $page",
            modifier = Modifier.fillMaxSize())
    }
}

@Composable
@Preview
fun CalendarPreview() {
    CalendarView()
}