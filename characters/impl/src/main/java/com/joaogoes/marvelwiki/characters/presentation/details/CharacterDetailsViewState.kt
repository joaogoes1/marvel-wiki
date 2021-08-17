package com.joaogoes.marvelwiki.characters.presentation.details

import androidx.lifecycle.MutableLiveData
import com.joaogoes.marvelwiki.characters.data.model.CharacterModel


class CharacterDetailsViewState {
    val character = MutableLiveData<CharacterModel?>(null)
    val state = MutableLiveData(State.LOADING)

    enum class State { LOADING, SUCCESS, ERROR, NO_CONNECTION }
}
