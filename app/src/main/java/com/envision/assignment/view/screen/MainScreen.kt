package com.envision.assignment.view.screen

import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.envision.assignment.CaptureScreenState
import com.envision.assignment.LibraryItem
import com.envision.assignment.tabsData
import com.envision.assignment.view.component.TabContainer
import com.envision.assignment.view.component.TabPager
import com.envision.assignment.viewmodel.CaptureViewModel
import com.envision.assignment.viewmodel.LibraryViewModel
import com.envision.core.extension.state
import com.envision.core.theme.EnvisionTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    captureScreenState: CaptureScreenState,
    libraryState: List<LibraryItem>,
    onImageSaved: (Uri) -> Unit,
    onImageCaptureException:(ImageCaptureException)-> Unit,
    onPermissionGranted : () -> Unit,
    onPermissionDenied : () -> Unit
) {
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
        ) {
            if (it == 0) {
                CaptureScreen(
                    captureScreenState = captureScreenState,
                    onImageSaved = { onImageSaved(it) },
                    onImageCaptureException = { onImageCaptureException(it) },
                    onPermissionGranted = { onPermissionGranted() },
                    onPermissionDenied = { onPermissionDenied() })
            } else {
                LibraryScreen(libraryItems = libraryState)
            }
        }
    }
}

// @Preview
// @Composable
// fun HomeScreenPreview() {
//     EnvisionTheme {
//         MainScreen()
//     }
// }