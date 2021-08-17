package com.joaogoes.marvelwiki.characters.presentation.characterslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.characters.data.model.CharacterModel
import com.joaogoes.marvelwiki.characters.domain.GetCharactersUseCase
import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.favorites.domain.RemoveSavedFavoriteUseCase
import com.joaogoes.marvelwiki.favorites.domain.SaveFavoriteUseCase
import com.joaogoes.marvelwiki.network.ServiceError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharacters: GetCharactersUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val removeSavedFavorite: RemoveSavedFavoriteUseCase,
) : ViewModel() {
    val viewState: CharactersViewState = CharactersViewState()

    fun loadCharacters() {
        viewModelScope.launch {
            getCharacters()
                .onSuccess { characterList ->
                    if (characterList.isEmpty()) {
                        viewState.characters.postValue(characterList)
                        viewState.state.postValue(CharactersViewState.State.EMPTY_STATE)
                    } else {
                        viewState.characters.postValue(characterList)
                        viewState.state.postValue(CharactersViewState.State.SUCCESS)
                    }
                }
                .onError { error ->
                    if (error is ServiceError.NoConnectionError)
                        viewState.state.postValue(CharactersViewState.State.NO_CONNECTION)
                    else
                        viewState.state.postValue(CharactersViewState.State.ERROR)
                }
        }
    }

    fun favoriteCharacter(character: CharacterModel) = viewModelScope.launch {
        if (character.isFavorite) {
            removeSavedFavorite(
                FavoriteModel(
                    character.id ?: 0,
                    character.name ?: "",
                    character.imageUrl
                )
            ).onSuccess {
                handleFavoriteCharacterSuccess(character)
            }
        } else {
            saveFavorite(
                character.id ?: 0,
                character.name ?: "",
                character.imageUrl ?: ""
            ).onSuccess {
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