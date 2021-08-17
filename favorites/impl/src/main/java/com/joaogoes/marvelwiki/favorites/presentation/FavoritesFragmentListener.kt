package com.joaogoes.marvelwiki.favorites.presentation

import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel


interface FavoritesFragmentListener {
    fun openCharacter(characterId: Int)
    fun removeSavedFavorite(favorite: FavoriteModel)
}