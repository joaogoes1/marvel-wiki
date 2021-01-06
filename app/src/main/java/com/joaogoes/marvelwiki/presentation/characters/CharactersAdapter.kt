package com.joaogoes.marvelwiki.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.databinding.CharactersCardBinding

// TODO: Use Glide to render image
class CharactersAdapter :
    ListAdapter<CharacterModel, CharactersAdapter.CharacterViewHolder>(CharactersDiffUtil) {

    class CharacterViewHolder(val binding: CharactersCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(CharactersCardBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = currentList[position]
        holder.binding.apply {
            name = item.name
            imageUrl = item.imageUrl
        }
    }

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