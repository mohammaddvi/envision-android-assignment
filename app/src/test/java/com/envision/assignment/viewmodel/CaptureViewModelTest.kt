package com.envision.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.envision.assignment.CaptureScreenState
import com.envision.assignment.usecase.ConcatParagraphsUseCase
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
    private lateinit var errorParser: ErrorParserImpl

    private fun createCaptureViewModel() = CaptureViewModel(
        uploadFileUseCase,
        concatParagraphsUseCase,
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
    fun `when user has done nothing yet then view model state should to not loaded`() =
        testCoroutineScope.runBlockingTest {
            val viewModel = createCaptureViewModel()
            assertEquals(CaptureScreenState.Capturing, viewModel.currentState.captureScreenState)
        }

    @Test
    fun `when user upload image then view model state should to loading`() =
        testCoroutineScope.runBlockingTest {
            coEvery {
                uploadFileUseCase.execute(any())
            }.coAnswers {
                delay(1000)
                ocrResultModel
            }
            val viewModel = createCaptureViewModel()
            viewModel.onImageSaved(uriBuilderWrapper.uriCreator())
            assertEquals(CaptureScreenState.Processing, viewModel.currentState.captureScreenState)
        }

    @Test
    fun `when ocr result fetched successfully then view model state should to loaded with data`() =
        testCoroutineScope.runBlockingTest {
            coEvery {
                uploadFileUseCase.execute(any())
            }.coAnswers {
                delay(500)
                ocrResultModel
            }

            coEvery {
                concatParagraphsUseCase.execute(any())
            }.coAnswers {
                ocrResultConcated
            }

            val viewModel = createCaptureViewModel()
            viewModel.onImageSaved(uriBuilderWrapper.uriCreator())
            delay(1000)
            assertEquals(
                CaptureScreenState.ShowingResult(ocrResultConcated),
                viewModel.currentState.captureScreenState
            )
        }

    @Test
    fun `when fetching ocr result failed then view model state should update to failed`() =
        testCoroutineScope.runBlockingTest {
            coEvery {
                uploadFileUseCase.execute(any())
            }.throws(envisionThrowable)

            coEvery {
                concatParagraphsUseCase.execute(any())
            }.coAnswers {
                ocrResultConcated
            }

            val viewModel = createCaptureViewModel()
            viewModel.onImageSaved(uriBuilderWrapper.uriCreator())
            assertTrue(viewModel.currentState.captureScreenState is CaptureScreenState.Error)
        }
}