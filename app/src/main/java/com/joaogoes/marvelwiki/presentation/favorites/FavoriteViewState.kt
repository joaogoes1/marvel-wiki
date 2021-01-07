package com.joaogoes.marvelwiki.presentation.favorites

import androidx.lifecycle.MutableLiveData
import com.joaogoes.marvelwiki.data.model.FavoriteModel

class FavoriteViewState {
    val favorites = MutableLiveData<List<FavoriteModel>>(emptyList())
    val state = MutableLiveData<State>(State.LOADING)

    enum class State { LOADING, ERROR, EMPTY_STATE, SUCCESS }
}