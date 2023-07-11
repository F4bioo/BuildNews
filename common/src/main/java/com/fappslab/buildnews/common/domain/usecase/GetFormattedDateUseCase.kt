package com.fappslab.buildnews.common.domain.usecase

import com.fappslab.buildnews.common.domain.model.FlavorType
import com.fappslab.buildnews.libraries.arch.extension.orDash
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

private const val PATTER = "dd/MM/yyyy"

class GetFormattedDateUseCase {

    operator fun invoke(flavorName: String, dateISO8601: String): String {
        return runCatching {
            when (val type = FlavorType.getType(flavorName)) {
                FlavorType.NewsAPI -> DateTimeFormat.forPattern(type.iso8601)
                FlavorType.NYTimesAPI -> DateTimeFormat.forPattern(type.iso8601)
                else -> throw IllegalArgumentException("Invalid flavor")
            }.let { formatter -> DateTime.parse(dateISO8601, formatter).toString(PATTER) }

        }.fold(
            onFailure = { "---" },
            onSuccess = { formattedDate ->
                formattedDate.orDash()
            }
        )
    }
}
