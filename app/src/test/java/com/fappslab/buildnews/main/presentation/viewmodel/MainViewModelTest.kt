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
import org.junit.Before
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

    @Before
    fun setUp() {
        subject = MainViewModel(
            provider = provider,
            dispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getArticlesSuccess Should emit expected states When is invoked`() {
        // Given
        val articles = articlesStub.articles
        val expectedFirstState = initialState.copy(childPosition = 0)
        val expectedFinalState = expectedFirstState.copy(articles = articles, childPosition = 3)
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)

        // When
        subject.getArticles()

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
    fun `getArticlesFailure Should emit expected states When is invoked`() {
        // Given
        val cause = Throwable("Some error message")
        val expectedFirstState = initialState.copy(childPosition = 0)
        val expectedFinalState = expectedFirstState.copy(
            shouldShowError = true,
            errorMessage = cause.message,
            childPosition = 2
        )
        every { provider.getArticlesUseCase() } returns flow { throw cause }

        // When
        subject.getArticles()

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
        val expectedState = initialState.copy(
            article = article.copy(publishedAt = "08/07/2023"),
            shouldShowDetails = true
        )
        every { provider.getFormattedDateUseCase(any(), any()) } returns "08/07/2023"

        // When
        subject.onAdapterItem(article)

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        verify { provider.getFormattedDateUseCase(any(), any()) }
    }

    @Test
    fun `onDismissFeedbackDetails Should emit expected state When invoked by click on close button`() {
        // Given
        val expectedState = initialState.copy(shouldShowDetails = false)

        // When
        subject.onDismissFeedbackDetails()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `onTryAgain Should emit expected states When invoke by clicked from try again button`() {
        // Given
        val articles = articlesStub.articles
        val expectedFirstState = initialState.copy(shouldShowError = false, childPosition = 0)
        val expectedFinalState = expectedFirstState.copy(articles = articles, childPosition = 3)
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)

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
        verify { provider.getArticlesUseCase() }
    }

    @Test
    fun `onDismissFeedbackError Should emit expected state When invoked by click on close button`() {
        // Given
        val expectedState = initialState.copy(shouldShowError = false)

        // When
        subject.onDismissFeedbackError()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `onOpenBrowser Should emit expected OpenBrowser action When invoked by click on see more button`() {
        // Given
        val expectedUrl = "https://www.google.com"

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
    }

    @Test
    fun `onOpenBrowserFailure Should emit ShowOpenBrowserError action When pass an invalid url`() {
        // Given
        val expectedAction = MainViewAction.ShowOpenBrowserError

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
    }

    @Test
    fun `onCanAuthenticate Should emit expected state When is invoked with authenticator available`() {
        // Given
        val expectedState = initialState.copy(childPosition = 1)

        // When
        subject.onCanAuthenticate()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `onBiometricError Should emit expected states When there is no biometrics available`() {
        // Given
        val articles = articlesStub.articles
        val expectedFirstState = initialState.copy(childPosition = 0)
        val expectedFinalState = expectedFirstState.copy(articles = articles, childPosition = 3)
        every { provider.getArticlesUseCase() } returns flowOf(articlesStub)

        // When
        subject.onBiometricError(errorCode = 11)

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
    fun `onShowBiometricPrompt Should emit expected ShowBiometricPrompt action When is invoked to show biometric prompt`() {
        // Given
        val expectedAction = MainViewAction.ShowBiometricPrompt

        // When
        subject.onShowBiometricPrompt()

        // Then
        runTest {
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
