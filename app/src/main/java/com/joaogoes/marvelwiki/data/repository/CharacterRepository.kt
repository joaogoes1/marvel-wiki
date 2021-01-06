package com.joaogoes.marvelwiki.data.repository

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.api.CharacterApi
import com.joaogoes.marvelwiki.data.model.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CharacterRepository {
    suspend fun getCharacters(): Result<CharacterResponse?, ServiceError>
}

class CharacterRepositoryImpl @Inject constructor(
    private val characterApi: CharacterApi
) : CharacterRepository {
    override suspend fun getCharacters(): Result<CharacterResponse?, ServiceError> =
        withContext(Dispatchers.IO) {
            val result = characterApi.getCharactersAsync()
            Result.Success(result)
        }
}

sealed class ServiceError {
    object NetworkError : ServiceError()
    object UnknownError: ServiceError()
}