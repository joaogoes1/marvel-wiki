package com.joaogoes.marvelwiki.characters.domain

import com.joaogoes.marvelwiki.characters.data.model.CharacterModel
import com.joaogoes.marvelwiki.characters.data.repository.CharacterRepository
import com.joaogoes.marvelwiki.network.ServiceError
import javax.inject.Inject

interface GetCharacterUseCase {
    suspend operator fun invoke(characterId: Int): com.joaogoes.marvelwiki.network.Result<CharacterModel, ServiceError>
}

class GetCharacterUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterUseCase {

    override suspend fun invoke(characterId: Int): com.joaogoes.marvelwiki.network.Result<CharacterModel, ServiceError> =
        characterRepository.getCharacter(characterId).mapError { ServiceError.UnknownError }
}
