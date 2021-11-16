package com.joaogoes.marvelwiki.favorites.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.favorites.R
import com.joaogoes.favorites.databinding.FavoriteFragmentBinding
import com.joaogoes.marvelwiki.characters.navigation.CharactersNavigator
import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.utils.view.showOnly
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorite_fragment), FavoritesFragmentListener {

//    @Inject
//    lateinit var charactersNavigator: CharactersNavigator
    private val viewModel by viewModels<FavoritesViewModel>()
    private val binding: FavoriteFragmentBinding by viewBinding(FavoriteFragmentBinding::bind)
    private val adapter = FavoriteAdapter(this)

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
//        charactersNavigator.openCharacterDetails(characterId)
    }

    override fun removeSavedFavorite(favorite: FavoriteModel) {
        viewModel.removeFavorite(favorite)
    }
}