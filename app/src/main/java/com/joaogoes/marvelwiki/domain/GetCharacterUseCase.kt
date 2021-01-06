package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import com.joaogoes.marvelwiki.data.repository.ServiceError
import javax.inject.Inject

interface GetCharacterUseCase {
    suspend operator fun invoke(characterId: Int): Result<CharacterModel, ServiceError>
}

class GetCharacterUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterUseCase {

    override suspend fun invoke(characterId: Int): Result<CharacterModel, ServiceError> =
        characterRepository.getCharacter(characterId)
}
