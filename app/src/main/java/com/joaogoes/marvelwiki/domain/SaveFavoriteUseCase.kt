package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.datasource.DatabaseError
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import javax.inject.Inject

interface SaveFavoriteUseCase {
    suspend operator fun invoke(characterModel: CharacterModel): Result<Unit, DatabaseError>
}

class SaveFavoriteUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : SaveFavoriteUseCase {

    override suspend fun invoke(characterModel: CharacterModel): Result<Unit, DatabaseError> =
        characterRepository.saveFavorite(characterModel)
}