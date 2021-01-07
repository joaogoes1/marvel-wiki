package com.joaogoes.marvelwiki.data.repository

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.datasource.LocalDataSource
import com.joaogoes.marvelwiki.data.datasource.RemoteDataSource
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.response.CharacterApiResponse
import com.joaogoes.marvelwiki.utils.CharacterApiResponseFactory
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
            val expected = makeValidCharacterModel(true)
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
    fun `getCharacter, return success but element is null, return NotFoundError`() = runBlockingTest {
        coEvery { remoteDataSource.getCharacter(any()) } returns Result.Success(
            CharacterApiResponseFactory.make(data = CharacterApiResponseFactory.makeCharacterDataContainer(null))
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

    private fun makeValidCharacterModel(
        hasAFavorite: Boolean = false
    ) = CharacterModel(
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