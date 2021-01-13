package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoveFavoriteUseCaseImplTest {

    private val repository: CharacterRepository = mockk()
    val useCase = RemoveFavoriteUseCaseImpl(repository)

    @Test
    fun `Call invoke, repository return success, return Success with the list`() = runBlockingTest {
        val expected = FavoriteModel(
            1, "Iron Man", null
        )
        prepareScenario()

        val result = useCase(expected)

        coVerify(exactly = 1) { repository.removeSavedFavorite(expected) }
        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(Unit, (result as Result.Success).value)
    }

    @Test
    fun `Call invoke, repository return error, return Success with the list`() = runBlockingTest {
        val expected = FavoriteModel(
            1, "Iron Man", null
        )
        prepareScenario(Result.Error(DatabaseError.UnknownError))

        val result = useCase(expected)

        coVerify(exactly = 1) { repository.removeSavedFavorite(expected) }
        Assert.assertTrue(result is Result.Error)
        Assert.assertEquals(DatabaseError.UnknownError, (result as Result.Error).value)
    }

    private fun prepareScenario(
        result: Result<Unit, DatabaseError> = Result.Success(Unit)
    ) {
        coEvery { repository.removeSavedFavorite(any<FavoriteModel>()) } returns result
    }
}