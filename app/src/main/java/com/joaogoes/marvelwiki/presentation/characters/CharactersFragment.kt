package com.joaogoes.marvelwiki.presentation.characters

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.databinding.CharactersFragmentBinding
import com.joaogoes.marvelwiki.di.Injectable
import com.joaogoes.marvelwiki.presentation.home.HomeFragmentDirections
import com.joaogoes.marvelwiki.presentation.utils.showOnly
import javax.inject.Inject

class CharactersFragment : Fragment(R.layout.characters_fragment), CharactersFragmentListener,
    Injectable {

    @Inject
    lateinit var viewModel: CharactersViewModel
    private val binding: CharactersFragmentBinding by viewBinding(CharactersFragmentBinding::bind)
    private val adapter = CharactersAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.charactersList.adapter = adapter
        setupSwitch()
        setupSwipeToRefresh()
        setupTryAgainButton()
        observeViewState()
        observeViewType()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCharacters()
    }

    private fun setupSwitch() {
        binding.charactersFragmentDisplayModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                viewModel.viewState.viewType.postValue(CharactersAdapter.ViewType.LIST)
            else
                viewModel.viewState.viewType.postValue(CharactersAdapter.ViewType.GRID)
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = true
            viewModel.loadCharacters()
        }
    }

    private fun observeViewType() {
        viewModel.viewState.viewType.observe(viewLifecycleOwner, { viewType ->
            when (viewType) {
                CharactersAdapter.ViewType.LIST -> {
                    binding.charactersFragmentDisplayModeSwitch.isChecked = true
                    binding.charactersList.layoutManager = LinearLayoutManager(requireContext())
                }
                CharactersAdapter.ViewType.GRID -> {
                    binding.charactersFragmentDisplayModeSwitch.isChecked = false
                    binding.charactersList.layoutManager = GridLayoutManager(requireContext(), 2)
                }
            }
            adapter.viewType = viewType
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeViewState() {
        viewModel.viewState.characters.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.viewState.state.observe(viewLifecycleOwner, { state ->
            binding.swipeToRefresh.isRefreshing = false
            when (state) {
                CharactersViewState.State.EMPTY_STATE -> binding.showOnly(binding.charactersFragmentEmptyState.root)
                CharactersViewState.State.SUCCESS -> binding.showOnly(binding.swipeToRefresh)
                CharactersViewState.State.ERROR -> binding.showOnly(binding.charactersFragmentErrorState.root)
                CharactersViewState.State.LOADING -> binding.showOnly(binding.charactersFragmentLoadingState.root)
                CharactersViewState.State.NO_CONNECTION -> binding.showOnly(binding.charactersFragmentNoConnectionState.root)
            }
        })
    }

    override fun saveFavorite(character: CharacterModel) {
        viewModel.favoriteCharacter(character)
    }

    override fun openCharacter(characterId: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCharacterDetailsFragment(
                characterId
            )
        )
    }

    private fun setupTryAgainButton() {
        binding.charactersFragmentNoConnectionState.tryAgain.setOnClickListener {
            viewModel.loadCharacters()
        }
        binding.charactersFragmentErrorState.tryAgain.setOnClickListener {
            viewModel.loadCharacters()
        }
    }
}
