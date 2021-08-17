package com.joaogoes.marvelwiki.characters.domain

import com.joaogoes.marvelwiki.characters.data.model.CharacterModel
import com.joaogoes.marvelwiki.characters.data.repository.CharacterRepository
import com.joaogoes.marvelwiki.network.Result
import com.joaogoes.marvelwiki.network.ServiceError
import javax.inject.Inject


interface GetCharactersUseCase {
    suspend operator fun invoke(): Result<List<CharacterModel>, ServiceError>
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : GetCharactersUseCase {
    override suspend fun invoke(): Result<List<CharacterModel>, ServiceError> =
        repository.getCharacters()
}