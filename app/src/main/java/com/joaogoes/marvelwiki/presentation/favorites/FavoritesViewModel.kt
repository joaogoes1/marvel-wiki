package com.joaogoes.marvelwiki.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.domain.GetFavoritesUseCase
import com.joaogoes.marvelwiki.domain.RemoveFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val getFavorites: GetFavoritesUseCase,
    private val removeSavedFavorite: RemoveFavoriteUseCase
) : ViewModel() {
    val viewState = FavoriteViewState()

    fun loadFavorites() = viewModelScope.launch {
        viewState.state.postValue(FavoriteViewState.State.LOADING)
        getFavorites()
            .onSuccess {
                viewState.favorites.postValue(it)
                if (it.isEmpty())
                    viewState.state.postValue(FavoriteViewState.State.EMPTY_STATE)
                else
                    viewState.state.postValue(FavoriteViewState.State.SUCCESS)
            }
            .onError {
                viewState.favorites.postValue(emptyList())
                viewState.state.postValue(FavoriteViewState.State.ERROR)
            }
    }

    // TODO: Handle error
    fun removeFavorite(favorite: FavoriteModel) = viewModelScope.launch {
        removeSavedFavorite(favorite)
            .onSuccess {
                loadFavorites()
            }
    }
}