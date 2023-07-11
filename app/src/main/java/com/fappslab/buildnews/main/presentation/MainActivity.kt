package com.fappslab.buildnews.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fappslab.buildnews.databinding.ActivityMainBinding
import com.fappslab.buildnews.details.presentation.extension.navigateToLinkIntent
import com.fappslab.buildnews.details.presentation.extension.showErrorBuyBookAction
import com.fappslab.buildnews.details.presentation.extension.showFeedbackDetails
import com.fappslab.buildnews.details.presentation.extension.showFeedbackError
import com.fappslab.buildnews.libraries.arch.viewbinding.viewBinding
import com.fappslab.buildnews.libraries.arch.viewmodel.onViewAction
import com.fappslab.buildnews.libraries.arch.viewmodel.onViewState
import com.fappslab.buildnews.main.presentation.adapter.MainAdapter
import com.fappslab.buildnews.main.presentation.viewmodel.MainViewAction
import com.fappslab.buildnews.main.presentation.viewmodel.MainViewModel
import com.fappslab.buildnews.main.presentation.viewmodel.MainViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.fappslab.buildnews.libraries.design.R as DS

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModel()
    private val adapter by lazy { MainAdapter(viewModel::onAdapterItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservables()
        setupRecycler()
        setupListeners()
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->
            state.showFeedbackErrorState()
            state.showFeedbackDetailsState()
            adapter.submitList(state.articles)
            flipperChildState(state.childPosition)
        }

        onViewAction(viewModel) { action ->
            when (action) {
                is MainViewAction.OpenBrowser -> navigateToExternalLinkAction(action.url)
                MainViewAction.BackPressed -> finish()
                MainViewAction.ShowOpenBrowserError -> showErrorBuyBookAction()
            }
        }
    }

    private fun setupRecycler() = binding.run {
        recyclerMain.adapter = adapter
        recyclerMain.itemAnimator = null
    }

    private fun setupListeners() = binding.run {
        includeEmpty.buttonTryAgain.setOnClickListener { viewModel.onTryAgain() }
    }

    private fun flipperChildState(childPosition: Int) {
        binding.flipperContainer.displayedChild = childPosition
    }

    private fun MainViewState.showFeedbackDetailsState() = article?.let {
        showFeedbackDetails(
            article = it,
            shouldShow = shouldShowDetails,
            dismissAction = viewModel::onDismissFeedbackDetails,
            openBrowser = viewModel::onOpenBrowser
        )
    }

    private fun MainViewState.showFeedbackErrorState() {
        showFeedbackError(
            shouldShow = shouldShowError,
            message = errorMessage ?: getString(DS.string.common_error_message),
            primaryAction = viewModel::onTryAgain,
            dismissAction = viewModel::onDismissFeedbackError
        )
    }

    private fun navigateToExternalLinkAction(url: String) {
        url.navigateToLinkIntent()
            .also(::startActivity)
    }
}
