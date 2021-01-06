package com.joaogoes.marvelwiki.presentation.characters

import androidx.lifecycle.MutableLiveData
import com.joaogoes.marvelwiki.data.model.CharacterModel

class CharactersViewState {
    val characters: MutableLiveData<List<CharacterModel>> = MutableLiveData(emptyList())
    val state: MutableLiveData<State> = MutableLiveData(State.LOADING)

    enum class State { LOADING, ERROR, SUCCESS, EMPTY_STATE, NO_CONNECTION }
}