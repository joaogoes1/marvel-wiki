package com.joaogoes.marvelwiki.characters.presentation.characterslist

import androidx.lifecycle.MutableLiveData
import com.joaogoes.marvelwiki.characters.data.model.CharacterModel


class CharactersViewState {
    val characters: MutableLiveData<List<CharacterModel>> = MutableLiveData(emptyList())
    val state: MutableLiveData<State> = MutableLiveData(State.LOADING)
    val viewType: MutableLiveData<CharactersAdapter.ViewType> = MutableLiveData(CharactersAdapter.ViewType.GRID)

    enum class State { LOADING, ERROR, SUCCESS, EMPTY_STATE, NO_CONNECTION }
}