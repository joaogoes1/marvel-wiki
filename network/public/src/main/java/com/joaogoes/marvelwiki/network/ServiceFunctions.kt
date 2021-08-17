package com.joaogoes.marvelwiki.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun <T> safeCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T,
): Result<T, ServiceError> =
    withContext(dispatcher) {
        try {
            Result.Success(block())
        } catch (e: NoConnectionException) {
            Result.Error(ServiceError.NoConnectionError)
        } catch (e: Exception) {
            Result.Error(ServiceError.NetworkError)
        }
    }