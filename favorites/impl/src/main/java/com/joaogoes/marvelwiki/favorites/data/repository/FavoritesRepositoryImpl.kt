package com.joaogoes.marvelwiki.favorites.data.repository

import com.joaogoes.marvelwiki.characters.data.model.CharacterModel
import com.joaogoes.marvelwiki.favorites.data.database.entity.FavoriteEntity
import com.joaogoes.marvelwiki.favorites.data.datasource.LocalDataSource
import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.network.Result
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : FavoritesRepository {

    override suspend fun getFavorites(): Result<List<FavoriteModel>, FavoritesResultError> =
        localDataSource
            .getFavorites()
            .mapSuccess { list ->
                list.map { it.toModel() }
            }.mapError {
                FavoritesResultError.NotFoundError
            }

    override suspend fun saveFavorite(
        id: Int,
        name: String,
        imageUrl: String
    ): Result<Unit, FavoritesResultError> {
        val entity = FavoriteEntity(
            id = id,
            name = name,
            imageUrl = imageUrl
        )
        return localDataSource.saveFavorite(entity).mapError { FavoritesResultError.NotFoundError }
    }

    override suspend fun removeSavedFavorite(character: CharacterModel): Result<Unit, FavoritesResultError> {
        if (character.id == null || character.name == null)
            return Result.Error(FavoritesResultError.NotFoundError)

        val entity = FavoriteEntity(
            id = character.id ?: 0,
            name = character.name ?: "",
            imageUrl = character.imageUrl
        )
        return localDataSource.removeSavedFavorite(entity).mapError {
            FavoritesResultError.NotFoundError
        }
    }

    override suspend fun removeSavedFavorite(favoriteModel: FavoriteModel): Result<Unit, FavoritesResultError> =
        localDataSource.removeSavedFavorite(favoriteModel.toEntity()).mapError {
            FavoritesResultError.NotFoundError
        }
}


fun FavoriteEntity.toModel() = FavoriteModel(id, name, imageUrl)

fun FavoriteModel.toEntity() = FavoriteEntity(id, name, imageUrl)