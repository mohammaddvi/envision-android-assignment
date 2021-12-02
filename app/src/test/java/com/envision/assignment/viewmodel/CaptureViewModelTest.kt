package com.envision.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.envision.assignment.CaptureScreenState
import com.envision.assignment.usecase.ConcatParagraphsUseCase
import com.envision.assignment.usecase.SaveDocumentUseCase
import com.envision.assignment.usecase.UploadFileUseCase
import com.envision.core.errorhandling.ErrorParserImpl
import com.envision.core.utils.TestUriBuilderWrapper
import com.envision.core.utils.createTestCoroutineDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CaptureViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @RelaxedMockK
    private lateinit var uploadFileUseCase: UploadFileUseCase

    @RelaxedMockK
    private lateinit var concatParagraphsUseCase: ConcatParagraphsUseCase

    @RelaxedMockK
    private lateinit var uriBuilderWrapper: TestUriBuilderWrapper

    @RelaxedMockK
    private lateinit var saveDocumentUseCase: SaveDocumentUseCase

    @RelaxedMockK
    private lateinit var errorParser: ErrorParserImpl

    private fun createCaptureViewModel() = CaptureViewModel(
        uploadFileUseCase,
        concatParagraphsUseCase,
        saveDocumentUseCase,
        errorParser,
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
    fun `when user has done nothing yet then view model state should be permission`() =
        testCoroutineScope.runBlockingTest {
            val viewModel = createCaptureViewModel()
            assertEquals(CaptureScreenState.Permission, viewModel.currentState.captureScreenState)
        }

    @Test
    fun `when user upload image then view model state should be processing`() =
        testCoroutineScope.runBlockingTest {
            coEvery {
                uploadFileUseCase.execute(any())
            }.coAnswers {
                delay(1000)
                fakeOcrResultModel
            }
            val viewModel = createCaptureViewModel()
            viewModel.onImageSaved(uriBuilderWrapper.uriCreator())
            assertEquals(CaptureScreenState.Processing, viewModel.currentState.captureScreenState)
        }

    @Test
    fun `when ocr result fetched successfully then view model state should be showing result with data`() =
        testCoroutineScope.runBlockingTest {
            coEvery {
                uploadFileUseCase.execute(any())
            }.coAnswers {
                delay(500)
                fakeOcrResultModel
            }

            coEvery {
                concatParagraphsUseCase.execute(any())
            }.coAnswers {
                fakeOcrResultConcated
            }

            val viewModel = createCaptureViewModel()
            viewModel.onImageSaved(uriBuilderWrapper.uriCreator())
            delay(1000)
            assertEquals(
                CaptureScreenState.ShowingResult(fakeOcrResultConcated),
                viewModel.currentState.captureScreenState
            )
        }

    @Test
    fun `when fetching ocr result failed then view model state should be error`() =
        testCoroutineScope.runBlockingTest {
            coEvery {
                uploadFileUseCase.execute(any())
            }.throws(fakeThrowable)

            coEvery {
                concatParagraphsUseCase.execute(any())
            }.coAnswers {
                fakeOcrResultConcated
            }

            val viewModel = createCaptureViewModel()
            viewModel.onImageSaved(uriBuilderWrapper.uriCreator())
            assertTrue(viewModel.currentState.captureScreenState is CaptureScreenState.Error)
        }
}