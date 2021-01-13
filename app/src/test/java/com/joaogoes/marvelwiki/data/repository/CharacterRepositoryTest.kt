package com.joaogoes.marvelwiki.data.repository

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.database.entity.FavoriteEntity
import com.joaogoes.marvelwiki.data.datasource.LocalDataSource
import com.joaogoes.marvelwiki.data.datasource.RemoteDataSource
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.data.response.CharacterApiResponse
import com.joaogoes.marvelwiki.utils.CharacterApiResponseFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest {

    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()
    private val repository = CharacterRepositoryImpl(remoteDataSource, localDataSource)

    @Test
    fun `getCharacter, return success and valid, return character`() = runBlockingTest {
        val expected = makeValidCharacterModel()
        coEvery { remoteDataSource.getCharacter(1) } returns Result.Success(
            makeValidCharacterResponseList()
        )
        coEvery { localDataSource.getFavoritesId() } returns Result.Success(emptyList())

        val result = repository.getCharacter(1)

        when (result) {
            is Result.Success -> {
                assertEquals(expected, result.value)
            }
            is Result.Error -> fail("Result should be Success")
        }
    }

    @Test
    fun `getCharacter, return success and valid, return character with favorite`() =
        runBlockingTest {
            val expected = makeValidCharacterModel(hasAFavorite = true)
            coEvery { remoteDataSource.getCharacter(1) } returns Result.Success(
                makeValidCharacterResponseList()
            )
            coEvery { localDataSource.getFavoritesId() } returns Result.Success(listOf(1, 2, 3))

            val result = repository.getCharacter(1)

            when (result) {
                is Result.Success -> {
                    assertEquals(expected, result.value)
                    assertTrue(result.value.isFavorite)
                }
                is Result.Error -> fail("Result should be Success")
            }
        }

    @Test
    fun `getCharacter, return success but element is null, return NotFoundError`() =
        runBlockingTest {
            coEvery { remoteDataSource.getCharacter(any()) } returns Result.Success(
                CharacterApiResponseFactory.make(
                    data = CharacterApiResponseFactory.makeCharacterDataContainer(
                        null
                    )
                )
            )
            coEvery { localDataSource.getFavoritesId() } returns Result.Success(emptyList())

            val result = repository.getCharacter(1)

            when (result) {
                is Result.Success -> fail("Result should be Error")
                is Result.Error -> assertEquals(ServiceError.NotFoundError, result.value)
            }
        }

    @Test
    fun `getCharacter, return success but 404, return NotFoundError`() = runBlockingTest {
        coEvery { remoteDataSource.getCharacter(any()) } returns Result.Success(
            makeValidCharacterResponseList(404)
        )
        coEvery { localDataSource.getFavoritesId() } returns Result.Success(emptyList())

        val result = repository.getCharacter(1)

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(ServiceError.NotFoundError, result.value)
        }
    }

    @Test
    fun `getCharacter, return NetworkError, return NetworkError`() = runBlockingTest {
        coEvery { remoteDataSource.getCharacter(any()) } returns Result.Error(ServiceError.NetworkError)
        coEvery { localDataSource.getFavoritesId() } returns Result.Success(emptyList())

        val result = repository.getCharacter(1)

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(ServiceError.NetworkError, result.value)
        }
    }

    @Test
    fun `getCharacter, return UnknownError, return UnknownError`() = runBlockingTest {
        coEvery { remoteDataSource.getCharacter(any()) } returns Result.Error(ServiceError.UnknownError)
        coEvery { localDataSource.getFavoritesId() } returns Result.Success(emptyList())

        val result = repository.getCharacter(1)

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(ServiceError.UnknownError, result.value)
        }
    }

    @Test
    fun `getCharacters, return success and valid, return list`() = runBlockingTest {
        val expected = makeValidCharacterModelList()
        coEvery { remoteDataSource.getCharacters() } returns Result.Success(
            makeValidCharacterResponseList()
        )
        coEvery { localDataSource.getFavoritesId() } returns Result.Success(emptyList())

        val result = repository.getCharacters()

        when (result) {
            is Result.Success -> {
                assertEquals(expected, result.value)
            }
            is Result.Error -> fail("Result should be Success")
        }
    }

    @Test
    fun `getCharacters, return success and valid, return list with favorite`() = runBlockingTest {
        val expected = makeValidCharacterModelList(true)
        coEvery { remoteDataSource.getCharacters() } returns Result.Success(
            makeValidCharacterResponseList()
        )
        coEvery { localDataSource.getFavoritesId() } returns Result.Success(listOf(2))

        val result = repository.getCharacters()

        when (result) {
            is Result.Success -> {
                val characters = result.value

                assertEquals(expected, characters)
                assertFalse(characters[0].isFavorite)
                assertTrue(characters[1].isFavorite)
                assertFalse(characters[2].isFavorite)
            }
            is Result.Error -> fail("Result should be Success")
        }
    }

    @Test
    fun `getCharacters, return NetworkError, return NetworkError`() = runBlockingTest {
        coEvery { remoteDataSource.getCharacters() } returns Result.Error(ServiceError.NetworkError)
        coEvery { localDataSource.getFavoritesId() } returns Result.Success(emptyList())

        val result = repository.getCharacters()

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(ServiceError.NetworkError, result.value)
        }
    }

    @Test
    fun `getCharacters, return NoConnectionError, return NoConnectionError`() = runBlockingTest {
        coEvery { remoteDataSource.getCharacters() } returns Result.Error(ServiceError.NoConnectionError)
        coEvery { localDataSource.getFavoritesId() } returns Result.Success(emptyList())

        val result = repository.getCharacters()

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(ServiceError.NoConnectionError, result.value)
        }
    }

    @Test
    fun `getFavorites, return Success, return favorites list`() = runBlockingTest {
        val expected = listOf(
            FavoriteModel(1, "Batman", null),
            FavoriteModel(2, "Batman", null),
            FavoriteModel(3, "Batman", null),
        )
        val entities = listOf(
            FavoriteEntity(1, "Batman", null),
            FavoriteEntity(2, "Batman", null),
            FavoriteEntity(3, "Batman", null),
        )
        coEvery { localDataSource.getFavorites() } returns Result.Success(entities)

        val result = repository.getFavorites()

        when (result) {
            is Result.Success -> assertEquals(expected, result.value)
            is Result.Error -> fail("Result should be Success")
        }
    }

    @Test
    fun `getFavorites, return UnknownError, return UnknownError`() = runBlockingTest {
        coEvery { localDataSource.getFavorites() } returns Result.Error(DatabaseError.UnknownError)

        val result = repository.getFavorites()

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(DatabaseError.UnknownError, result.value)
        }
    }

    @Test
    fun `saveFavorite, success, return success Unit`() = runBlockingTest {
        val characterModel = makeValidCharacterModel()
        val entity = FavoriteEntity(
            id = characterModel.id ?: -1,
            name = characterModel.name ?: "",
            imageUrl = characterModel.imageUrl
        )
        coEvery { localDataSource.saveFavorite(entity) } returns Result.Success(Unit)

        val result = repository.saveFavorite(characterModel)

        when (result) {
            is Result.Success -> assertEquals(Unit, result.value)
            is Result.Error -> fail("Result should be Success")
        }
    }

    @Test
    fun `saveFavorite, characterId null, return InvalidCharacterError`() = runBlockingTest {
        val character = makeValidCharacterModel(id = null)

        val result = repository.saveFavorite(character)

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(DatabaseError.InvalidCharacter, result.value)
        }
    }

    @Test
    fun `saveFavorite, character name null, return InvalidCharacterError`() = runBlockingTest {
        val character = makeValidCharacterModel(name = null)

        val result = repository.saveFavorite(character)

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(DatabaseError.InvalidCharacter, result.value)
        }
    }

    @Test
    fun `saveFavorite, data source return UnknownError, return UnknownError`() = runBlockingTest {
        coEvery { localDataSource.saveFavorite(any()) } returns Result.Error(DatabaseError.UnknownError)

        val result = repository.saveFavorite(makeValidCharacterModel())

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(DatabaseError.UnknownError, result.value)
        }
    }

    @Test
    fun `removeFavorite, saveFavorite, success, return success Unit`() = runBlockingTest {
        val characterModel = makeValidCharacterModel()
        val entity = FavoriteEntity(
            id = characterModel.id ?: -1,
            name = characterModel.name ?: "",
            imageUrl = characterModel.imageUrl
        )
        coEvery { localDataSource.removeSavedFavorite(entity) } returns Result.Success(Unit)

        val result = repository.removeSavedFavorite(characterModel)

        when (result) {
            is Result.Success -> assertEquals(Unit, result.value)
            is Result.Error -> fail("Result should be Success")
        }
    }

    @Test
    fun `removeFavorite, characterId null, return InvalidCharacterError`() = runBlockingTest {
        val character = makeValidCharacterModel(id = null)

        val result = repository.removeSavedFavorite(character)

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(DatabaseError.InvalidCharacter, result.value)
        }
    }

    @Test
    fun `removeFavorite, character name null, return InvalidCharacterError`() = runBlockingTest {
        val character = makeValidCharacterModel(name = null)

        val result = repository.removeSavedFavorite(character)

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(DatabaseError.InvalidCharacter, result.value)
        }
    }

    @Test
    fun `removeFavorite, data source return UnknownError, return UnknownError`() = runBlockingTest {
        coEvery { localDataSource.removeSavedFavorite(any()) } returns Result.Error(DatabaseError.UnknownError)

        val result = repository.removeSavedFavorite(makeValidCharacterModel())

        when (result) {
            is Result.Success -> fail("Result should be Error")
            is Result.Error -> assertEquals(DatabaseError.UnknownError, result.value)
        }
    }

    @Test
    fun `removeFavorite with FavoriteModel, success, return success Unit`() = runBlockingTest {
        val favoriteModel = FavoriteModel(
            id = 1,
            name = "IRON MAN",
            imageUrl = null,
        )
        val favoriteEntity = FavoriteEntity(
            id = favoriteModel.id,
            name = favoriteModel.name,
            imageUrl = favoriteModel.imageUrl
        )
        coEvery { localDataSource.removeSavedFavorite(favoriteEntity) } returns Result.Success(Unit)

        val result = repository.removeSavedFavorite(favoriteModel)

        when (result) {
            is Result.Success -> assertEquals(Unit, result.value)
            is Result.Error -> fail("Result should be Success")
        }
    }

    @Test
    fun `removeFavorite with FavoriteModel, data source return UnknownError, return UnknownError`() =
        runBlockingTest {
            val favoriteModel = FavoriteModel(
                id = 1,
                name = "IRON MAN",
                imageUrl = null,
            )
            coEvery { localDataSource.removeSavedFavorite(any()) } returns Result.Error(
                DatabaseError.UnknownError
            )

            val result = repository.removeSavedFavorite(favoriteModel)

            when (result) {
                is Result.Success -> fail("Result should be Error")
                is Result.Error -> assertEquals(DatabaseError.UnknownError, result.value)
            }
        }

    private fun makeValidCharacterModel(
        id: Int? = 1,
        name: String? = "Batman",
        hasAFavorite: Boolean = false,
    ) = CharacterModel(
        id = id,
        name = name,
        description = "Cavaleiro das trevas",
        resourceURI = null,
        urls = emptyList(),
        imageUrl = null,
        comics = emptyList(),
        stories = emptyList(),
        events = emptyList(),
        series = emptyList(),
        isFavorite = hasAFavorite,
    )

    private fun makeValidCharacterModelList(
        hasAFavorite: Boolean = false
    ) = listOf(
        CharacterModel(
            id = 1,
            name = "Batman",
            description = "Cavaleiro das trevas",
            resourceURI = null,
            urls = emptyList(),
            imageUrl = null,
            comics = emptyList(),
            stories = emptyList(),
            events = emptyList(),
            series = emptyList(),
            isFavorite = false,
        ),
        CharacterModel(
            id = 2,
            name = "Barman",
            description = "Cavaleiro dos bares",
            resourceURI = null,
            urls = emptyList(),
            imageUrl = null,
            comics = emptyList(),
            stories = emptyList(),
            events = emptyList(),
            series = emptyList(),
            isFavorite = hasAFavorite,
        ),
        CharacterModel(
            id = 3,
            name = "Batman",
            description = "Cavaleiro das trevas",
            resourceURI = null,
            urls = emptyList(),
            imageUrl = null,
            comics = emptyList(),
            stories = emptyList(),
            events = emptyList(),
            series = emptyList(),
            isFavorite = false,
        ),
    )

    private fun makeValidCharacterResponseList(
        code: Int = 200
    ): CharacterApiResponse =
        CharacterApiResponseFactory.make(
            code = code,
            data = CharacterApiResponseFactory.makeCharacterDataContainer(
                result = listOf(
                    CharacterApiResponseFactory.makeCharacterResponse(
                        id = 1,
                        name = "Batman",
                        description = "Cavaleiro das trevas",
                    ),
                    CharacterApiResponseFactory.makeCharacterResponse(
                        id = 2,
                        name = "Barman",
                        description = "Cavaleiro dos bares",
                    ),
                    CharacterApiResponseFactory.makeCharacterResponse(
                        id = 3,
                        name = "Batman",
                        description = "Cavaleiro das trevas",
                    ),
                )
            )
        )
}