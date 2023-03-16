package com.social2023Network.presentation.ui.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.social2023Network.R
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.animePage.AnimePage
import com.social2023Network.presentation.ui.home.component.mainPage.MainPage
import com.social2023Network.presentation.ui.home.component.util.Background
import com.social2023Network.presentation.ui.home.component.weatherPage.WeatherPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val itemsTab = stringArrayResource(id = R.array.tab_items)
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Background {
        Column {
            TabLayout(items = itemsTab, pagerState, onTabSelected = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            } )
            HorizontalPager(count = itemsTab.size, state = pagerState) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    when (pagerState.currentPage) {
                        0 -> MainPage(viewModel)
                        1 -> AnimePage(viewModel)
                        2 -> {}
                        3 -> WeatherPage(viewModel)
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(items: Array<String>, pagerState: PagerState, onTabSelected: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        it
                    )
                )
            },
            backgroundColor = colorResource(id = R.color.purple_500),
            contentColor = Color.White
        ) {
            items.forEachIndexed { index, s ->
                TabItem(title = s, pagerState.currentPage == index) { onTabSelected(index) }
            }
        }
    }
}

@Composable
fun TabItem(title: String, isSelected: Boolean, onTabSelected: () -> Unit) {
    val scale by animateFloatAsState(if (isSelected) 1.2f else 1f)
    val textColor by animateColorAsState(if (isSelected) Color.White else Color.White.copy(0.5f))

    Box(
        modifier = Modifier
            .clickable(onClick = onTabSelected)
            .height(48.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = textColor,
            style = MaterialTheme.typography.subtitle1
        )
    }
}
