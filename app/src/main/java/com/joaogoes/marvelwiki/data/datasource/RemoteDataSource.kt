package com.joaogoes.marvelwiki.data.datasource

import android.util.Log
import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.api.CharacterApi
import com.joaogoes.marvelwiki.data.api.NoConnectionException
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.data.response.CharacterApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getCharacters(): Result<CharacterApiResponse, ServiceError>
}

class RemoteDataSourceImpl @Inject constructor(
    private val characterApi: CharacterApi
) : RemoteDataSource {

    override suspend fun getCharacters(): Result<CharacterApiResponse, ServiceError> =
        safeCall { characterApi.getCharactersAsync() }

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