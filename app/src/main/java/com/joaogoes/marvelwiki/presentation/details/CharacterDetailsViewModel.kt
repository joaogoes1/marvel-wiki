package com.joaogoes.marvelwiki.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.domain.GetCharacterUseCase
import com.joaogoes.marvelwiki.domain.RemoveSavedFavoriteUseCase
import com.joaogoes.marvelwiki.domain.SaveFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    fun favoriteCharacter() = viewModelScope.launch {
        viewState.character.value?.let { character ->
            saveFavorite(character).onSuccess {
                handleFavoriteCharacterSuccess(character)
            }
        }
    }

    fun removeFavoriteCharacter() = viewModelScope.launch {
        viewState.character.value?.let { character ->
            removeSavedFavorite(character).onSuccess {
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