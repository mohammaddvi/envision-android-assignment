package com.envision.assignment.viewmodel

import com.envision.assignment.LibraryItem
import com.envision.assignment.LibraryScreenState
import com.envision.assignment.repository.OfflineEnvisionRepository
import com.envision.core.coroutine.CoroutineDispatcherProvider
import com.envision.core.viewmodel.EnvisionStatefulViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val envisionRepository: OfflineEnvisionRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : EnvisionStatefulViewModel<LibraryViewModel.State>(State(), coroutineDispatcherProvider) {
    data class State(
        val libraryScreenState: LibraryScreenState = LibraryScreenState.ShowingLibraryItems,
        val libraryItems: List<LibraryItem> = listOf()
    )

    init {
        getDocuments()
    }

    fun onGoToLibraryListClicked() {
        applyState {
            copy(libraryScreenState = LibraryScreenState.ShowingLibraryItems)
        }
    }

    fun onGoToReadLibraryItemContent(item: LibraryItem) {
        applyState {
            copy(libraryScreenState = LibraryScreenState.ReadingItemContent(item.content))
        }
    }

    private fun getDocuments() {
        launch {
            onIo {
                envisionRepository.getAllDocuments().collect {
                    applyState {
                        copy(
                            libraryScreenState = LibraryScreenState.ShowingLibraryItems,
                            libraryItems = it
                        )
                    }
                }

            }
        }
    }
}
