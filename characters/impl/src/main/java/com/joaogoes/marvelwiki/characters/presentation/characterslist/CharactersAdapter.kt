package com.joaogoes.marvelwiki.characters.presentation.characterslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaogoes.marvelwiki.characters.R
import com.joaogoes.marvelwiki.characters.data.model.CharacterModel
import com.joaogoes.marvelwiki.characters.databinding.VerticalGridCardItemBinding
import com.joaogoes.marvelwiki.characters.databinding.VerticalListCardItemBinding


class CharactersAdapter(
    private val listener: CharactersFragmentListener
) : ListAdapter<CharacterModel, CharactersAdapter.CharacterViewHolder>(CharactersDiffUtil) {
    var viewType: ViewType = ViewType.GRID

    sealed class CharacterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        class CharacterGridViewHolder(val binding: VerticalGridCardItemBinding) :
            CharacterViewHolder(binding.root)

        class CharacterListViewHolder(val binding: VerticalListCardItemBinding) :
            CharacterViewHolder(binding.root)
    }

    enum class ViewType(val viewType: Int) { GRID(0), LIST(1) }

    override fun getItemViewType(position: Int): Int {
        return viewType.viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == ViewType.GRID.viewType)
            CharacterViewHolder.CharacterGridViewHolder(
                VerticalGridCardItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        else
            CharacterViewHolder.CharacterListViewHolder(
                VerticalListCardItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = currentList[position]
        when (holder) {
            is CharacterViewHolder.CharacterGridViewHolder -> {
                holder.binding.apply {
                    name = item.name
                    imageUrl = item.imageUrl
                    favoriteButton.setBackgroundResource(getFavoriteIcon(item.isFavorite))
                    favoriteButton.setOnClickListener { listener.saveFavorite(item) }
                    container.setOnClickListener { listener.openCharacter(item.id ?: -1) }
                }
            }
            is CharacterViewHolder.CharacterListViewHolder -> {
                holder.binding.apply {
                    name = item.name
                    imageUrl = item.imageUrl
                    favoriteButton.setBackgroundResource(getFavoriteIcon(item.isFavorite))
                    favoriteButton.setOnClickListener { listener.saveFavorite(item) }
                    container.setOnClickListener { listener.openCharacter(item.id ?: -1) }
                }
            }
        }
    }

    private fun getFavoriteIcon(isFavorite: Boolean) =
        if (isFavorite)
            R.drawable.ic_star_filled
        else
            R.drawable.ic_star_outline

    object CharactersDiffUtil : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(
            oldItem: CharacterModel,
            newItem: CharacterModel
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CharacterModel,
            newItem: CharacterModel
        ): Boolean = oldItem == newItem
    }
}
