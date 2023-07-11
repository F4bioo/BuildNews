package com.fappslab.buildnews.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fappslab.buildnews.common.domain.model.Articles.Article
import com.fappslab.buildnews.databinding.AdapterMainItemBinding
import com.fappslab.buildnews.design.extension.loadImage

typealias OnMainItemClicked = (Article) -> Unit

class MainHolder(
    private val binding: AdapterMainItemBinding,
    private val itemClicked: OnMainItemClicked
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Article) = binding.run {
        textTitle.text = item.title
        imageCover.loadImage(item.imageUrl)
        cardItem.setOnClickListener { itemClicked(item) }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClicked: OnMainItemClicked): MainHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = AdapterMainItemBinding
                .inflate(inflater, parent, false)

            return MainHolder(binding, onItemClicked)
        }
    }
}
