package com.envision.assignment.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.envision.assignment.LibraryItem
import com.envision.assignment.LibraryScreenState
import com.envision.assignment.R
import com.envision.assignment.viewmodel.LibraryViewModel
import com.envision.core.extension.state
import com.envision.core.theme.EnvisionTheme
import com.envision.core.theme.black
import com.envision.core.theme.graySeparator
import org.koin.androidx.compose.getViewModel

@Composable
fun LibraryScreen(modifier: Modifier = Modifier) {
    val libraryViewModel: LibraryViewModel = getViewModel()
    val libraryState = libraryViewModel.state()
    Column(modifier = modifier) {
        when (libraryState.value.libraryScreenState) {
            is LibraryScreenState.ShowingLibraryItems -> {
                ShowingLibraryItems(libraryItems = libraryState.value.libraryItems) {
                    libraryViewModel.onGoToReadLibraryItemContent(it)
                }
            }
            is LibraryScreenState.ReadingItemContent -> {
                ReadingFileState((libraryState.value.libraryScreenState as LibraryScreenState.ReadingItemContent).content) {
                    libraryViewModel.onGoToLibraryListClicked()
                }
            }
        }
    }
}

@Composable
fun ShowingLibraryItems(
    modifier: Modifier = Modifier,
    libraryItems: List<LibraryItem>,
    onItemClicked: (LibraryItem) -> Unit
) {
    LazyColumn(modifier = modifier, content = {
        items(libraryItems) { item ->
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClicked(item)
                    }
                    .padding(16.dp)) {
                    Text(text = item.name, color = EnvisionTheme.colors.black)
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .background(color = EnvisionTheme.colors.graySeparator)
                        .height(1.dp)
                ) {}
            }
        }
    })
}

@Composable
fun ReadingFileState(libraryItemContent: String, onReturnCLicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = libraryItemContent,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(16.dp)
                .background(color = EnvisionTheme.colors.onPrimary)
                .verticalScroll(rememberScrollState(0)),
            color = EnvisionTheme.colors.black
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onReturnCLicked() }
                .padding(bottom = 32.dp)
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(50.dp))
                .height(50.dp)
                .background(color = EnvisionTheme.colors.primary),

            ) {
            Text(
                text = stringResource(R.string.back_to_lib_items),
                modifier = Modifier.align(Alignment.Center),
                color = EnvisionTheme.colors.onPrimary
            )
        }
    }
}