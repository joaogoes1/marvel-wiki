package com.joaogoes.marvelwiki.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.data.model.CharacterResponse
import com.joaogoes.marvelwiki.domain.GetCharactersUseCase
import com.joaogoes.marvelwiki.presentation.characters.CharactersViewState.State.LOADING
import com.joaogoes.marvelwiki.presentation.characters.CharactersViewState.State.SUCCESS
import com.joaogoes.marvelwiki.presentation.characters.CharactersViewState.State.ERROR
import com.joaogoes.marvelwiki.presentation.characters.CharactersViewState.State.EMPTY_STATE
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getCharacters: GetCharactersUseCase
) : ViewModel() {
    val viewState = CharactersViewState()

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
                    // TODO: Handle error
                    viewState.state.postValue(ERROR)
                }
        }
    }

    private fun CharacterResponse.toCharacterItemUiModel(): List<CharactersItemUiModel> =
        data?.results?.map { character ->
            CharactersItemUiModel(
                name = character.name ?: "",
                imageUrl = character.thumbnail?.path?.let { it + character.thumbnail.extension }
                    ?: ""
            )
        } ?: emptyList()
}