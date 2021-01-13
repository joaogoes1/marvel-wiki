package com.joaogoes.marvelwiki.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.domain.GetCharacterUseCase
import com.joaogoes.marvelwiki.domain.RemoveSavedFavoriteUseCase
import com.joaogoes.marvelwiki.domain.SaveFavoriteUseCase
import com.joaogoes.marvelwiki.presentation.characters.CharactersViewState
import com.joaogoes.marvelwiki.utils.MainCoroutineRule
import com.joaogoes.marvelwiki.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getCharacterUseCase = mockk<GetCharacterUseCase>()
    private val saveFavorite = mockk<SaveFavoriteUseCase>()
    private val removeSavedFavoriteUseCase = mockk<RemoveSavedFavoriteUseCase>()
    private val viewModel = CharacterDetailsViewModel(
        getCharacterUseCase,
        saveFavorite,
        removeSavedFavoriteUseCase,
    )

    @Test
    fun `loadCharacter, return Success, set state success and load character`() {
        val expected = makeEmptyCharacter()
        prepareScenario(Result.Success(expected))

        viewModel.loadCharacter(1)

        viewModel.viewState.character.observeForever {  }
        viewModel.viewState.state.observeForever {  }

        val character = viewModel.viewState.character.getOrAwaitValue()
        val state = viewModel.viewState.state.getOrAwaitValue()
        assertEquals(expected, character)
        assertEquals(CharacterDetailsViewState.State.SUCCESS, state)
    }

    @Test
    fun `loadCharacter, return Error, set state error and character is null`() {
        prepareScenario(Result.Error(ServiceError.UnknownError))

        viewModel.loadCharacter(1)

        viewModel.viewState.character.observeForever {  }
        viewModel.viewState.state.observeForever {  }

        val character = viewModel.viewState.character.getOrAwaitValue()
        val state = viewModel.viewState.state.getOrAwaitValue()
        assertNull(character)
        assertEquals(CharacterDetailsViewState.State.ERROR, state)
    }

    @Test
    fun `loadCharacter, no connection, set state error and character is null`() {
        prepareScenario(Result.Error(ServiceError.NoConnectionError))

        viewModel.loadCharacter(1)

        viewModel.viewState.character.observeForever {  }
        viewModel.viewState.state.observeForever {  }

        val character = viewModel.viewState.character.getOrAwaitValue()
        val state = viewModel.viewState.state.getOrAwaitValue()
        assertNull(character)
        assertEquals(CharacterDetailsViewState.State.NO_CONNECTION, state)
    }

    @Test
    fun `saveFavorite, return Success, isFavorite is true`() {
        val expected = makeEmptyCharacter(isFavorite = false)
        prepareScenario(
            loadCharactersResult = Result.Success(expected),
            saveFavoriteResult = Result.Success(Unit)
        )

        viewModel.loadCharacter(1)
        viewModel.favoriteCharacter()

        viewModel.viewState.character.observeForever {}

        val character = viewModel.viewState.character.getOrAwaitValue()
        assertTrue(character?.isFavorite == true)
    }

    @Test
    fun `removeFavoriteCharacter, return Success, isFavorite is false`() {
        val expected = makeEmptyCharacter(isFavorite = true)
        prepareScenario(
            loadCharactersResult = Result.Success(expected),
            saveFavoriteResult = Result.Success(Unit)
        )

        viewModel.loadCharacter(1)
        viewModel.favoriteCharacter()

        viewModel.viewState.character.observeForever {}

        val character = viewModel.viewState.character.getOrAwaitValue()
        assertFalse(character?.isFavorite == true)
    }

    private fun makeEmptyCharacter(
        id: Int = 1,
        isFavorite: Boolean = false
    ) = CharacterModel(
        id,
        null,
        null,
        null,
        emptyList(),
        null,
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        isFavorite
    )

    private fun prepareScenario(
        loadCharactersResult: Result<CharacterModel, ServiceError> = Result.Success(
            makeEmptyCharacter()
        ),
        saveFavoriteResult: Result<Unit, DatabaseError> = Result.Success(Unit),
        removeSavedFavoriteResult: Result<Unit, DatabaseError> = Result.Success(Unit),
    ) {
        coEvery { getCharacterUseCase.invoke(any()) } returns loadCharactersResult
        coEvery { saveFavorite.invoke(any()) } returns saveFavoriteResult
        coEvery { removeSavedFavoriteUseCase.invoke(any()) } returns removeSavedFavoriteResult
    }
}
