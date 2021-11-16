package com.joaogoes.marvelwiki.characters.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
    private val _state =
        MutableLiveData<CharacterDetailsViewState>(CharacterDetailsViewState.Loading)
    val state = Transformations.map(_state) { it }

    fun loadCharacter(characterId: Int) = viewModelScope.launch {
        _state.postValue(CharacterDetailsViewState.Loading)
        getCharacter(characterId)
            .onSuccess { character ->
                _state.postValue(CharacterDetailsViewState.Success(character))
            }
            .onError { error ->
                if (error is ServiceError.NoConnectionError)
                    _state.postValue(CharacterDetailsViewState.NoConnection)
                else
                    _state.postValue(CharacterDetailsViewState.Error)
            }
    }

    // TODO: Handle error
    fun favoriteCharacter() {
        val state = state.value
        viewModelScope.launch {
            if (state is CharacterDetailsViewState.Success) {
                val character = state.characterModel
                val id = character.id ?: return@launch
                val name = character.name ?: return@launch
                val imageUrl = character.imageUrl ?: return@launch
                saveFavorite(id, name, imageUrl).onSuccess {
                    handleFavoriteCharacterSuccess(character)
                }
            }
        }
    }

    // TODO: Handle error
    fun removeFavoriteCharacter() {
        val state = state.value
        viewModelScope.launch {
            if (state is CharacterDetailsViewState.Success) {
                val character = state.characterModel
                val favorite = FavoriteModel(
                    id = character.id ?: return@launch,
                    name = character.name ?: return@launch,
                    imageUrl = character.imageUrl ?: return@launch
                )
                removeSavedFavorite(favorite).onSuccess {
                    handleFavoriteCharacterSuccess(character)
                }
            }
        }
    }

    private fun handleFavoriteCharacterSuccess(character: CharacterModel) {
        _state.postValue(CharacterDetailsViewState.Success(character.copy(isFavorite = !character.isFavorite)))
    }
}