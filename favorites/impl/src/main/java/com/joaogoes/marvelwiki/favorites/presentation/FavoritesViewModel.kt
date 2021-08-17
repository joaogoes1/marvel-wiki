package com.joaogoes.marvelwiki.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.favorites.domain.GetFavoritesUseCase
import com.joaogoes.marvelwiki.favorites.domain.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
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
    fun removeFavorite(favorite: com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel) =
        viewModelScope.launch {
            removeSavedFavorite(favorite)
                .onSuccess {
                    loadFavorites()
                }
        }
}