package com.joaogoes.marvelwiki.characters.data.repository

import com.joaogoes.marvelwiki.characters.data.datasource.RemoteDataSource
import com.joaogoes.marvelwiki.characters.data.model.CharacterModel
import com.joaogoes.marvelwiki.characters.data.model.CharacterUrl
import com.joaogoes.marvelwiki.characters.data.model.ComicModel
import com.joaogoes.marvelwiki.characters.data.model.EventModel
import com.joaogoes.marvelwiki.characters.data.model.SeriesModel
import com.joaogoes.marvelwiki.characters.data.model.StoryModel
import com.joaogoes.marvelwiki.characters.data.response.CharacterApiResponse
import com.joaogoes.marvelwiki.characters.data.response.CharacterResponse
import com.joaogoes.marvelwiki.characters.data.response.ComicSummary
import com.joaogoes.marvelwiki.characters.data.response.EventSummary
import com.joaogoes.marvelwiki.characters.data.response.Image
import com.joaogoes.marvelwiki.characters.data.response.SeriesSummary
import com.joaogoes.marvelwiki.characters.data.response.StorySummary
import com.joaogoes.marvelwiki.characters.data.response.URL
import com.joaogoes.marvelwiki.favorites.data.model.FavoritesResultError
import com.joaogoes.marvelwiki.favorites.data.repository.FavoritesRepository
import com.joaogoes.marvelwiki.network.Result
import com.joaogoes.marvelwiki.network.ServiceError
import javax.inject.Inject


interface CharacterRepository {
    suspend fun getCharacter(characterId: Int): Result<CharacterModel, FavoritesResultError>
    suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError>
}

// TODO: Create a DataError, or something like this, because the don't need to kwon if error is about database or service
//  and sometimes the same function can catch error from both
class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val favoritesRepository: FavoritesRepository
) : CharacterRepository {

    override suspend fun getCharacter(characterId: Int): Result<CharacterModel, FavoritesResultError> =
        when (val result = remoteDataSource.getCharacter(characterId)) {
            is Result.Success -> {
                val item = result.value.data?.results?.get(0)
                if (result.value.code == 404 || item == null)
                    Result.Error(FavoritesResultError.NotFoundError)
                else {
                    val favoritesId =
                        favoritesRepository.getFavorites().handleResult()?.map { it.id }
                    val model = item.toModel()
                    if (favoritesId?.find { id -> id == model.id } != null)
                        Result.Success(model.copy(isFavorite = true))
                    else
                        Result.Success(model)
                }
            }
            is Result.Error -> result.mapError { FavoritesResultError.NotFoundError }
        }

    override suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError> =
        remoteDataSource.getCharacters().mapSuccess {
            val favoritesIds = favoritesRepository.getFavorites().handleResult()?.map { it.id }

            it.toCharacterModelList().map { character ->
                if (favoritesIds?.find { id -> id == character.id } != null) {
                    character.copy(isFavorite = true)
                } else character
            }
        }
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
