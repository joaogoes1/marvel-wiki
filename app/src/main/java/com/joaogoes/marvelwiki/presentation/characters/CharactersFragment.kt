package com.joaogoes.marvelwiki.presentation.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.databinding.CharactersFragmentBinding
import com.joaogoes.marvelwiki.di.Injectable
import javax.inject.Inject

class CharactersFragment : Fragment(R.layout.characters_fragment), CharactersFragmentListener, Injectable {

    @Inject
    lateinit var viewModel: CharactersViewModel
    private val binding: CharactersFragmentBinding by viewBinding(CharactersFragmentBinding::bind)
    private val adapter = CharactersAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.charactersList.adapter = adapter
        setupSwipeToRefresh()
        observeViewState()
        viewModel.loadCharacters()
    }

    private fun setupSwipeToRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = true
            viewModel.loadCharacters()
        }
    }
    private fun observeViewState() {
        viewModel.viewState.characters.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.viewState.state.observe(viewLifecycleOwner, { state ->
            binding.swipeToRefresh.isRefreshing = false
            hideAll()
            when (state) {
                CharactersViewState.State.EMPTY_STATE -> showEmptyState()
                CharactersViewState.State.SUCCESS -> showSuccessState()
                CharactersViewState.State.ERROR -> showErrorState()
                CharactersViewState.State.LOADING -> showLoadingState()
                CharactersViewState.State.NO_CONNECTION -> showNoConnectionState()
            }
        })
    }

    private fun showSuccessState() {
        binding.swipeToRefresh.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding.emptyState.root.visibility = View.VISIBLE
    }

    private fun showErrorState() {
        binding.errorState.root.visibility = View.VISIBLE
        binding.errorState.tryAgain.setOnClickListener {
            viewModel.loadCharacters()
        }
    }

    private fun showLoadingState() {
        binding.loadingState.root.visibility = View.VISIBLE
    }

    private fun showNoConnectionState() {
        binding.noConnectionState.root.visibility = View.VISIBLE
        binding.noConnectionState.tryAgain.setOnClickListener {
            viewModel.loadCharacters()
        }
    }

    private fun hideAll() {
        binding.swipeToRefresh.visibility = View.GONE
        binding.emptyState.root.visibility = View.GONE
        binding.errorState.root.visibility = View.GONE
        binding.noConnectionState.root.visibility = View.GONE
        binding.loadingState.root.visibility = View.GONE
    }

    override fun saveFavorite(character: CharacterModel) {
        viewModel.favoriteCharacter(character)
    }

    override fun openCharacter(characterId: Int) {
        TODO("Not yet implemented")
    }
}