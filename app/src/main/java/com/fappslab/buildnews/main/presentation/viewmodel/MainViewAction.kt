package com.fappslab.buildnews.main.presentation.viewmodel

sealed class MainViewAction {
    data class OpenBrowser(val url: String) : MainViewAction()
    object ShowOpenBrowserError : MainViewAction()
    object BackPressed : MainViewAction()
    object ShowBiometricPrompt : MainViewAction()
}
