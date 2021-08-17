package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel
import com.joaogoes.marvelwiki.characters.data.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavoritesUseCaseImplTest {

    private val repository: CharacterRepository = mockk()
    val useCase = GetFavoritesUseCaseImpl(repository)

    @Test
    fun `Call invoke, repository return success, return Success with the list`() = runBlockingTest {
        val expected = listOf(
            com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel(
                1, "Iron Man", null
            )
        )
        prepareScenario(Result.Success(expected))

        val result = useCase()

        coVerify(exactly = 1) { repository.getFavorites() }
        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(expected, (result as Result.Success).value)
    }

    @Test
    fun `Call invoke, repository return error, return Success with the list`() = runBlockingTest {
        prepareScenario(Result.Error(DatabaseError.UnknownError))

        val result = useCase()

        coVerify(exactly = 1) { repository.getFavorites() }
        Assert.assertTrue(result is Result.Error)
        Assert.assertEquals(DatabaseError.UnknownError, (result as Result.Error).value)
    }

    private fun prepareScenario(
        result: Result<List<com.joaogoes.marvelwiki.favorites.data.model.FavoriteModel>, DatabaseError>
    ) {
        coEvery { repository.getFavorites() } returns result
    }
}