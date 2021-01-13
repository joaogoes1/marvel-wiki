package com.joaogoes.marvelwiki.data.repository

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.database.entity.FavoriteEntity
import com.joaogoes.marvelwiki.data.datasource.LocalDataSource
import com.joaogoes.marvelwiki.data.datasource.RemoteDataSource
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.model.CharacterUrl
import com.joaogoes.marvelwiki.data.model.ComicModel
import com.joaogoes.marvelwiki.data.model.EventModel
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.data.model.SeriesModel
import com.joaogoes.marvelwiki.data.model.StoryModel
import com.joaogoes.marvelwiki.data.response.CharacterApiResponse
import com.joaogoes.marvelwiki.data.response.CharacterResponse
import com.joaogoes.marvelwiki.data.response.ComicSummary
import com.joaogoes.marvelwiki.data.response.EventSummary
import com.joaogoes.marvelwiki.data.response.Image
import com.joaogoes.marvelwiki.data.response.SeriesSummary
import com.joaogoes.marvelwiki.data.response.StorySummary
import com.joaogoes.marvelwiki.data.response.URL
import javax.inject.Inject

interface CharacterRepository {
    suspend fun getCharacter(characterId: Int): Result<CharacterModel, ServiceError>
    suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError>
    suspend fun getFavorites(): Result<List<FavoriteModel>, DatabaseError>
    suspend fun saveFavorite(character: CharacterModel): Result<Unit, DatabaseError>
    suspend fun removeSavedFavorite(character: CharacterModel): Result<Unit, DatabaseError>
    suspend fun removeSavedFavorite(favoriteModel: FavoriteModel): Result<Unit, DatabaseError>
}

// TODO: Create a DataError, or something like this, because the don't need to kwon if error is about database or service
//  and sometimes the same function can catch error from both
class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : CharacterRepository {

    override suspend fun getCharacter(characterId: Int): Result<CharacterModel, ServiceError> =
        when (val result = remoteDataSource.getCharacter(characterId)) {
            is Result.Success -> {
                val item = result.value.data?.results?.get(0)
                if (result.value.code == 404 || item == null)
                    Result.Error(ServiceError.NotFoundError)
                else {
                    val favoritesId = localDataSource.getFavoritesId().handleResult()
                    val model = item.toModel()
                    if (favoritesId?.find { id -> id == model.id } != null)
                        Result.Success(model.copy(isFavorite = true))
                    else
                        Result.Success(model)
                }
            }
            is Result.Error -> result
        }

    override suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError> =
        remoteDataSource.getCharacters().mapSuccess {
            val favoritesIds = localDataSource.getFavoritesId().handleResult()

            it.toCharacterModelList().map { character ->
                if (favoritesIds?.find { id -> id == character.id } != null) {
                    character.copy(isFavorite = true)
                } else character
            }
        }

    override suspend fun getFavorites(): Result<List<FavoriteModel>, DatabaseError> =
        localDataSource.getFavorites().mapSuccess { list ->
            list.map { it.toModel() }
        }

    override suspend fun saveFavorite(character: CharacterModel): Result<Unit, DatabaseError> {
        if (character.id == null || character.name == null)
            return Result.Error(DatabaseError.InvalidCharacter)

        val entity = FavoriteEntity(
            id = character.id,
            name = character.name,
            imageUrl = character.imageUrl
        )
        return localDataSource.saveFavorite(entity)
    }

    override suspend fun removeSavedFavorite(character: CharacterModel): Result<Unit, DatabaseError> {
        if (character.id == null || character.name == null)
            return Result.Error(DatabaseError.InvalidCharacter)

        val entity = FavoriteEntity(
            id = character.id,
            name = character.name,
            imageUrl = character.imageUrl
        )
        return localDataSource.removeSavedFavorite(entity)
    }

    override suspend fun removeSavedFavorite(favoriteModel: FavoriteModel): Result<Unit, DatabaseError> =
        localDataSource.removeSavedFavorite(favoriteModel.toEntity())
}

sealed class ServiceError {
    object NetworkError : ServiceError()
    object NoConnectionError : ServiceError()
    object NotFoundError : ServiceError()
    object UnknownError : ServiceError()
}

fun CharacterApiResponse.toCharacterModelList() = data?.results?.map { it.toModel() } ?: emptyList()

fun CharacterResponse.toModel() = CharacterModel(
    id = id,
    name = name,
    description = description,
    resourceURI = resourceURI,
    urls = urls?.map { it.toCharacterUrl() } ?: emptyList(),
    imageUrl = thumbnail?.toUrlString(),
    comics = comics?.items?.map { it.toModel() } ?: emptyList(),
    stories = stories?.items?.map { it.toModel() } ?: emptyList(),
    events = events?.items?.map { it.toModel() } ?: emptyList(),
    series = series?.items?.map { it.toModel() } ?: emptyList(),
)

fun URL.toCharacterUrl() = CharacterUrl(type, url)

fun Image.toUrlString() =
    if (path == null || extension == null) null else "$path.$extension".replace(
        "http://",
        "https://"
    )

fun ComicSummary.toModel() = ComicModel(resourceURI, name)

fun StorySummary.toModel() = StoryModel(resourceURI, name, type)

fun EventSummary.toModel() = EventModel(resourceURI, name)

fun SeriesSummary.toModel() = SeriesModel(resourceURI, name)

fun FavoriteEntity.toModel() = FavoriteModel(id, name, imageUrl)

fun FavoriteModel.toEntity() = FavoriteEntity(id, name, imageUrl)
