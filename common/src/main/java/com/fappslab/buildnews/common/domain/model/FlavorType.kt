package com.fappslab.buildnews.common.domain.model

enum class FlavorType(val iso8601: String) {
    NewsAPI(iso8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'"),
    NYTimesAPI(iso8601 = "yyyy-MM-dd"),
    None(iso8601 = "");

    companion object {
        fun getType(flavorName: String): FlavorType =
            when (flavorName.lowercase()) {
                NewsAPI.name.lowercase() -> NewsAPI
                NYTimesAPI.name.lowercase() -> NYTimesAPI
                else -> None
            }
    }
}
