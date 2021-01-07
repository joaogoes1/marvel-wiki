package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.datasource.DatabaseError
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import javax.inject.Inject

interface GetFavoritesUseCase {
    suspend operator fun invoke(): Result<List<FavoriteModel>, DatabaseError>
}

class GetFavoritesUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
): GetFavoritesUseCase {
    override suspend fun invoke(): Result<List<FavoriteModel>, DatabaseError> =
        characterRepository.getFavorites()
}