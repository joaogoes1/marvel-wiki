package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveFavoriteUseCaseImplTest {

    private val repository: CharacterRepository = mockk()
    val useCase = SaveFavoriteUseCaseImpl(repository)

    @Test
    fun `Call invoke, repository return success, return Success with the list`() = runBlockingTest {
        val expected = CharacterModel(
            1, "Iron Man", null, null, emptyList(), null, emptyList(), emptyList(), emptyList(),
            emptyList()
        )
        prepareScenario()

        val result = useCase(expected)

        coVerify(exactly = 1) { repository.saveFavorite(expected) }
        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(Unit, (result as Result.Success).value)
    }

    @Test
    fun `Call invoke, repository return error, return Success with the list`() = runBlockingTest {
        val expected = CharacterModel(
            1, "Iron Man", null, null, emptyList(), null, emptyList(), emptyList(), emptyList(),
            emptyList()
        )
        prepareScenario(Result.Error(DatabaseError.UnknownError))

        val result = useCase(expected)

        coVerify(exactly = 1) { repository.saveFavorite(expected) }
        Assert.assertTrue(result is Result.Error)
        Assert.assertEquals(DatabaseError.UnknownError, (result as Result.Error).value)
    }

    private fun prepareScenario(
        result: Result<Unit, DatabaseError> = Result.Success(Unit)
    ) {
        coEvery { repository.saveFavorite(any()) } returns result
    }
}