package com.joaogoes.marvelwiki.characters.presentation.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.characters.R
import com.joaogoes.marvelwiki.characters.data.model.ComicModel
import com.joaogoes.marvelwiki.characters.data.model.SeriesModel
import com.joaogoes.marvelwiki.characters.databinding.CharacterDetailsFragmentBinding
import com.joaogoes.marvelwiki.utils.databinding.loadImage
import com.joaogoes.marvelwiki.utils.view.showOnly
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.character_details_fragment) {

    private val viewModel by viewModels<CharacterDetailsViewModel>()
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val comicsAdapter = CharacterDetailsSectionAdapter(ComicsDiffUtil)
    private val seriesAdapter = CharacterDetailsSectionAdapter(SeriesDiffUtil)
    private val binding by viewBinding(CharacterDetailsFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupMenu()
        setupToolbar()
        setupRecyclerViews()
        setupTryAgainButton()
        observeState()
        viewModel.loadCharacter(args.characterId)
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity?)?.setSupportActionBar(binding.characterDetailsFragmentToolbar)
    }

    private fun setupMenu() {
        binding.characterDetailsFragmentToolbar.inflateMenu(R.menu.character_details_fragment_menu)
        binding.characterDetailsFragmentToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.favorite_item -> {
                    viewModel.removeFavoriteCharacter()
                    true
                }
                R.id.remove_favorite_item -> {
                    viewModel.favoriteCharacter()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerViews() {
        val context = requireContext()
        val orientation = RecyclerView.HORIZONTAL

        binding.comicsList.apply {
            layoutManager = LinearLayoutManager(context, orientation, false)
            adapter = comicsAdapter
        }
        binding.seriesList.apply {
            layoutManager = LinearLayoutManager(context, orientation, false)
            adapter = seriesAdapter
        }

    }

    private fun setupTryAgainButton() {
        binding.charactersFragmentErrorState.tryAgain.setOnClickListener {
            viewModel.loadCharacter(args.characterId)
        }
        binding.charactersFragmentNoConnectionState.tryAgain.setOnClickListener {
            viewModel.loadCharacter(args.characterId)
        }
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is CharacterDetailsViewState.Success -> {
                    val character = state.characterModel
                    activity?.title = character.name
                    binding.characterDetailsImage.loadImage(character.imageUrl ?: "")
                    updateDescription(character.description)
                    updateComicList(character.comics)
                    updateSeriesList(character.series)
                    updateMenu(character.isFavorite)
                    binding.showOnly(binding.characterDetailsContainer)
                }
                is CharacterDetailsViewState.Error -> binding.showOnly(binding.charactersFragmentErrorState.root)
                is CharacterDetailsViewState.Loading -> binding.showOnly(binding.charactersFragmentLoadingState.root)
                is CharacterDetailsViewState.NoConnection -> binding.showOnly(binding.charactersFragmentNoConnectionState.root)
            }
        })
    }

    private fun updateDescription(description: String?) {
        binding.characterDetailsDescription.text = if (description.isNullOrBlank())
            getString(R.string.characters_details_no_description)
        else
            description
    }

    private fun updateComicList(comics: List<ComicModel>?) {
        binding.comicsList.isVisible = !comics.isNullOrEmpty()
        binding.comicsListTitle.isVisible = !comics.isNullOrEmpty()
        comicsAdapter.submitList(comics)
    }

    private fun updateSeriesList(series: List<SeriesModel>?) {
        binding.seriesList.isVisible = !series.isNullOrEmpty()
        binding.seriesListTitle.isVisible = !series.isNullOrEmpty()
        seriesAdapter.submitList(series)
    }

    private fun updateMenu(isFavorite: Boolean) {
        val favoriteItem = binding.characterDetailsFragmentToolbar.menu.findItem(R.id.favorite_item)
        val removeFavoriteItem =
            binding.characterDetailsFragmentToolbar.menu.findItem(R.id.remove_favorite_item)

        if (isFavorite) {
            favoriteItem?.isVisible = true
            removeFavoriteItem?.isVisible = false
        } else {
            favoriteItem?.isVisible = false
            removeFavoriteItem?.isVisible = true
        }
    }
}