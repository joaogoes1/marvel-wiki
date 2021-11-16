package com.joaogoes.marvelwiki.characters.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaogoes.marvelwiki.characters.data.model.ComicModel
import com.joaogoes.marvelwiki.characters.data.model.SeriesModel
import com.joaogoes.marvelwiki.characters.databinding.HorizontalListCardItemBinding
import com.joaogoes.marvelwiki.utils.databinding.loadImage

class CharacterDetailsSectionAdapter<E, T : CharacterDetailsSectionDiffUtil<E>>(private val diffUtil: T) :
    ListAdapter<E, CharacterDetailsSectionAdapter.CharacterViewHolder>(diffUtil) {

    class CharacterViewHolder(val binding: HorizontalListCardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(
            HorizontalListCardItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = currentList[position]
        diffUtil.bindViewHolder(holder, item)
    }
}

abstract class CharacterDetailsSectionDiffUtil<T> : DiffUtil.ItemCallback<T>() {
    abstract fun bindViewHolder(holder: CharacterDetailsSectionAdapter.CharacterViewHolder, item: T)
}

object ComicsDiffUtil : CharacterDetailsSectionDiffUtil<ComicModel>() {
    override fun areItemsTheSame(
        oldItem: ComicModel,
        newItem: ComicModel
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: ComicModel,
        newItem: ComicModel
    ): Boolean = oldItem == newItem

    override fun bindViewHolder(
        holder: CharacterDetailsSectionAdapter.CharacterViewHolder,
        item: ComicModel
    ) {
        holder.binding.apply {
            characterLabel.text = item.name
            characterImage.loadImage(item.resourceURI)
        }
    }
}

object SeriesDiffUtil : CharacterDetailsSectionDiffUtil<SeriesModel>() {
    override fun areItemsTheSame(
        oldItem: SeriesModel,
        newItem: SeriesModel
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: SeriesModel,
        newItem: SeriesModel
    ): Boolean = oldItem == newItem

    override fun bindViewHolder(
        holder: CharacterDetailsSectionAdapter.CharacterViewHolder,
        item: SeriesModel
    ) {
        holder.binding.apply {
            characterLabel.text = item.name
            characterImage.loadImage(item.resourceURI)
        }
    }
}