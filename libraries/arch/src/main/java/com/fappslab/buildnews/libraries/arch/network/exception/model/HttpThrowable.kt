package com.fappslab.buildnews.libraries.arch.network.exception.model

data class HttpThrowable(
    val code: Int? = null,
    override val message: String? = null,
    private val throwable: Throwable? = null
) : Throwable(message, throwable)
