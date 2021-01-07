package com.joaogoes.marvelwiki.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.databinding.VerticalGridCardItemBinding
import com.joaogoes.marvelwiki.presentation.characters.CharactersFragmentListener

class FavoriteAdapter(
    private val listener: FavoritesFragmentListener
) : ListAdapter<FavoriteModel, FavoriteAdapter.FavoritesViewHolder>(FavoritesDiffUtil) {

    class FavoritesViewHolder(val binding: VerticalGridCardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return FavoritesViewHolder(
            VerticalGridCardItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = currentList[position]
        holder.binding.apply {
            name = item.name
            imageUrl = item.imageUrl
            favoriteButton.setBackgroundResource(R.drawable.ic_star_filled)
            favoriteButton.setOnClickListener { listener.removeSavedFavorite(item) }
            container.setOnClickListener { listener.openCharacter(item.id) }
        }
    }

    object FavoritesDiffUtil : DiffUtil.ItemCallback<FavoriteModel>() {
        override fun areItemsTheSame(
            oldItem: FavoriteModel,
            newItem: FavoriteModel
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FavoriteModel,
            newItem: FavoriteModel
        ): Boolean = oldItem == newItem
    }
}
