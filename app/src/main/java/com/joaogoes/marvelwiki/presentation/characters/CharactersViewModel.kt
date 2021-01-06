package com.joaogoes.marvelwiki.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.domain.GetCharactersUseCase
import com.joaogoes.marvelwiki.domain.RemoveSavedFavoriteUseCase
import com.joaogoes.marvelwiki.domain.SaveFavoriteUseCase
import com.joaogoes.marvelwiki.presentation.characters.CharactersViewState.State.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getCharacters: GetCharactersUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val removeSavedFavoriteUseCase: RemoveSavedFavoriteUseCase,
) : ViewModel() {
    val viewState: CharactersViewState = CharactersViewState()

    fun loadCharacters() {
        viewModelScope.launch {
            viewState.state.postValue(LOADING)
            getCharacters()
                .onSuccess { characterList ->
                    if (characterList.isEmpty()) {
                        viewState.characters.postValue(characterList)
                        viewState.state.postValue(EMPTY_STATE)
                    } else {
                        viewState.characters.postValue(characterList)
                        viewState.state.postValue(SUCCESS)
                    }
                }
                .onError { error ->
                    if (error is ServiceError.NoConnectionError)
                        viewState.state.postValue(NO_CONNECTION)
                    else
                        viewState.state.postValue(ERROR)
                }
        }
    }

    fun favoriteCharacter(character: CharacterModel) = viewModelScope.launch {
        if (character.isFavorite) {
            removeSavedFavoriteUseCase(character).onSuccess {
                handleFavoriteCharacterSuccess(character)
            }
        } else {
            saveFavorite(character).onSuccess {
                handleFavoriteCharacterSuccess(character)
            }
        }
    }

    private fun handleFavoriteCharacterSuccess(character: CharacterModel) {
        val newList = viewState.characters.value?.map {
            if (it == character) {
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }
        viewState.characters.postValue(newList)
    }
}