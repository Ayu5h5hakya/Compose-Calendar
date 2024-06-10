package com.example.compose_calendar.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 500,pageCount = { 1000 })
    var currentPage by remember { mutableIntStateOf(500) }

    val calendar = getDateTime(currentPage)
    val daysList = calendar.dayList()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPage = page
        }
    }

    Column (modifier = modifier){
        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            ){
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, "arrow left", modifier = Modifier.clickable {
                coroutineScope.launch {
                    currentPage--
                    pagerState.animateScrollToPage(currentPage)
                }
        })
        Text("${calendar.month} ${calendar.year}")
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, "arrow right", modifier = Modifier.clickable {
                coroutineScope.launch {
                    currentPage++
                    pagerState.animateScrollToPage(currentPage)
                }
            })
        }
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround) {
            Text("M")
            Text("T")
            Text("W")
            Text("T")
            Text("F")
            Text("S")
            Text("S")
        }
        HorizontalPager(state = pagerState) {
            LazyColumn {
                items(6) { colIndex ->
                    LazyRow (horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()){
                        items(7) { rowIndex ->
                            val day = daysList[colIndex * 7 + rowIndex]
                            if(colIndex * 7 + rowIndex < daysList.size)
                                Box(Modifier
                                    .width(48.dp),contentAlignment = Alignment.Center,) {
                                    Day(day = day, modifier = Modifier.combinedClickable(
                                        onLongClick = {
                                            Log.d("monthpager", "CalendarView: long click $day")
                                        }
                                    ) {
                                        Log.d("monthpager", "CalendarView: click $day")
                                    })
                                }
                        }
                    }
                }
            }
        }
    }
}

private fun getDateTime(currentPage: Int) : LocalDateTime {
    var calendar = LocalDateTime.now()
    if((currentPage - 500)!=0) {
        calendar = calendar.plusMonths((currentPage - 500).toLong())
    }
    return calendar
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
fun Day(modifier: Modifier = Modifier,day : Int) {
    Text("$day", modifier = modifier.padding(8.dp))
}

@Composable
@Preview
fun CalendarPreview() {
    CalendarView()
}