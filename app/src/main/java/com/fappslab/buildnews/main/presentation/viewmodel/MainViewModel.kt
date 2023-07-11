package com.fappslab.buildnews.main.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.buildnews.BuildConfig
import com.fappslab.buildnews.common.domain.model.Articles.Article
import com.fappslab.buildnews.libraries.arch.viewmodel.ViewModel
import com.fappslab.buildnews.main.domain.provider.UseCaseProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.net.URL

class MainViewModel(
    private val provider: UseCaseProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<MainViewState, MainViewAction>(MainViewState()) {

    init {
        getBooks()
    }

    private fun getBooks() {
        provider.getArticlesUseCase()
            .flowOn(dispatcher)
            .onStart { onState { it.copy(childPosition = LOADING_CHILD) } }
            .catch { cause -> onState { it.getBooksFailure(cause.message) } }
            .onEach { articles -> onState { it.getBooksSuccess(articles) } }
            .launchIn(viewModelScope)
    }

    fun onAdapterItem(article: Article) {
        provider.getFormattedDateUseCase(
            flavorName = BuildConfig.FLAVOR,
            dateISO8601 = article.publishedAt
        ).also { formattedDate ->
            onState { it.showDetailsState(article, formattedDate) }
        }
    }

    fun onDismissFeedbackDetails() {
        onState { it.copy(shouldShowDetails = false) }
    }

    fun onTryAgain() {
        onDismissFeedbackError()
        getBooks()
    }

    fun onDismissFeedbackError() {
        onState { it.copy(shouldShowError = false) }
    }

    fun onOpenBrowser(url: String) {
        val action = if (isValidUrl(url)) {
            MainViewAction.OpenBrowser(url)
        } else MainViewAction.ShowOpenBrowserError

        onAction { action }
    }

    private fun isValidUrl(url: String): Boolean =
        runCatching {
            URL(url).run { true }
        }.getOrDefault(defaultValue = false)
}
