package com.example.compose_calendar.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MonthPager(modifier: Modifier = Modifier,
               daysList: List<Int>,
               onDayClick: (Int) -> Unit,
               onDayLongClick: (Int) -> Unit,
               onPageChanged: (Int) -> Unit,) {
    val pagerState = rememberPagerState(pageCount = {12})

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {page ->
            onPageChanged(page)
        }
    }

    HorizontalPager(modifier = modifier, state = pagerState) { page ->
        LazyColumn {
            items(6) { colIndex ->
                LazyRow (horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()){
                    items(7) { rowIndex ->
                        if(colIndex * 7 + rowIndex < daysList.size)
                            Box(Modifier
                                .width(48.dp),contentAlignment = Alignment.Center,) {
                                Day(day = daysList[colIndex * 7 + rowIndex], modifier = Modifier.combinedClickable(
                                    onLongClick = {
                                        onDayLongClick(daysList[colIndex * 7 + rowIndex])
                                    }
                                ) {
                                    onDayClick(daysList[colIndex * 7 + rowIndex])
                                })
                            }
                    }
                }
            }
        }
    }
}

@Composable
fun Day(modifier: Modifier = Modifier,day : Int) {
    Text("$day", modifier = modifier.padding(8.dp))
}