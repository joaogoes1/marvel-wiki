package com.joaogoes.marvelwiki.presentation.characters

import androidx.lifecycle.MutableLiveData

class CharactersViewState {
    val characters: MutableLiveData<List<CharactersItemUiModel>> = MutableLiveData(emptyList())
    val state: MutableLiveData<State> = MutableLiveData(State.LOADING)

    enum class State { LOADING, ERROR, SUCCESS, EMPTY_STATE }
}