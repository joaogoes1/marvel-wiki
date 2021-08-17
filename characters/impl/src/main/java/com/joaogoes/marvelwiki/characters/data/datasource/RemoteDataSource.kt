package com.joaogoes.marvelwiki.characters.data.datasource

import com.joaogoes.marvelwiki.characters.data.response.CharacterApiResponse
import com.joaogoes.marvelwiki.characters.data.service.CharacterApi
import com.joaogoes.marvelwiki.network.Result
import com.joaogoes.marvelwiki.network.ServiceError
import com.joaogoes.marvelwiki.network.safeCall
import javax.inject.Inject


interface RemoteDataSource {
    suspend fun getCharacters(): Result<CharacterApiResponse, ServiceError>
    suspend fun getCharacter(characterId: Int): Result<CharacterApiResponse, ServiceError>
}

class RemoteDataSourceImpl @Inject constructor(
    private val characterApi: CharacterApi
) : RemoteDataSource {

    override suspend fun getCharacters(): Result<CharacterApiResponse, ServiceError> =
        safeCall { characterApi.getCharacters() }

    override suspend fun getCharacter(characterId: Int): Result<CharacterApiResponse, ServiceError> =
        safeCall { characterApi.getCharacter(characterId) }
}
