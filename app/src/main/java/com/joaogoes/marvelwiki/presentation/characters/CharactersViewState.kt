package com.joaogoes.marvelwiki.presentation.characters

import androidx.lifecycle.MutableLiveData
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.presentation.characters.CharactersAdapter.ViewType.GRID

class CharactersViewState {
    val characters: MutableLiveData<List<CharacterModel>> = MutableLiveData(emptyList())
    val state: MutableLiveData<State> = MutableLiveData(State.LOADING)
    val viewType: MutableLiveData<CharactersAdapter.ViewType> = MutableLiveData(GRID)

    enum class State { LOADING, ERROR, SUCCESS, EMPTY_STATE, NO_CONNECTION }
}