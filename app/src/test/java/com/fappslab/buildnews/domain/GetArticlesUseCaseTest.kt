package com.fappslab.buildnews.domain

import app.cash.turbine.test
import com.fappslab.buildnews.common.domain.repository.BuildNewsRepository
import com.fappslab.buildnews.common.domain.usecase.GetArticlesUseCase
import com.fappslab.buildnews.stub.getArticlesStub
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetArticlesUseCaseTest {

    private val repository: BuildNewsRepository = mockk()
    private val subject = GetArticlesUseCase(repository)

    @Test
    fun `getArticlesSuccess Should emit articles When use case get success result`() {
        // Given
        val expectedResult = getArticlesStub()
        every { repository.getArticles() } returns flowOf(expectedResult)

        // When
        val result = subject()

        // Then
        runTest {
            result.test {
                assertEquals(expectedResult, awaitItem())
                awaitComplete()
            }
        }
        verify { repository.getArticles() }
    }

    @Test
    fun `getArticlesFailure Should emit throwable When use case get failure result`() {
        // Given
        val expectedResult = Throwable("Some error message")
        every { repository.getArticles() } returns flow { throw expectedResult }

        // When
        val result = subject()

        // Then
        runTest {
            result.test {
                val cause = awaitError()
                assertEquals(expectedResult, cause)
                assertEquals(expectedResult.message, cause.message)
            }
        }
        verify { repository.getArticles() }
    }
}
