package com.joaogoes.marvelwiki.presentation.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.databinding.CharactersFragmentBinding
import com.joaogoes.marvelwiki.di.Injectable
import javax.inject.Inject

class CharactersFragment : Fragment(R.layout.characters_fragment), Injectable {

    @Inject
    lateinit var viewModel: CharactersViewModel
    private val binding: CharactersFragmentBinding by viewBinding(CharactersFragmentBinding::bind)
    private val adapter = CharactersAdapter()

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
            when (state) {
                CharactersViewState.State.EMPTY_STATE -> showEmptyState()
                CharactersViewState.State.SUCCESS -> showSuccessState()
                CharactersViewState.State.ERROR -> showErrorState()
                CharactersViewState.State.LOADING -> showLoadingState()
            }
        })
    }

    private fun showSuccessState() {
        binding.swipeToRefresh.visibility = View.VISIBLE
        binding.emptyState.root.visibility = View.GONE
        binding.errorState.root.visibility = View.GONE
        binding.loadingState.root.visibility = View.GONE
    }

    private fun showEmptyState() {
        binding.swipeToRefresh.visibility = View.GONE
        binding.emptyState.root.visibility = View.VISIBLE
        binding.errorState.root.visibility = View.GONE
        binding.loadingState.root.visibility = View.GONE
    }

    private fun showErrorState() {
        binding.swipeToRefresh.visibility = View.GONE
        binding.emptyState.root.visibility = View.GONE
        binding.errorState.root.visibility = View.VISIBLE
        binding.loadingState.root.visibility = View.GONE
        binding.errorState.tryAgain.setOnClickListener {
            viewModel.loadCharacters()
        }
    }

    private fun showLoadingState() {
        binding.swipeToRefresh.visibility = View.GONE
        binding.emptyState.root.visibility = View.GONE
        binding.errorState.root.visibility = View.GONE
        binding.loadingState.root.visibility = View.VISIBLE
    }
}