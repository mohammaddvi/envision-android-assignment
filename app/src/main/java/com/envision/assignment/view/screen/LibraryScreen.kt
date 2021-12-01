package com.envision.assignment.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.envision.assignment.LibraryItem
import com.envision.assignment.viewmodel.LibraryViewModel
import com.envision.core.extension.state
import com.envision.core.theme.EnvisionTheme
import com.envision.core.theme.graySeparator
import org.koin.androidx.compose.getViewModel

@Composable
fun LibraryScreen(modifier: Modifier = Modifier, libraryItems: List<LibraryItem>? = null) {
    val libraryViewModel : LibraryViewModel = getViewModel()
    val libraryState = libraryViewModel.state()
    LazyColumn(modifier = modifier, content = {
        libraryItems?.let {
            items(libraryState.value.documents) { item ->
                Column {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(text = item.date)
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        Text(text = item.time)
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
        }
    })
}

// @Preview
// @Composable
// fun LibraryScreenPreview() {
//     EnvisionTheme {
//         LibraryScreen(
//             libraryItems =
//             listOf(
//                 LibraryItem("22/02/21", "15:32"),
//                 LibraryItem("22/02/21", "13:32"),
//                 LibraryItem("22/01/21", "15:32"),
//                 LibraryItem("04/01/21", "10:40"),
//             )
//         )
//     }
// }