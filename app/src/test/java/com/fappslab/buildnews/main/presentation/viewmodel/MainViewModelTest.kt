package com.fappslab.buildnews.main.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.fappslab.buildnews.libraries.arch.rules.DispatcherRule
import com.fappslab.buildnews.main.domain.provider.UseCaseProvider
import com.fappslab.buildnews.stub.getArticleStub
import com.fappslab.buildnews.stub.getArticlesStub
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = dispatcherRule.testDispatcher
    private val articlesStub = getArticlesStub()
    private val articleStub = getArticleStub()
    private val initialState = MainViewState()
    private val provider = UseCaseProvider(
        getFormattedDateUseCase = mockk(),
        getArticlesUseCase = mockk()
    )
    private lateinit var subject: MainViewModel

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `initGetArticlesSuccess Should emit expected states When init block is invoked`() {
        // Given
        val articles = articlesStub.articles
        val expectedFirstState = initialState.copy(childPosition = 0)
        val expectedFinalState = expectedFirstState.copy(articles = articles, childPosition = 2)
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)

        // When
        setupSubject()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        verify { provider.getArticlesUseCase() }
    }

    @Test
    fun `initGetArticlesFailure Should emit expected states When init block is invoked`() {
        // Given
        val cause = Throwable("Some error message")
        val expectedFirstState = initialState.copy(childPosition = 0)
        val expectedFinalState = expectedFirstState.copy(
            shouldShowError = true,
            errorMessage = cause.message,
            childPosition = 1
        )
        every { provider.getArticlesUseCase() } returns flow { throw cause }

        // When
        setupSubject()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        verify { provider.getArticlesUseCase() }
    }

    @Test
    fun `onAdapterItem Should emit expected state When invoked by click on adapter item`() {
        // Given
        val article = articleStub
        val articles = articlesStub.articles
        val expectedFinalState = initialState.copy(
            article = article.copy(publishedAt = "08/07/2023"),
            shouldShowDetails = true,
            articles = articles,
            childPosition = 2,
        )
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)
        every { provider.getFormattedDateUseCase(any(), any()) } returns "08/07/2023"
        setupSubject()
        dispatcher.scheduler.advanceUntilIdle()

        // When
        runTest { subject.onAdapterItem(article) }

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        verify { provider.getArticlesUseCase() }
        verify { provider.getFormattedDateUseCase(any(), any()) }
    }

    @Test
    fun `onDismissFeedbackDetails Should emit expected state When invoked by click on close button`() {
        // Given
        val articles = articlesStub.articles
        val expectedFinalState = initialState.copy(childPosition = 2, articles = articles)
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)
        setupSubject()
        dispatcher.scheduler.advanceUntilIdle()

        // When
        subject.onDismissFeedbackDetails()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        verify { provider.getArticlesUseCase() }
    }

    @Test
    fun `onTryAgain Should emite expected states When invoke by clicked from try again button`() {
        // Given
        val articles = articlesStub.articles
        val expectedFirstState = initialState.copy(childPosition = 0)
        val expectedFinalState = expectedFirstState.copy(articles = articles, childPosition = 2)
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)
        setupSubject()

        // When
        subject.onTryAgain()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        verify(exactly = 2) { provider.getArticlesUseCase() }
    }

    @Test
    fun `onDismissFeedbackError Should emit expected state When invoked by click on close button`() {
        // Given
        val articles = articlesStub.articles
        val expectedFinalState = initialState.copy(childPosition = 2, articles = articles)
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)
        setupSubject()
        dispatcher.scheduler.advanceUntilIdle()

        // When
        subject.onDismissFeedbackError()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        verify { provider.getArticlesUseCase() }
    }

    @Test
    fun `onOpenBrowser Should emit expected OpenBrowser action When invoked by click on see more button`() {
        // Given
        val expectedUrl = "https://www.google.com"
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)
        setupSubject()

        // When
        subject.onOpenBrowser(url = "https://www.google.com")

        // Then
        runTest {
            subject.action.test {
                val result = awaitItem() as? MainViewAction.OpenBrowser
                assertEquals(expectedUrl, result?.url)
                cancelAndConsumeRemainingEvents()
            }
        }
        verify { provider.getArticlesUseCase() }
    }

    @Test
    fun `onOpenBrowserFailure Should emit ShowOpenBrowserError action When pass an invalid url`() {
        // Given
        val expectedAction = MainViewAction.ShowOpenBrowserError
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)
        setupSubject()

        // When
        subject.onOpenBrowser(url = "")

        // Then
        runTest {
            subject.action.test {
                val result = awaitItem()
                assertEquals(expectedAction, result)
                cancelAndConsumeRemainingEvents()
            }
        }
        verify { provider.getArticlesUseCase() }
    }

    private fun setupSubject() {
        subject = MainViewModel(
            provider = provider,
            dispatcher = dispatcher
        )
    }
}
