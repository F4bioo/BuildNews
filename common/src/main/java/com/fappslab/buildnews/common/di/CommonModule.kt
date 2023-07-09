package com.fappslab.buildnews.common.di

import com.fappslab.buildnews.libraries.arch.koinload.KoinLoad
import org.koin.core.module.Module
import org.koin.dsl.module

object CommonModule : KoinLoad() {

    override val dataModule: Module = module {

    }
}
