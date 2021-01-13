package com.joaogoes.marvelwiki.presentation.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.model.FavoriteModel
import com.joaogoes.marvelwiki.domain.GetFavoritesUseCase
import com.joaogoes.marvelwiki.domain.RemoveFavoriteUseCase
import com.joaogoes.marvelwiki.utils.MainCoroutineRule
import com.joaogoes.marvelwiki.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getFavoritesUseCase = mockk<GetFavoritesUseCase>()
    private val removeSavedFavoriteUseCase = mockk<RemoveFavoriteUseCase>()
    private val viewModel = FavoritesViewModel(
        getFavoritesUseCase,
        removeSavedFavoriteUseCase,
    )

    @Test
    fun `When load favorites, if return a list, show success state`() {
        val favoriteList = makeFavoriteList()
        prepareScenario(Result.Success(favoriteList))

        viewModel.loadFavorites()

        viewModel.viewState.state.observeForever {}
        viewModel.viewState.favorites.observeForever {}
        val state = viewModel.viewState.state.getOrAwaitValue()
        val list = viewModel.viewState.favorites.getOrAwaitValue()

        assertEquals(FavoriteViewState.State.SUCCESS, state)
        assertEquals(favoriteList, list)
    }

    @Test
    fun `When load favorites, if return empty list, show empty state`() {
        prepareScenario(Result.Success(emptyList()))

        viewModel.loadFavorites()

        viewModel.viewState.state.observeForever {}
        viewModel.viewState.favorites.observeForever {}
        val state = viewModel.viewState.state.getOrAwaitValue()
        val list = viewModel.viewState.favorites.getOrAwaitValue()

        assertEquals(FavoriteViewState.State.EMPTY_STATE, state)
        assertEquals(0, list.size)
    }

    @Test
    fun `When load favorites, if return Error, set error state`() {
        prepareScenario(Result.Error(DatabaseError.UnknownError))

        viewModel.loadFavorites()

        viewModel.viewState.state.observeForever {}
        assertEquals(viewModel.viewState.state.getOrAwaitValue(), FavoriteViewState.State.ERROR)
    }

    @Test
    fun `Remove favorite character, on success, change the list`() {
        prepareScenario()

        viewModel.loadFavorites()

        viewModel.removeFavorite(makeFavorite())

        verify(exactly = 2) { viewModel.loadFavorites() }
    }

    private fun makeFavorite() = FavoriteModel(
        id = 123,
        name = "Captain America",
        imageUrl = null,
    )

    private fun makeFavoriteList() = listOf(
        FavoriteModel(
            id = 1,
            name = "Iron Man",
            imageUrl = null,
        ),
        FavoriteModel(
            id = 2,
            name = "Agent Zero",
            imageUrl = null,
        ),
        FavoriteModel(
            id = 3,
            name = "Ajak",
            imageUrl = null,
        ),
    )

    private fun prepareScenario(
        loadFavoritesResult: Result<List<FavoriteModel>, DatabaseError> = Result.Success(
            makeFavoriteList()
        ),
        removeSavedFavoriteResult: Result<Unit, DatabaseError> = Result.Success(Unit),
    ) {
        coEvery { getFavoritesUseCase.invoke() } returns loadFavoritesResult
        coEvery { removeSavedFavoriteUseCase.invoke(any()) } returns removeSavedFavoriteResult
    }
}
