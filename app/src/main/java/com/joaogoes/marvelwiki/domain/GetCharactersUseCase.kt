package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.model.CharacterResponse
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import javax.inject.Inject

interface GetCharactersUseCase {
    suspend operator fun invoke(): Result<CharacterResponse?, Nothing>
}

class GetCharacters @Inject constructor(
    private val repository: CharacterRepository
): GetCharactersUseCase {
    override suspend fun invoke(): Result<CharacterResponse?, Nothing> {
        return repository.getCharacters()
    }
}