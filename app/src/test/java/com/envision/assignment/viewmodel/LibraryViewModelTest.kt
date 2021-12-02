package com.envision.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.envision.assignment.LibraryScreenState
import com.envision.assignment.repository.OfflineEnvisionRepository
import com.envision.core.errorhandling.ErrorParserImpl
import com.envision.core.utils.createTestCoroutineDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @RelaxedMockK
    private lateinit var offlineEnvisionRepository: OfflineEnvisionRepository

    private fun createLibraryViewModel() = LibraryViewModel(
        offlineEnvisionRepository,
        createTestCoroutineDispatcherProvider(testCoroutineDispatcher)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when view model initialized then get data from local database and state should be updated`() =
        testCoroutineScope.runBlockingTest {
            coEvery {
                offlineEnvisionRepository.getAllDocuments()
            }.coAnswers {
                flow { emit(fakeLibItems) }
            }
            val viewModel = createLibraryViewModel()
            assertEquals(fakeLibItems, viewModel.currentState.libraryItems)
            assertEquals(
                LibraryScreenState.ShowingLibraryItems,
                viewModel.currentState.libraryScreenState
            )
        }

    @Test
    fun `when user click on library item then state should update to reading item`() {
        val viewModel = createLibraryViewModel()
        viewModel.onGoToReadLibraryItemContent(fakeLibraryItem)
        assert(viewModel.currentState.libraryScreenState is LibraryScreenState.ReadingItemContent)
    }

}