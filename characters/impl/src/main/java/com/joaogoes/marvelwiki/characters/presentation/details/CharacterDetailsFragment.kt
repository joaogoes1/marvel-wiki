package com.joaogoes.marvelwiki.characters.presentation.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.characters.R
import com.joaogoes.marvelwiki.characters.data.model.ComicModel
import com.joaogoes.marvelwiki.characters.data.model.SeriesModel
import com.joaogoes.marvelwiki.characters.databinding.CharacterDetailsFragmentBinding
import com.joaogoes.marvelwiki.utils.view.showOnly
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.character_details_fragment) {

    private val viewModel by viewModels<CharacterDetailsViewModel>()
    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private val comicsAdapter = CharacterDetailsSectionAdapter(ComicsDiffUtil)
    private val seriesAdapter = CharacterDetailsSectionAdapter(SeriesDiffUtil)
    private val binding: CharacterDetailsFragmentBinding by viewBinding(
        CharacterDetailsFragmentBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupMenu()
        setupRecyclerViews()
        setupTryAgainButton()
        observeState()
        observeCharacter()
        viewModel.loadCharacter(args.characterId)
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

    @SuppressLint("WrongConstant")
    private fun setupRecyclerViews() {
        val context = requireContext()
        val orientation = LinearLayoutManager.HORIZONTAL

        binding.comicsList.apply {
            layoutManager = LinearLayoutManager(context, orientation, false)
            adapter = comicsAdapter
        }
        binding.seriesList.apply {
            layoutManager = LinearLayoutManager(requireContext(), orientation, false)
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
        viewModel.viewState.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                CharacterDetailsViewState.State.SUCCESS -> binding.showOnly(binding.characterDetailsContainer)
                CharacterDetailsViewState.State.ERROR -> binding.showOnly(binding.charactersFragmentErrorState.root)
                CharacterDetailsViewState.State.LOADING -> binding.showOnly(binding.charactersFragmentLoadingState.root)
                CharacterDetailsViewState.State.NO_CONNECTION -> binding.showOnly(binding.charactersFragmentNoConnectionState.root)
            }
        })
    }

    private fun observeCharacter() {
        viewModel.viewState.character.observe(viewLifecycleOwner, { character ->
            binding.apply {
                name = character?.name ?: ""
                imageUrl = character?.imageUrl ?: ""
            }
            updateDescription(character?.description)
            updateComicList(character?.comics)
            updateSeriesList(character?.series)
            updateMenu(character?.isFavorite ?: false)
        })
    }

    private fun updateDescription(description: String?) {
        binding.description = if (description.isNullOrBlank())
            getString(R.string.characters_details_no_description)
        else
            description
    }

    private fun updateComicList(comics: List<ComicModel>?) {
        binding.comicsList.visibility = if (comics.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.comicsListTitle.visibility = if (comics.isNullOrEmpty()) View.GONE else View.VISIBLE
        comicsAdapter.submitList(comics)
    }

    private fun updateSeriesList(series: List<SeriesModel>?) {
        binding.seriesList.visibility = if (series.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.seriesListTitle.visibility = if (series.isNullOrEmpty()) View.GONE else View.VISIBLE
        seriesAdapter.submitList(series)
    }

    private fun updateMenu(isFavorite: Boolean) {
        val favoriteItem = binding.characterDetailsFragmentToolbar.menu.findItem(R.id.favorite_item)
        val removeFavoriteItem =
            binding.characterDetailsFragmentToolbar.menu.findItem(R.id.remove_favorite_item)

        if (isFavorite) {
            favoriteItem.isVisible = true
            removeFavoriteItem.isVisible = false
        } else {
            favoriteItem.isVisible = false
            removeFavoriteItem.isVisible = true
        }
    }
}