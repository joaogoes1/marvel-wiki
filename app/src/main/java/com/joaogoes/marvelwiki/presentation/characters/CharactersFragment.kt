package com.joaogoes.marvelwiki.presentation.characters

import android.os.Bundle
import android.view.View
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
        setupMenu()
        setupSwipeToRefresh()
        setupTryAgainButton()
        observeViewState()
        observeViewType()
        viewModel.loadCharacters()
    }

    private fun setupMenu() {
        binding.charactersFragmentToolbar.inflateMenu(R.menu.characters_fragment_menu)
        binding.charactersFragmentToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.grid_mode -> {
                    viewModel.viewState.viewType.postValue(CharactersAdapter.ViewType.GRID)
                    true
                }
                R.id.list_mode -> {
                    viewModel.viewState.viewType.postValue(CharactersAdapter.ViewType.LIST)
                    true
                }
                else -> false
            }
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
            val gridItem = binding.charactersFragmentToolbar.menu.findItem(R.id.grid_mode)
            val listItem = binding.charactersFragmentToolbar.menu.findItem(R.id.list_mode)
            when (viewType) {
                CharactersAdapter.ViewType.LIST -> {
                    gridItem.isVisible = true
                    listItem.isVisible = false
                    binding.charactersList.layoutManager = LinearLayoutManager(requireContext())
                }
                CharactersAdapter.ViewType.GRID -> {
                    listItem.isVisible = true
                    gridItem.isVisible = false
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
                CharactersViewState.State.EMPTY_STATE -> binding.showOnly(binding.emptyState.root)
                CharactersViewState.State.SUCCESS -> {
                    binding.showOnly(binding.swipeToRefresh)
                    binding.charactersFragmentToolbar.visibility = View.VISIBLE
                }
                CharactersViewState.State.ERROR -> binding.showOnly(binding.errorState.root)
                CharactersViewState.State.LOADING -> binding.showOnly(binding.loadingState.root)
                CharactersViewState.State.NO_CONNECTION -> binding.showOnly(binding.noConnectionState.root)
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
        binding.noConnectionState.tryAgain.setOnClickListener {
            viewModel.loadCharacters()
        }
        binding.errorState.tryAgain.setOnClickListener {
            viewModel.loadCharacters()
        }
    }
}
