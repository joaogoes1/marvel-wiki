package com.joaogoes.marvelwiki.favorites.domain

import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.network.Result

interface RemoveFavoriteUseCase {
    suspend operator fun invoke(character: FavoriteModel): Result<Unit, FavoritesResultError>
}
