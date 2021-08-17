package com.joaogoes.marvelwiki.favorites.domain

import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.favorites.data.repository.FavoritesRepository
import com.joaogoes.marvelwiki.network.Result
import javax.inject.Inject


class SaveFavoriteUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : SaveFavoriteUseCase {

    override suspend fun invoke(
        id: Int,
        name: String,
        imageUrl: String
    ): Result<Unit, FavoritesResultError> =
        favoritesRepository.saveFavorite(id, name, imageUrl)
}