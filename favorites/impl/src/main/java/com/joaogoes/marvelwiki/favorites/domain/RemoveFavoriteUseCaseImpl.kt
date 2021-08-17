package com.joaogoes.marvelwiki.favorites.domain

import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.favorites.data.repository.FavoritesRepository
import com.joaogoes.marvelwiki.network.Result
import javax.inject.Inject


class RemoveFavoriteUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : RemoveFavoriteUseCase {
    override suspend operator fun invoke(character: FavoriteModel): Result<Unit, FavoritesResultError> =
        favoritesRepository.removeSavedFavorite(character)
}