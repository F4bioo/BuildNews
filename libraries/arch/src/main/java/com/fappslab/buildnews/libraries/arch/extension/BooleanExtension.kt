package com.fappslab.buildnews.libraries.arch.extension

fun <T : Any> T?.isNull() = this == null

fun <T : Any> T?.isNotNull() = !isNull()

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true
