package com.joaogoes.marvelwiki.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.databinding.FavoriteFragmentBinding
import com.joaogoes.marvelwiki.di.Injectable
import com.joaogoes.marvelwiki.presentation.home.HomeFragmentDirections
import com.joaogoes.marvelwiki.presentation.utils.showOnly
import javax.inject.Inject

class FavoritesFragment : Fragment(R.layout.favorite_fragment), FavoritesFragmentListener,
    Injectable {

    @Inject
    lateinit var viewModel: FavoritesViewModel
    val binding: FavoriteFragmentBinding by viewBinding(FavoriteFragmentBinding::bind)
    val adapter = FavoriteAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.favoritesList.adapter = adapter
        setupTryAgainButton()
        observeViewState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorites()
    }

    private fun observeViewState() {
        viewModel.viewState.favorites.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.viewState.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                FavoriteViewState.State.EMPTY_STATE -> binding.showOnly(binding.favoritesFragmentEmptyState.root)
                FavoriteViewState.State.SUCCESS -> binding.showOnly(binding.favoritesList)
                FavoriteViewState.State.ERROR -> binding.showOnly(binding.favoritesFragmentErrorState.root)
                FavoriteViewState.State.LOADING -> binding.showOnly(binding.favoritesFragmentLoadingState.root)
            }
        })
    }

    private fun setupTryAgainButton() {
        binding.favoritesFragmentErrorState.tryAgain.setOnClickListener {
            viewModel.loadFavorites()
        }
    }

    override fun openCharacter(characterId: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCharacterDetailsFragment(
                characterId
            )
        )
    }

    override fun removeSavedFavorite(favorite: FavoriteModel) {
        viewModel.removeFavorite(favorite)
    }
}