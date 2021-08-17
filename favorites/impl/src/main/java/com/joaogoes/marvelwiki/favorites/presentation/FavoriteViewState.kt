package com.joaogoes.marvelwiki.favorites.presentation

import androidx.lifecycle.MutableLiveData


class FavoriteViewState {
    val favorites =
        MutableLiveData<List<com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel>>(emptyList())
    val state = MutableLiveData<State>(State.LOADING)

    enum class State { LOADING, ERROR, EMPTY_STATE, SUCCESS }
}
