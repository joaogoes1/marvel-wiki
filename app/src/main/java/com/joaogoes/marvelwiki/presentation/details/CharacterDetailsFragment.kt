package com.joaogoes.marvelwiki.presentation.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.data.model.ComicModel
import com.joaogoes.marvelwiki.data.model.SeriesModel
import com.joaogoes.marvelwiki.databinding.CharacterDetailsFragmentBinding
import com.joaogoes.marvelwiki.di.Injectable
import com.joaogoes.marvelwiki.presentation.utils.showOnly
import javax.inject.Inject

class CharacterDetailsFragment : Fragment(R.layout.character_details_fragment), Injectable {

    @Inject
    lateinit var viewModel: CharacterDetailsViewModel
    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private val comicsAdapter = CharacterDetailsSectionAdapter(ComicsDiffUtil)
    private val seriesAdapter = CharacterDetailsSectionAdapter(SeriesDiffUtil)
    private val binding: CharacterDetailsFragmentBinding by viewBinding(
        CharacterDetailsFragmentBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerViews()
        setupTryAgainButton()
        observeState()
        observeCharacter()
        viewModel.loadCharacter(args.characterId)
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
        binding.errorState.tryAgain.setOnClickListener {
            viewModel.loadCharacter(args.characterId)
        }
        binding.noConnectionState.tryAgain.setOnClickListener {
            viewModel.loadCharacter(args.characterId)
        }
    }

    private fun observeState() {
        viewModel.viewState.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                CharacterDetailsViewState.State.SUCCESS -> binding.showOnly(binding.characterDetailsContainer)
                CharacterDetailsViewState.State.ERROR -> binding.showOnly(binding.errorState.root)
                CharacterDetailsViewState.State.LOADING -> binding.showOnly(binding.loadingState.root)
                CharacterDetailsViewState.State.NO_CONNECTION -> binding.showOnly(binding.noConnectionState.root)
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
            updateSeriesList(character?.seriesList)
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
}