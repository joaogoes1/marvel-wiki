package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.response.CharacterApiResponse
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import com.joaogoes.marvelwiki.data.repository.ServiceError
import javax.inject.Inject

interface GetCharactersUseCase {
    suspend operator fun invoke(): Result<List<CharacterModel>, ServiceError>
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
): GetCharactersUseCase {
    override suspend fun invoke(): Result<List<CharacterModel>, ServiceError> =
        repository.getCharacters()
}