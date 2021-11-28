package com.envision.assignment.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.envision.assignment.home.view.compose.TabPager
import com.envision.assignment.home.view.compose.TabContainer
import com.envision.assignment.tabsData
import com.envision.core.theme.EnvisionTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 1)
    Column(modifier = modifier) {
        val coroutineScope = rememberCoroutineScope()
        TabContainer(pagerState.currentPage, tabsData) { tabItem ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(tabItem.index)
            }
        }
        TabPager(
            modifier = Modifier.weight(1f),
            tabItems = tabsData,
            pagerState = pagerState
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    EnvisionTheme {
        MainScreen()
    }
}