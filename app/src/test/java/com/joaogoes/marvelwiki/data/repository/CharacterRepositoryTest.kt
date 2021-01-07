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
    fun `getCharacter, return success and valid, return list`() = runBlockingTest {
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
    fun `getCharacter, return success and valid, return list with favorite`() = runBlockingTest {
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

    private fun makeValidCharacterModelList(
        hasAFavorite: Boolean = false
    ) = listOf(
        CharacterModel(
            id = 1,
            name = "Batman",
            description = "Cavalheiro das trevas",
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
            description = "Cavalheiro dos bares",
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
            description = "Cavalheiro das trevas",
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

    private fun makeValidCharacterResponseList(): CharacterApiResponse =
        CharacterApiResponseFactory.make(
            data = CharacterApiResponseFactory.makeCharacterDataContainer(
                result = listOf(
                    CharacterApiResponseFactory.makeCharacterResponse(
                        id = 1,
                        name = "Batman",
                        description = "Cavalheiro das trevas",
                    ),
                    CharacterApiResponseFactory.makeCharacterResponse(
                        id = 2,
                        name = "Barman",
                        description = "Cavalheiro dos bares",
                    ),
                    CharacterApiResponseFactory.makeCharacterResponse(
                        id = 3,
                        name = "Batman",
                        description = "Cavalheiro das trevas",
                    ),
                )
            )
        )
}