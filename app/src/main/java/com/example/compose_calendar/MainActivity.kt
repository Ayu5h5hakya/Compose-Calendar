package com.example.compose_calendar

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_calendar.ui.components.Calendar
import com.example.compose_calendar.ui.theme.ComposecalendarTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposecalendarTheme {
                Scaffold { innerPadding ->
                    CalendarView(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView(modifier: Modifier) {
    val pagerState = rememberPagerState(initialPage = 500,pageCount = { 1000 })
    var currentPage by remember { mutableIntStateOf(500) }
    val coroutineScope = rememberCoroutineScope()
    val calendar = getDateTime(currentPage)
    Calendar(
        modifier,
        pagerState = pagerState,
        calendar = calendar,
        header = {
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
        },
        onPageChange = { page ->
            currentPage = page
        },
        onDateClick = { date ->
            Log.d("Scaffold", "onCreate: ${date.year} ${date.month} ${date.dayOfMonth}")
        },
        onDateLongClick = { date ->
            Log.d("Scaffold", "onCreate: ${date.year} ${date.month} ${date.dayOfMonth}")
        }
    )
}

private fun getDateTime(currentPage: Int) : LocalDateTime {
    var calendar = LocalDateTime.now()
    if((currentPage - 500)!=0) {
        calendar = calendar.plusMonths((currentPage - 500).toLong())
    }
    return calendar
}

