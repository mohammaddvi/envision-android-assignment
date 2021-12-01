package com.envision.assignment.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.envision.assignment.TabItem
import com.envision.assignment.tabsData
import com.envision.core.theme.EnvisionTheme
import com.envision.core.theme.grayUnselected
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContainer(
    selectedTabIndex: Int,
    tabsItems: List<TabItem>,
    onPageSelected: ((tabItem: TabItem) -> Unit)
) {
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        selectedTabIndex = selectedTabIndex,
        backgroundColor = EnvisionTheme.colors.primary
    ) {
        tabsItems.forEachIndexed { index, tabItem ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onPageSelected(tabItem) },
                selectedContentColor = EnvisionTheme.colors.onPrimary,
                unselectedContentColor = EnvisionTheme.colors.grayUnselected
            ) {
                Text(text = tabItem.title)
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabPager(
    modifier: Modifier = Modifier,
    tabItems: List<TabItem>,
    pagerState: PagerState,
    onScreenUpdate: @Composable (Int) -> Unit
) {
    HorizontalPager(modifier = modifier, count = tabItems.size, state = pagerState) { index ->
        onScreenUpdate(index)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun TabPreview() {
    EnvisionTheme {
        Column {
            TabContainer(
                selectedTabIndex = 1,
                tabsItems = tabsData,
                onPageSelected = {})
            TabPager(tabItems = tabsData, pagerState = rememberPagerState()){}
        }
    }
}

