package com.fappslab.buildnews.details.presentation.extension

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.fappslab.buildnews.R
import com.fappslab.buildnews.common.domain.model.Articles.Article
import com.fappslab.buildnews.databinding.DialogDetailsBinding
import com.fappslab.buildnews.design.dsmodal.build
import com.fappslab.buildnews.design.dsmodal.dsFeedbackModal
import com.fappslab.buildnews.design.extension.loadImage
import com.fappslab.buildnews.libraries.design.R as DS

private const val FEEDBACK_ERROR_TAG = "FEEDBACK_ERROR_TAG"
private const val FEEDBACK_DETAILS_TAG = "FEEDBACK_DETAILS_TAG"

fun FragmentActivity.showFeedbackError(
    shouldShow: Boolean,
    message: String,
    primaryAction: () -> Unit,
    dismissAction: () -> Unit
) {
    dsFeedbackModal {
        titleRes = DS.string.common_error_title
        messageText = message
        primaryButton = {
            buttonTextRes = DS.string.common_try_again
            buttonAction = primaryAction
        }
        secondaryButton = {
            buttonTextRes = DS.string.common_cancel
            buttonAction = dismissAction
        }
        closeButton = dismissAction
        shouldBlock = true
    }.build(shouldShow, supportFragmentManager, FEEDBACK_ERROR_TAG)
}

fun FragmentActivity.showFeedbackDetails(
    shouldShow: Boolean,
    article: Article,
    openBrowser: (String) -> Unit,
    dismissAction: () -> Unit
) {
    val binding = DialogDetailsBinding.inflate(layoutInflater).apply {
        textTile.text = article.title
        textMessage.text = article.description
        imageAvatar.loadImage(article.imageUrl)
        buttonClose.setOnClickListener { dismissAction() }
        textPublishedDate.text = getString(R.string.published_date, article.publishedAt)
    }

    dsFeedbackModal {
        customView = binding.root
        primaryButton = {
            buttonTextRes = R.string.see_more
            buttonAction = { openBrowser(article.articleUrl) }
        }
        secondaryButton = {
            buttonTextRes = DS.string.common_close
            buttonAction = dismissAction
        }
        shouldBlock = true
    }.build(shouldShow, supportFragmentManager, FEEDBACK_DETAILS_TAG)
}

fun AppCompatActivity.showErrorSeeMoreAction() {
    Toast.makeText(this, getString(R.string.error_open_browser), Toast.LENGTH_LONG).show()
}


fun String.navigateToLinkIntent(): Intent {
    val url = this
    return Intent(Intent.ACTION_VIEW)
        .apply { data = Uri.parse(url) }
}
