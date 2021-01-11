package com.joaogoes.marvelwiki.presentation.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.domain.GetCharactersUseCase
import com.joaogoes.marvelwiki.domain.RemoveSavedFavoriteUseCase
import com.joaogoes.marvelwiki.domain.SaveFavoriteUseCase
import com.joaogoes.marvelwiki.utils.MainCoroutineRule
import com.joaogoes.marvelwiki.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getCharactersUseCase = mockk<GetCharactersUseCase>()
    private val saveFavorite = mockk<SaveFavoriteUseCase>()
    private val removeSavedFavoriteUseCase = mockk<RemoveSavedFavoriteUseCase>()
    private val viewModel = CharactersViewModel(
        getCharactersUseCase,
        saveFavorite,
        removeSavedFavoriteUseCase,
    )

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

    @Test
    fun `When load characters, if return No Connection, set no connection state`() {
        prepareScenario(Result.Error(ServiceError.NoConnectionError))

        viewModel.loadCharacters()

        viewModel.viewState.state.observeForever {}
        assertEquals(
            viewModel.viewState.state.getOrAwaitValue(),
            CharactersViewState.State.NO_CONNECTION
        )
    }

    @Test
    fun `Favorite character, when character is not favorite, calls SaveFavoriteUseCase`() {
        prepareScenario()
        val character = makeEmptyCharacter()

        viewModel.favoriteCharacter(character)

        coVerify(exactly = 1) { saveFavorite(character) }
    }

    @Test
    fun `Favorite character, when character is favorite, calls RemoveSavedFavoriteUseCase`() {
        prepareScenario()
        val character = makeEmptyCharacter().copy(isFavorite = true)

        viewModel.favoriteCharacter(character)

        coVerify(exactly = 1) { removeSavedFavoriteUseCase(character) }
    }

    @Test
    fun `Favorite character, on success and character is not favorite, change the list`() {
        val charactersList = makeCharacterList(false)
        prepareScenario(Result.Success(charactersList))
        viewModel.viewState.characters.observeForever {}

        viewModel.loadCharacters()

        viewModel.favoriteCharacter(charactersList[1])

        val newList = viewModel.viewState.characters.getOrAwaitValue()
        assertTrue(newList[1].isFavorite)
    }

    @Test
    fun `Favorite character, on success and character is favorite, change the list`() {
        val charactersList = makeCharacterList(true)
        prepareScenario(Result.Success(charactersList))
        viewModel.viewState.characters.observeForever {}

        viewModel.loadCharacters()

        viewModel.favoriteCharacter(charactersList[1])

        val newList = viewModel.viewState.characters.getOrAwaitValue()
        assertFalse(newList[1].isFavorite)
    }

    private fun makeEmptyCharacter() = CharacterModel(
        null,
        null,
        null,
        null,
        emptyList(),
        null,
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList()
    )

    private fun makeCharacterList(isFavorite: Boolean) = listOf(
        CharacterModel(
            id = 1,
            name = "Iron Man",
            description = "Lorem ipsum",
            null,
            emptyList(),
            null,
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList()
        ),
        CharacterModel(
            id = 2,
            name = "Agent Zero",
            description = "Lorem ipsum",
            null,
            emptyList(),
            null,
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            isFavorite = isFavorite
        ),
        CharacterModel(
            id = 3,
            name = "Ajak",
            description = "Lorem ipsum",
            null,
            emptyList(),
            null,
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList()
        ),
    )

    private fun prepareScenario(
        loadCharactersResult: Result<List<CharacterModel>, ServiceError> = Result.Success(emptyList()),
        saveFavoriteResult: Result<Unit, DatabaseError> = Result.Success(Unit),
        removeSavedFavoriteResult: Result<Unit, DatabaseError> = Result.Success(Unit),
    ) {
        coEvery { getCharactersUseCase.invoke() } returns loadCharactersResult
        coEvery { saveFavorite.invoke(any()) } returns saveFavoriteResult
        coEvery { removeSavedFavoriteUseCase.invoke(any()) } returns removeSavedFavoriteResult
    }
}
