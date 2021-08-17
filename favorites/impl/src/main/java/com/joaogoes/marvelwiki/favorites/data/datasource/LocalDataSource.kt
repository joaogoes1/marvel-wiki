package com.joaogoes.marvelwiki.favorites.data.datasource

import com.joaogoes.marvelwiki.database.DatabaseError
import com.joaogoes.marvelwiki.database.safeDatabaseCall
import com.joaogoes.marvelwiki.favorites.data.database.dao.FavoriteDao
import com.joaogoes.marvelwiki.favorites.data.database.entity.FavoriteEntity
import com.joaogoes.marvelwiki.network.Result
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