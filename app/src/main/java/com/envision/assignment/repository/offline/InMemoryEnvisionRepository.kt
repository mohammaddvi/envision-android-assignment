package com.envision.assignment.repository.offline

import com.envision.assignment.LibraryItem
import com.envision.assignment.repository.OfflineEnvisionRepository
import com.envision.assignment.toDocument
import com.envision.assignment.toLibraryItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class InMemoryEnvisionRepository(
    private val documentDao: DocumentDao
) : OfflineEnvisionRepository {
    override fun saveDocument(libraryItem: LibraryItem) {
        documentDao.insertDocument(libraryItem.toDocument())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllDocuments(): Flow<List<LibraryItem>> =
        documentDao.getAllDocuments().flatMapLatest { documents ->
            flowOf(documents.map { it.toLibraryItem() })
        }
    }