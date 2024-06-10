package com.example.compose_calendar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_calendar.ui.components.Calendar
import com.example.compose_calendar.ui.theme.ComposecalendarTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposecalendarTheme {
                Scaffold (Modifier.background(MaterialTheme.colorScheme.surface)){ innerPadding ->
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
        modifier.background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(10.dp)).padding(10.dp),
        pagerState = pagerState,
        calendar = calendar,
        header = {
             Box(Modifier
                 .padding(vertical = 4.dp)){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                ) {
                    Column(Modifier.weight(1f)) {
                        Row {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                "arrow left",
                                modifier = Modifier
                                    .clickable {
                                        coroutineScope.launch {
                                            currentPage--
                                            pagerState.animateScrollToPage(currentPage)
                                        }
                                    })
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                "arrow right",
                                modifier = Modifier.clickable {
                                    coroutineScope.launch {
                                        currentPage++
                                        pagerState.animateScrollToPage(currentPage)
                                    }
                                })
                        }
                        Text(calendar.month.name.lowercase().replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.headlineLarge)
                        Text("${calendar.year}",  style = MaterialTheme.typography.headlineSmall)
                    }
                    Column(Modifier
                        .background(
                            MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("2024", style = MaterialTheme.typography.titleMedium)
                        Text("20",style = MaterialTheme.typography.titleLarge.copy(fontSize = 48.sp))
                        Text("SEPT",style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        },
        dayBuilder = { date ->
            val bgColor = if(date.enabled) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.outlineVariant
            Box(Modifier
                .padding(vertical = 4.dp)
                .width(48.dp)
                .background(bgColor,shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center,) {
                Day(day = date.day, modifier = Modifier.combinedClickable(
                    onLongClick = {
                        Log.d("Scaffold", "onCreate: ${date.year} ${date.month} ${date.day}")
                    },
                    onClick = {
                        Log.d("Scaffold", "onCreate: ${date.year} ${date.month} ${date.day}")
                    },
                ))
            }
        },
        onPageChange = { page ->
            currentPage = page
        },
    )
}


@Composable
fun Day(modifier: Modifier = Modifier,day : Int) {
    Text("$day", modifier = modifier.padding(8.dp), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
}

private fun getDateTime(currentPage: Int) : LocalDateTime {
    var calendar = LocalDateTime.now()
    if((currentPage - 500)!=0) {
        calendar = calendar.plusMonths((currentPage - 500).toLong())
    }
    return calendar
}

