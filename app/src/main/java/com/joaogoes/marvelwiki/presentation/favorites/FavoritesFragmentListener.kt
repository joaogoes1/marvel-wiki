package com.joaogoes.marvelwiki.presentation.favorites

import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.model.FavoriteModel

interface FavoritesFragmentListener {
    fun openCharacter(characterId: Int)
    fun removeSavedFavorite(favorite: FavoriteModel)
}