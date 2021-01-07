package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import com.joaogoes.marvelwiki.data.repository.ServiceError
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharactersUseCaseImplTest {

    private val repository: CharacterRepository = mockk()
    val useCase = GetCharactersUseCaseImpl(repository)

    @Test
    fun `Call invoke, repository return success, return Success with the list`() = runBlockingTest {
        val expected = listOf(
            CharacterModel(
                1, "Iron Man", null, null, emptyList(), null, emptyList(), emptyList(), emptyList(),
                emptyList()
            )
        )
        prepareScenario(Result.Success(expected))

        val result = useCase()

        coVerify(exactly = 1) { repository.getCharacters() }
        assertTrue(result is Result.Success)
        assertEquals(expected, (result as Result.Success).value)
    }

    @Test
    fun `Call invoke, repository return error, return Success with the list`() = runBlockingTest {
        prepareScenario(Result.Error(ServiceError.NetworkError))

        val result = useCase()

        coVerify(exactly = 1) { repository.getCharacters() }
        assertTrue(result is Result.Error)
        assertEquals(ServiceError.NetworkError, (result as Result.Error).value)
    }

    private fun prepareScenario(
        result: Result<List<CharacterModel>, ServiceError>
    ) {
        coEvery { repository.getCharacters() } returns result
    }
}