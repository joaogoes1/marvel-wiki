package com.joaogoes.marvelwiki.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    // TODO: Handle network error
                    viewState.state.postValue(ERROR)
                }
        }
    }
}