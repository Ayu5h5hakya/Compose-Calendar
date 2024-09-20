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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalendarView(modifier: Modifier = Modifier) {
    Column (modifier = modifier){
        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            ){
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, "arrow left")
            Text("September 2024")
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, "arrow right")
        }
        Row(
            modifier= Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround) {
            Text("S")
            Text("M")
            Text("T")
            Text("W")
            Text("T")
            Text("F")
            Text("S")

        }
        MonthPager()
    }
}

@Composable
@Preview
fun CalendarPreview() {
    CalendarView()
}