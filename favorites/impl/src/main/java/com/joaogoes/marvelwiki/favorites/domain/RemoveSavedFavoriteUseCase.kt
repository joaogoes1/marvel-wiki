package com.joaogoes.marvelwiki.favorites.domain

import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.favorites.data.repository.FavoritesRepository
import com.joaogoes.marvelwiki.network.Result
import javax.inject.Inject


class RemoveSavedFavoriteUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : RemoveSavedFavoriteUseCase {

    override suspend fun invoke(favorite: FavoriteModel): Result<Unit, FavoritesResultError> =
        favoritesRepository.removeSavedFavorite(favorite)
}