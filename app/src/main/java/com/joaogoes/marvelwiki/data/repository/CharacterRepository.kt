package com.joaogoes.marvelwiki.data.repository

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.entity.CharacterEntity
import com.joaogoes.marvelwiki.data.datasource.DatabaseError
import com.joaogoes.marvelwiki.data.datasource.LocalDataSource
import com.joaogoes.marvelwiki.data.datasource.RemoteDataSource
import com.joaogoes.marvelwiki.data.model.*
import com.joaogoes.marvelwiki.data.response.*
import javax.inject.Inject

interface CharacterRepository {
    suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError>
    suspend fun saveFavorite(character: CharacterModel): Result<Unit, DatabaseError>
    suspend fun removeSavedFavorite(character: CharacterModel): Result<Unit, DatabaseError>
    suspend fun getCharacter(characterId: Int): Result<CharacterModel, ServiceError>
}

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : CharacterRepository {

    // TODO: Compare with favorites
    // TODO: Handle error
    override suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError> =
        remoteDataSource.getCharacters().mapSuccess { it.toCharacterModelList() }

    override suspend fun saveFavorite(character: CharacterModel): Result<Unit, DatabaseError> {
        if (character.id == null || character.name == null)
            return Result.Error(DatabaseError.InvalidCharacter)

        val entity = CharacterEntity(
            id = character.id,
            name = character.name,
            imageUrl = character.imageUrl
        )
        return localDataSource.saveFavorite(entity)
    }

    override suspend fun removeSavedFavorite(character: CharacterModel): Result<Unit, DatabaseError> {
        if (character.id == null || character.name == null)
            return Result.Error(DatabaseError.InvalidCharacter)

        val entity = CharacterEntity(
            id = character.id,
            name = character.name,
            imageUrl = character.imageUrl
        )
        return localDataSource.saveFavorite(entity)
    }

    override suspend fun getCharacter(characterId: Int): Result<CharacterModel, ServiceError> =
        when (val result = remoteDataSource.getCharacter(characterId)) {
            is Result.Success -> {
                val item = result.value.data?.results?.get(0)
                if (result.value.code == 404 || item == null)
                    Result.Error(ServiceError.NotFoundError)
                else
                    Result.Success(item.toModel())
            }
            is Result.Error -> result
        }
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
    comics = comics.items?.map { it.toModel() } ?: emptyList(),
    stories = stories?.items?.map { it.toModel() } ?: emptyList(),
    events = events?.items?.map { it.toModel() } ?: emptyList(),
    seriesList = seriesList?.items?.map { it.toModel() } ?: emptyList(),
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
