package com.fappslab.buildnews.design.dsmodal

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.fappslab.buildnews.design.extension.loadImage
import com.fappslab.buildnews.libraries.arch.extension.isNotNull
import com.fappslab.buildnews.libraries.arch.extension.isNull
import com.fappslab.buildnews.libraries.arch.viewbinding.viewBinding
import com.fappslab.buildnews.libraries.design.R
import com.fappslab.buildnews.libraries.design.databinding.DsFeedbackModalBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DsFeedbackModal : BottomSheetDialogFragment(R.layout.ds_feedback_modal) {

    private val binding: DsFeedbackModalBinding by viewBinding()

    private var primaryButtonConfig: DsButtonConfig? = null
    private var secondaryButtonConfig: DsButtonConfig? = null
    private var primaryButtonAction: (() -> Unit)? = null
    private var secondaryButtonAction: (() -> Unit)? = null

    var customView: View? = null
    var closeButton: (() -> Unit)? = null
    var onDismiss: (() -> Unit)? = null
    var shouldBlock: Boolean = false

    @DrawableRes
    var imageRes: Int? = null
    var imageUrl: String? = null

    @StringRes
    var titleRes: Int? = null
    var titleText: String? = null

    @StringRes
    var messageRes: Int? = null
    var messageText: String? = null

    var primaryButton: (DsButtonConfig.() -> Unit)? = null
        set(value) {
            field = value
            value?.let { block ->
                primaryButtonConfig = DsButtonConfig().apply(block)
                primaryButtonAction = primaryButtonConfig?.buttonAction
            }
        }

    var secondaryButton: (DsButtonConfig.() -> Unit)? = null
        set(value) {
            field = value
            value?.let { block ->
                secondaryButtonConfig = DsButtonConfig().apply(block)
                secondaryButtonAction = secondaryButtonConfig?.buttonAction
            }
        }

    override fun getTheme(): Int {
        return R.style.DsBottomSheet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        primaryButtonConfig?.setupPrimaryButton()
        secondaryButtonConfig?.setupSecondaryButton()
        setupButtonContainer()
        setupBehavior()
        setupDragLine()
        setupCloseButton()
        setupAvatarImage()
        setupTitle()
        setupMessage()
        setupCustomView()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }

    private fun setupBehavior() {
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            if (shouldBlock) {
                isHideable = true
                isCancelable = false
                isDraggable = false
            }
        }
    }

    private fun setupDragLine() {
        binding.dragLine.isInvisible = shouldBlock
    }

    private fun setupCloseButton() {
        binding.buttonClose.setOnClickListener {
            closeButton?.invoke()
            dismissAllowingStateLoss()
        }
    }

    private fun setupAvatarImage() {
        binding.imageAvatar.run {
            imageUrl?.let(::loadImage)
            imageRes?.let(::setImageResource)
            isVisible = imageRes.isNotNull() ||
                    !imageUrl.isNullOrBlank()
        }
        if (imageRes.isNull() && imageUrl.isNullOrEmpty()) {
            binding.textTile.run {
                val layoutParams = this.layoutParams as? ViewGroup.MarginLayoutParams
                layoutParams?.topMargin = 0
                this.layoutParams = layoutParams
            }
        }
    }

    private fun setupTitle() {
        binding.textTile.run {
            titleRes?.let(::setText)
            titleText?.let { text = it }
            isVisible = shouldShow(pairData = titleRes to titleText)
        }
    }

    private fun setupMessage() {
        binding.textMessage.run {
            messageRes?.let(::setText)
            messageText?.let { text = it }
            isVisible = shouldShow(pairData = messageRes to messageText)
        }
    }

    private fun setupCustomView() = binding.run {
        customView?.let {
            customContainer.addView(it)
            customContainer.isVisible = true
            scrollContainer.isVisible = false
            barContainer.isVisible = false
        }
    }

    private fun setupButtonContainer() {
        binding.buttonContainer.isVisible =
            primaryButtonConfig.isNotNull() &&
                    secondaryButtonConfig.isNotNull()
    }

    private fun DsButtonConfig.setupPrimaryButton() {
        binding.buttonPrimary.apply {
            buttonTextRes?.let(::setText)
            buttonText?.let { text = it }
            isVisible = shouldShow(pairData = buttonTextRes to buttonText)
            setOnClickListener {
                primaryButtonAction?.invoke()
                dismissAllowingStateLoss()
            }
        }
    }

    private fun DsButtonConfig.setupSecondaryButton() {
        binding.buttonSecondary.apply {
            buttonTextRes?.let(::setText)
            buttonText?.let { text = it }
            isVisible = shouldShow(pairData = buttonTextRes to buttonText)
            setOnClickListener {
                secondaryButtonAction?.invoke()
                dismissAllowingStateLoss()
            }
        }
    }

    private fun shouldShow(pairData: Pair<Int?, String?>): Boolean {
        val (textRes, textString) = pairData
        return textRes.isNotNull() or textString.isNotNull()
    }
}
