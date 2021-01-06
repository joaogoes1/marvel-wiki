package com.joaogoes.marvelwiki.data.repository

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.api.CharacterApi
import com.joaogoes.marvelwiki.data.model.*
import com.joaogoes.marvelwiki.data.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CharacterRepository {
    suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError>
}

class CharacterRepositoryImpl @Inject constructor(
    private val characterApi: CharacterApi
) : CharacterRepository {
    override suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError> =
        withContext(Dispatchers.IO) {
            val result = characterApi.getCharactersAsync()
            Result.Success(result.toCharacterModelList())
        }
}

sealed class ServiceError {
    object NetworkError : ServiceError()
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

fun Image.toUrlString() = if (path == null || extension == null) null else "$path.$extension".replace("http://", "https://")

fun ComicSummary.toModel() = ComicModel(resourceURI, name)

fun StorySummary.toModel() = StoryModel(resourceURI, name, type)

fun EventSummary.toModel() = EventModel(resourceURI, name)

fun SeriesSummary.toModel() = SeriesModel(resourceURI, name)
