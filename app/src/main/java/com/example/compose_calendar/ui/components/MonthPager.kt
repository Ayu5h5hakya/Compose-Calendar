package com.example.compose_calendar.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose_calendar.R
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MonthPager(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = {12})
    HorizontalPager(modifier = modifier, state = pagerState) { page ->
        var calendar =Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val firstDayOfMonth = calendar[Calendar.DAY_OF_WEEK]
        var daysList = mutableListOf<Int>()
        calendar.set(calendar[Calendar.YEAR],calendar[Calendar.MONTH]+1,0)
        val lastDateOfMonth = calendar[Calendar.DAY_OF_MONTH]
        val lastDayOfMonth = calendar[Calendar.DAY_OF_WEEK]
        var i = 1
        while (i <= lastDateOfMonth) {
            daysList.add(i)
            i++
        }
        var remainingCellCount = 42 - daysList.size
        for(i in lastDayOfMonth..remainingCellCount+1 ) {
            daysList.add(i-lastDayOfMonth+1)
        }
        LazyColumn {
            items(6) { colIndex ->
                LazyRow (horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()){
                    items(7) { rowIndex ->
                        if(colIndex * 7 + rowIndex < daysList.size)
                            Box(Modifier
                                .width(48.dp),contentAlignment = Alignment.Center,) {
                                Day(day = daysList[colIndex * 7 + rowIndex])
                            }
                    }
                }
            }
        }
    }
}

@Composable
fun Day(day : Int) {
    Text("$day", Modifier.padding(8.dp))
}