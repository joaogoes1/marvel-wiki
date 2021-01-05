package com.joaogoes.marvelwiki.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaogoes.marvelwiki.databinding.CharactersCardBinding

// TODO: Use Glide to render image
class CharactersAdapter :
    ListAdapter<CharactersItemUiModel, CharactersAdapter.CharacterViewHolder>(CharactersDiffUtil) {

    class CharacterViewHolder(val binding: CharactersCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(CharactersCardBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = currentList[position]
        holder.binding.apply {
            uiModel = item
        }
    }

    object CharactersDiffUtil : DiffUtil.ItemCallback<CharactersItemUiModel>() {
        override fun areItemsTheSame(
            oldItem: CharactersItemUiModel,
            newItem: CharactersItemUiModel
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: CharactersItemUiModel,
            newItem: CharactersItemUiModel
        ): Boolean = oldItem == newItem

    }
}