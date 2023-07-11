package com.fappslab.buildnews.main.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fappslab.buildnews.common.domain.model.Articles.Article
import com.fappslab.buildnews.libraries.arch.adapter.CommonDiffCallback

class MainAdapter(
    private val itemClicked: OnMainItemClicked
) : ListAdapter<Article, MainHolder>(CommonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder =
        MainHolder.create(parent, itemClicked)

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }
}
