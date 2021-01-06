package com.joaogoes.marvelwiki.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.data.model.CharacterResponse
import com.joaogoes.marvelwiki.domain.GetCharactersUseCase
import com.joaogoes.marvelwiki.presentation.characters.CharactersViewState.State.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getCharacters: GetCharactersUseCase,
) : ViewModel() {
    val viewState: CharactersViewState = CharactersViewState()

    fun loadCharacters() {
        viewModelScope.launch {
            viewState.state.postValue(LOADING)
            getCharacters()
                .onSuccess { response ->
                    val charactersList = response?.toCharacterItemUiModel()
                    if (charactersList?.isEmpty() == true) {
                        viewState.characters.postValue(charactersList)
                        viewState.state.postValue(EMPTY_STATE)
                    } else {
                        viewState.characters.postValue(charactersList)
                        viewState.state.postValue(SUCCESS)
                    }
                }
                .onError { error ->
                    // TODO: Handle network error
                    viewState.state.postValue(ERROR)
                }
        }
    }

    private fun CharacterResponse.toCharacterItemUiModel(): List<CharactersItemUiModel> =
        data?.results?.map { character ->
            val imageUrlHttp =
                character.thumbnail?.path?.let { "$it.${character.thumbnail.extension}" }

            // This is needed because API 28 or higher disable cleartext traffic (http) by default
            val imageUrl = imageUrlHttp?.replace("http://", "https://")

            CharactersItemUiModel(
                name = character.name ?: "",
                imageUrl = imageUrl
            )
        } ?: emptyList()
}