package com.fappslab.buildnews.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt.PromptInfo
import com.fappslab.buildnews.R
import com.fappslab.buildnews.databinding.ActivityMainBinding
import com.fappslab.buildnews.details.presentation.extension.navigateToLinkIntent
import com.fappslab.buildnews.details.presentation.extension.showErrorSeeMoreAction
import com.fappslab.buildnews.details.presentation.extension.showFeedbackDetails
import com.fappslab.buildnews.details.presentation.extension.showFeedbackError
import com.fappslab.buildnews.libraries.arch.viewbinding.viewBinding
import com.fappslab.buildnews.libraries.arch.viewmodel.onViewAction
import com.fappslab.buildnews.libraries.arch.viewmodel.onViewState
import com.fappslab.buildnews.main.presentation.adapter.MainAdapter
import com.fappslab.buildnews.main.presentation.extension.biometricListeners
import com.fappslab.buildnews.main.presentation.extension.checkDeviceHasBiometric
import com.fappslab.buildnews.main.presentation.viewmodel.MainViewAction
import com.fappslab.buildnews.main.presentation.viewmodel.MainViewModel
import com.fappslab.buildnews.main.presentation.viewmodel.MainViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.fappslab.buildnews.libraries.design.R as DS

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModel()
    private val adapter by lazy { MainAdapter(viewModel::onAdapterItem) }
    private val biometricPrompt by lazy {
        biometricListeners(viewModel::onBiometricError, viewModel::getArticles)
    }
    private var prompt: PromptInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservables()
        setupRecycler()
        setupListeners()
        setupBiometricDialog()
        setupCheckBiometric()
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
                MainViewAction.ShowOpenBrowserError -> showErrorSeeMoreAction()
                MainViewAction.ShowBiometricPrompt -> showBiometricPromptAction()
            }
        }
    }

    private fun setupRecycler() = binding.run {
        recyclerMain.adapter = adapter
        recyclerMain.itemAnimator = null
    }

    private fun setupListeners() = binding.run {
        includeEmpty.buttonTryAgain.setOnClickListener { viewModel.onTryAgain() }
        includeBiometric.buttonVerify.setOnClickListener { viewModel.onShowBiometricPrompt() }
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
        url.navigateToLinkIntent().also(::startActivity)
    }

    private fun showBiometricPromptAction() {
        prompt?.let(biometricPrompt::authenticate)
    }

    private fun setupCheckBiometric() {
        checkDeviceHasBiometric(viewModel::getArticles) {
            showBiometricPromptAction()
            viewModel.onCanAuthenticate()
        }
    }

    private fun setupBiometricDialog() {
        val appName = getString(R.string.app_name)
        prompt = PromptInfo
            .Builder()
            .setTitle(getString(R.string.verify_identity))
            .setDescription(getString(R.string.fingerprint_description, appName))
            .setNegativeButtonText(getString(DS.string.common_close)).build()
    }
}
