package com.envision.assignment.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.envision.assignment.TabItem
import com.envision.assignment.tabsData
import com.envision.assignment.view.component.TabContainer
import com.envision.assignment.view.component.TabPager
import com.envision.assignment.viewmodel.LibraryViewModel
import com.envision.core.extension.state
import com.envision.core.theme.EnvisionTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = TabItem.Library.index)
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
        ) {
            if (it == TabItem.Capture.index) CaptureScreen(modifier) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(TabItem.Library.index)
                }
            }
            else LibraryScreen(modifier = modifier)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    EnvisionTheme {
        MainScreen()
    }
}