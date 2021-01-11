package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import javax.inject.Inject

interface RemoveSavedFavoriteUseCase {
    suspend operator fun invoke(character: CharacterModel): Result<Unit, DatabaseError>
}

class RemoveSavedFavoriteUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : RemoveSavedFavoriteUseCase {

    override suspend fun invoke(character: CharacterModel): Result<Unit, DatabaseError> =
        characterRepository.removeSavedFavorite(character)
}