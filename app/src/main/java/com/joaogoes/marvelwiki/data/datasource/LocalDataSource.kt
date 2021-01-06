package com.joaogoes.marvelwiki.data.datasource

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.dao.FavoriteDao
import com.joaogoes.marvelwiki.data.database.entity.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LocalDataSource {
    suspend fun saveFavorite(character: CharacterEntity): Result<Unit, DatabaseError>
    suspend fun removeSavedFavorite(character: CharacterEntity): Result<Unit, DatabaseError>
}

class LocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : LocalDataSource {
    override suspend fun saveFavorite(character: CharacterEntity): Result<Unit, DatabaseError> =
        withContext(Dispatchers.IO) {
            val result = favoriteDao.insertFavorite(character)

            if (result == character.id.toLong())
                Result.Success(Unit)
            else
                Result.Error(DatabaseError.UnknownError)
        }

    override suspend fun removeSavedFavorite(character: CharacterEntity): Result<Unit, DatabaseError> =
        withContext(Dispatchers.IO) {
            val result = favoriteDao.deleteFavorite(character)

            if (result == character.id)
                Result.Success(Unit)
            else
                Result.Error(DatabaseError.UnknownError)
        }
}

sealed class DatabaseError {
    object InvalidCharacter : DatabaseError()
    object UnknownError : DatabaseError()
}
