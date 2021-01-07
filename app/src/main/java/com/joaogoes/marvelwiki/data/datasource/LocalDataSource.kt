package com.joaogoes.marvelwiki.data.datasource

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.dao.FavoriteDao
import com.joaogoes.marvelwiki.data.database.entity.FavoriteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LocalDataSource {
    suspend fun getFavorites(): Result<List<FavoriteEntity>, DatabaseError>
    suspend fun getFavoritesId(): Result<List<Int>, DatabaseError>
    suspend fun saveFavorite(character: FavoriteEntity): Result<Unit, DatabaseError>
    suspend fun removeSavedFavorite(character: FavoriteEntity): Result<Unit, DatabaseError>
}

class LocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : LocalDataSource {
    override suspend fun getFavorites(): Result<List<FavoriteEntity>, DatabaseError> =
        safeDatabaseCall { favoriteDao.getAllFavorites() }

    override suspend fun getFavoritesId(): Result<List<Int>, DatabaseError> =
        safeDatabaseCall { favoriteDao.getAllFavoritesId() }

    override suspend fun saveFavorite(character: FavoriteEntity): Result<Unit, DatabaseError> {
        val result = safeDatabaseCall { favoriteDao.insertFavorite(character) }

        return if (result is Result.Success && result.value == character.id.toLong())
            Result.Success(Unit)
        else
            Result.Error(DatabaseError.UnknownError)
    }

    override suspend fun removeSavedFavorite(character: FavoriteEntity): Result<Unit, DatabaseError> {
        val result = safeDatabaseCall { favoriteDao.deleteFavorite(character) }

        return if (result is Result.Success && result.value > 0)
            Result.Success(Unit)
        else
            Result.Error(DatabaseError.UnknownError)
    }
}

sealed class DatabaseError {
    object InvalidCharacter : DatabaseError()
    object UnknownError : DatabaseError()
}

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