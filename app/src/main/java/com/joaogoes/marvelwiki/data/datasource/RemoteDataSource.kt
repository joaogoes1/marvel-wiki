package com.joaogoes.marvelwiki.data.datasource

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.service.CharacterApi
import com.joaogoes.marvelwiki.data.service.NoConnectionException
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.data.response.CharacterApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

private suspend fun <T> safeCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T,
): Result<T, ServiceError> =
    withContext(dispatcher) {
        try {
            Result.Success(block())
        } catch (e: NoConnectionException){
            Result.Error(ServiceError.NoConnectionError)
        } catch (e: Exception) {
            Result.Error(ServiceError.NetworkError)
        }
    }