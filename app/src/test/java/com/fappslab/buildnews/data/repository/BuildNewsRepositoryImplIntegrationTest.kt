package com.fappslab.buildnews.data.repository

import app.cash.turbine.test
import com.fappslab.buildnews.common.domain.repository.BuildNewsRepository
import com.fappslab.buildnews.main.di.AppModule
import com.fappslab.buildnews.di.FlavorModule
import com.fappslab.buildnews.libraries.arch.rules.RemoteRule
import com.fappslab.buildnews.stub.getArticlesStub
import com.fappslab.buildnews.stub.toApiServerFailureResponse
import com.fappslab.buildnews.stub.toApiServerSuccessResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class BuildNewsRepositoryImplIntegrationTest : KoinTest {

    @get:Rule
    val remoteRule = RemoteRule(modules = AppModule.modules + FlavorModule.modules)

    private val subject: BuildNewsRepository by inject()

    @Test
    fun `getArticlesSuccess Should emit articles When repository get success result`() {
        // Given
        val expectedSize = 2
        val expectedResult = getArticlesStub()
        remoteRule.toApiServerSuccessResponse()

        // When
        val result = subject.getArticles()

        // Then
        runTest {
            result.test {
                val item = awaitItem()
                assertEquals(expectedResult, item)
                assertEquals(expectedSize, item.articles.size)
                awaitComplete()
            }
        }
    }

    @Test
    fun `getArticlesFailure Should emit throwable When repository get failure result`() {
        // Given
        val expectedResult = "Your API key is invalid or incorrect."
        remoteRule.toApiServerFailureResponse()

        // When
        val result = subject.getArticles()

        // Then
        runTest {
            result.test {
                assertEquals(expectedResult, awaitError().message)
            }
        }
    }
}
