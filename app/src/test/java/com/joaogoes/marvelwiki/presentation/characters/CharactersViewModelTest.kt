package com.joaogoes.marvelwiki.presentation.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.domain.GetCharactersUseCase
import com.joaogoes.marvelwiki.utils.MainCoroutineRule
import com.joaogoes.marvelwiki.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getCharactersUseCase = mockk<GetCharactersUseCase>()
    private val viewModel = CharactersViewModel(getCharactersUseCase)

    @Test
    fun `When load characters, if return a list, show success state`() {
        val charactersList = listOf(
            makeEmptyCharacter(),
            makeEmptyCharacter()
        )
        prepareScenario(Result.Success(charactersList))

        viewModel.loadCharacters()

        viewModel.viewState.state.observeForever {}
        viewModel.viewState.characters.observeForever {}
        val state = viewModel.viewState.state.getOrAwaitValue()
        val list = viewModel.viewState.characters.getOrAwaitValue()

        assertEquals(CharactersViewState.State.SUCCESS, state)
        assertEquals(charactersList, list)
    }

    @Test
    fun `When load characters, if return empty list, show empty state`() {
        prepareScenario(Result.Success(emptyList()))

        viewModel.loadCharacters()

        viewModel.viewState.state.observeForever {}
        viewModel.viewState.characters.observeForever {}
        val state = viewModel.viewState.state.getOrAwaitValue()
        val list = viewModel.viewState.characters.getOrAwaitValue()

        assertEquals(CharactersViewState.State.EMPTY_STATE, state)
        assertEquals(0, list.size)
    }

    @Test
    fun `When load characters, if return Error, set error state`() {
        prepareScenario(Result.Error(ServiceError.UnknownError))

        viewModel.loadCharacters()

        viewModel.viewState.state.observeForever {}
        assertEquals(viewModel.viewState.state.getOrAwaitValue(), CharactersViewState.State.ERROR)
    }

    private fun makeEmptyCharacter() = CharacterModel(
        null, null, null, null, emptyList(), null, emptyList(), emptyList(), emptyList(), emptyList()
    )

    private fun prepareScenario(
        result: Result<List<CharacterModel>, ServiceError>
    ) {
        coEvery { getCharactersUseCase.invoke() } returns result
    }
}
