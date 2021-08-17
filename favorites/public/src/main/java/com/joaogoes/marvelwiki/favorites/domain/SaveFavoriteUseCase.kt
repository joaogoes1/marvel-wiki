package com.joaogoes.marvelwiki.favorites.domain

import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.network.Result


interface SaveFavoriteUseCase {
    suspend operator fun invoke(id: Int, name: String, imageUrl: String): Result<Unit, FavoritesResultError>
}
