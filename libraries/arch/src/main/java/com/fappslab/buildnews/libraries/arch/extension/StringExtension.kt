package com.fappslab.buildnews.libraries.arch.extension

fun String?.orDash() = this ?: "---"

fun Int?.orDash() = if (isNotNull()) toString() else "---"

fun String?.httpsOrEmpty(): String = this?.replace(
    oldValue = "http://", newValue = "https://"
).orEmpty()

fun String.capitalize(): String =
    replaceFirstChar { it.uppercase() }
