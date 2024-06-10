package com.example.compose_calendar.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Calendar(modifier: Modifier = Modifier,
             calendar: LocalDateTime,
             pagerState: PagerState = rememberPagerState(initialPage = 500,pageCount = { 1000 }),
             header: @Composable ColumnScope.() -> Unit = {},
             onPageChange: (Int) -> Unit = {},
             onDateLongClick: (LocalDateTime) -> Unit = {},
             onDateClick: (LocalDateTime) -> Unit = {},) {
    val daysList = calendar.dayList()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onPageChange(page)
        }
    }

    Column (modifier = modifier){
        header()
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
                                            onDateLongClick(calendar.withDayOfMonth(day))
                                        }
                                    ) {
                                        onDateClick(calendar.withDayOfMonth(day))
                                    })
                                }
                        }
                    }
                }
            }
        }
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
fun Day(modifier: Modifier = Modifier,day : Int) {
    Text("$day", modifier = modifier.padding(8.dp))
}