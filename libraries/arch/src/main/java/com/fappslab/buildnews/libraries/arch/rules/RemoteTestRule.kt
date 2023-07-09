package com.fappslab.buildnews.libraries.arch.rules

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PRIVATE)
class RemoteTestRule(
    private val modules: List<Module>
) : TestWatcher(), KoinTest {

    private val mockWebServer = MockWebServer()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    override fun starting(description: Description) {
        super.starting(description)
        startKoin {
            modules(modules + createRetrofitModule())
        }
    }

    override fun finished(description: Description) {
        super.finished(description)
        stopKoin()
        mockWebServer.shutdown()
    }

    fun mockWebServerResponse(body: String, code: Int) {
        mockWebServer.enqueue(MockResponse().setBody(body).setResponseCode(code))
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified Service> createTestService(): Service =
        retrofit.create(Service::class.java)

    private fun createRetrofitModule(): Module {
        return module(override = true) {
            single<Retrofit> { retrofit }
        }
    }
}
