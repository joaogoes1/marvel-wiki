package com.joaogoes.marvelwiki.characters.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.characters.data.model.CharacterModel
import com.joaogoes.marvelwiki.characters.domain.GetCharacterUseCase
import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.favorites.domain.RemoveSavedFavoriteUseCase
import com.joaogoes.marvelwiki.favorites.domain.SaveFavoriteUseCase
import com.joaogoes.marvelwiki.network.ServiceError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacter: GetCharacterUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val removeSavedFavorite: RemoveSavedFavoriteUseCase,
) : ViewModel() {
    val viewState = CharacterDetailsViewState()

    fun loadCharacter(characterId: Int) = viewModelScope.launch {
        viewState.state.postValue(CharacterDetailsViewState.State.LOADING)
        getCharacter(characterId)
            .onSuccess { character ->
                viewState.character.postValue(character)
                viewState.state.postValue(CharacterDetailsViewState.State.SUCCESS)
            }
            .onError { error ->
                if (error is ServiceError.NoConnectionError)
                    viewState.state.postValue(CharacterDetailsViewState.State.NO_CONNECTION)
                else
                    viewState.state.postValue(CharacterDetailsViewState.State.ERROR)
                viewState.character.postValue(null)
            }
    }

    // TODO: Handle error
    fun favoriteCharacter() = viewModelScope.launch {
        viewState.character.value?.let { character ->
            val id = character.id ?: return@let
            val name = character.name ?: return@let
            val imageUrl = character.imageUrl ?: return@let
            saveFavorite(id, name, imageUrl).onSuccess {
                handleFavoriteCharacterSuccess(character)
            }
        }
    }

    // TODO: Handle error
    fun removeFavoriteCharacter() = viewModelScope.launch {
        viewState.character.value?.let { character ->
            val favorite = FavoriteModel(
                id = character.id ?: return@let,
                name = character.name ?: return@let,
                imageUrl = character.imageUrl ?: return@let
            )
            removeSavedFavorite(favorite).onSuccess {
                handleFavoriteCharacterSuccess(character)
            }
        }
    }

    private fun handleFavoriteCharacterSuccess(character: CharacterModel) {
        val newCharacter = viewState.character.value?.let {
            if (it == character) {
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }
        viewState.character.postValue(newCharacter)
    }
}