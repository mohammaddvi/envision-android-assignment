package com.envision.assignment.viewmodel

import com.envision.assignment.LibraryItem
import com.envision.assignment.roomtest.Document
import com.envision.assignment.roomtest.DocumentDao
import com.envision.assignment.toLibraryItem
import com.envision.core.coroutine.CoroutineDispatcherProvider
import com.envision.core.viewmodel.EnvisionStatefulViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val documentDao: DocumentDao,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : EnvisionStatefulViewModel<LibraryViewModel.State>(State(), coroutineDispatcherProvider) {
    data class State(
        val documents: List<LibraryItem> = listOf()
    )

    init {
        getDocuments()
    }

    private fun getDocuments() {
        launch {
            onIo {
                documentDao.getAllDocuments().collect {
                    val result = it.map {
                        it.toLibraryItem()
                    }
                    applyState {
                        copy(documents = result)
                    }
                }
            }
        }
    }
}
