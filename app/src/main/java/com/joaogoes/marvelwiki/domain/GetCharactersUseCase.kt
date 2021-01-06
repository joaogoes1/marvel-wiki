package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.model.CharacterResponse
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import com.joaogoes.marvelwiki.data.repository.ServiceError
import javax.inject.Inject

interface GetCharactersUseCase {
    suspend operator fun invoke(): Result<CharacterResponse?, ServiceError>
}

class GetCharacters @Inject constructor(
    private val repository: CharacterRepository
): GetCharactersUseCase {
    override suspend fun invoke(): Result<CharacterResponse?, ServiceError> {
        return repository.getCharacters()
    }
}