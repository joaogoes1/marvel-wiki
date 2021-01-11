package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import javax.inject.Inject

interface RemoveFavoriteUseCase {
    suspend operator fun invoke(character: FavoriteModel): Result<Unit, DatabaseError>
}

class RemoveFavoriteUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : RemoveFavoriteUseCase {
    override suspend operator fun invoke(character: FavoriteModel): Result<Unit, DatabaseError> =
        characterRepository.removeSavedFavorite(character)
}