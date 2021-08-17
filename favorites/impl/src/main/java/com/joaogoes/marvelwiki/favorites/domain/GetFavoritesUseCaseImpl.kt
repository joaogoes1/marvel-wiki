package com.joaogoes.marvelwiki.favorites.domain

import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.favorites.data.repository.FavoritesRepository
import com.joaogoes.marvelwiki.network.Result
import javax.inject.Inject


class GetFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoritesUseCase {
    override suspend fun invoke(): Result<List<FavoriteModel>, FavoritesResultError> =
        favoritesRepository.getFavorites()
}