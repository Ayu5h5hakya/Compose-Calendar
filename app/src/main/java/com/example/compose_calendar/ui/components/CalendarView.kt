package com.example.compose_calendar.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

@Composable
fun CalendarView(modifier: Modifier = Modifier) {
    var calendar by remember { mutableStateOf(LocalDateTime.now()) }
    Column (modifier = modifier){
        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            ){
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, "arrow left", modifier = Modifier.clickable {
                calendar = calendar.plusMonths(1)
        })
        Text("${calendar.month} ${calendar.year}")
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, "arrow right", modifier = Modifier.clickable {
                calendar = calendar.minusMonths(1)
            })
        }
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
        MonthPager(daysList = calendar.dayList())
    }
}

fun LocalDateTime.dayList() : List<Int>{
    var daysList = mutableListOf<Int>()
    val firstDateOfMonth =  this.withDayOfMonth(1)
    val firstDayOfMonth = firstDateOfMonth.dayOfWeek.value
    val lastDateOfLastMonth = this.withDayOfMonth(1).minusDays(1)
    val lastDateOfMonth = firstDateOfMonth.plusMonths(1).minusDays(1)

    var i = 1
    while (i <= lastDateOfMonth.dayOfMonth) {
        daysList.add(i)
        i++
    }

    var start = lastDateOfLastMonth.dayOfMonth
    while(daysList.indexOf(1) != firstDayOfMonth-1) {
        daysList.add(0,start)
        start--
    }

    var j = 1
    while(daysList.size<= 42) {
        daysList.add(j)
        j++
    }
    return daysList
}

@Composable
@Preview
fun CalendarPreview() {
    CalendarView()
}