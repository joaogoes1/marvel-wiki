package com.joaogoes.marvelwiki.database

import com.joaogoes.marvelwiki.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeDatabaseCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T,
): Result<T, DatabaseError> = withContext(dispatcher) {
    return@withContext try {
        Result.Success(block())
    } catch (t: Throwable) {
        Result.Error(DatabaseError.UnknownError)
    }
}