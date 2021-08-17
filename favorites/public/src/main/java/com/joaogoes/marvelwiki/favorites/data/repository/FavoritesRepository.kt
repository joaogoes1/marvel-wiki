package com.joaogoes.marvelwiki.favorites.data.repository

import com.joaogoes.marvelwiki.characters.data.model.CharacterModel
import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.network.Result


interface FavoritesRepository {
    suspend fun getFavorites(): Result<List<FavoriteModel>, FavoritesResultError>
    suspend fun saveFavorite(
        id: Int,
        name: String,
        imageUrl: String
    ): Result<Unit, FavoritesResultError>

    suspend fun removeSavedFavorite(character: CharacterModel): Result<Unit, FavoritesResultError>
    suspend fun removeSavedFavorite(favoriteModel: FavoriteModel): Result<Unit, FavoritesResultError>
}