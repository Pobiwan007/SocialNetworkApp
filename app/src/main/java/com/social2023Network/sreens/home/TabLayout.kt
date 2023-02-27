package com.social2023Network.sreens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.social2023Network.data.Profile
import com.social2023Network.data.Story
import com.social2023Network.sreens.home.itemsList.ItemStory
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(list: Array<String>) {
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutine = rememberCoroutineScope()

    val tempList = listOf(
        Story(
            0,
            "17.02.2023:9:00",
            "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
            profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
        ),
        Story(
            0,
            "17.02.2023:9:00",
            "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
            profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
        ),
        Story(
            0,
            "17.02.2023:9:00",
            "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
            profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
        ),
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        it
                    )
                )
            },
            backgroundColor = colorResource(id = com.social2023Network.R.color.purple_500),
            contentColor = Color.White
        ) {
            list.forEachIndexed { index, s ->
                Tab(
                    selected = false,
                    onClick = {
                              coroutine.launch {
                                  pagerState.animateScrollToPage(index)
                              }
                    },
                    text = { Text(text = s) }
                )
            }
        }
        HorizontalPager(count = list.size, state = pagerState) {
            Column(modifier = Modifier.fillMaxSize().padding(top = 5.dp)) {
                when(pagerState.currentPage) {
                    0 ->         ItemStory(items = tempList)
                }
            }
        }
    }
}