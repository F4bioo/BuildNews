package com.fappslab.buildnews.domain

import com.fappslab.buildnews.common.domain.usecase.GetFormattedDateUseCase
import org.junit.Test
import kotlin.test.assertEquals

class GetFormattedDateUseCaseTest {

    private val subject = GetFormattedDateUseCase()

    @Test
    fun `formatDateSuccess Should return formatted date When use case is invoked with NewsApi flavor`() {
        // Given
        val publishedAt = "2023-07-08T14:58:45Z"
        val expectedResult = "08/07/2023"

        // When
        val result = subject(flavorName = "newsapi", dateISO8601 = publishedAt)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `formatDateSuccess Should return formatted date When use case is invoked with NYTimesApi flavor`() {
        // Given
        val publishedAt = "2023-07-06"
        val expectedResult = "06/07/2023"

        // When
        val result = subject(flavorName = "nytimesapi", dateISO8601 = publishedAt)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `formatDateFailure Should return three dash When use case is invoked with a unknown flavor`() {
        // Given
        val publishedAt = "2023-07-06"
        val expectedResult = "---"

        // When
        val result = subject(flavorName = "other-flavor", dateISO8601 = publishedAt)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `formatDateFailure Should return three dash When use case is invoked with an invalid dateISO8601`() {
        // Given
        val publishedAt = "06/07/2023"
        val expectedResult = "---"

        // When
        val result = subject(flavorName = "newsapi", dateISO8601 = publishedAt)

        // Then
        assertEquals(expectedResult, result)
    }
}
