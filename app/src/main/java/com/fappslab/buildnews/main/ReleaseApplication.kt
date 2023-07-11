package com.fappslab.buildnews.main

import android.app.Application
import com.fappslab.buildnews.BuildConfig
import com.fappslab.buildnews.main.di.AppModule
import com.fappslab.buildnews.di.FlavorModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import timber.log.Timber

open class ReleaseApplication : Application() {

    private val appDeclaration
        get(): KoinAppDeclaration = {
            Timber.plant(Timber.DebugTree())
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ReleaseApplication)
        }

    override fun onCreate() {
        super.onCreate()
        startKoin(appDeclaration = appDeclaration)
        koinLoad()
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

    private fun koinLoad() {
        AppModule.load()
        FlavorModule.load()
    }
}
