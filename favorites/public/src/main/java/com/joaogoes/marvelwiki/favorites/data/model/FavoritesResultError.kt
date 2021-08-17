package com.joaogoes.marvelwiki.favorites.data.model

sealed class FavoritesResultError {
    object NotFoundError : FavoritesResultError()
}
