package com.joaogoes.marvelwiki.data.datasource

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.database.dao.FavoriteDao
import com.joaogoes.marvelwiki.data.database.entity.FavoriteEntity
import com.joaogoes.marvelwiki.data.database.safeDatabaseCall
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class LocalDataSourceImplTest {

    private val favoriteDao: FavoriteDao = mockk()
    private val dataSource = LocalDataSourceImpl(favoriteDao)

    @Test
    fun `saveFavorite, valid id, return Success`() = runBlockingTest {
        val expected = FavoriteEntity(
            id = 123,
            name = "",
            imageUrl = null,
        )
        prepareScenario(Result.Success(123))

        val result = dataSource.saveFavorite(expected)

        assert(result is Result.Success)
    }

    @Test
    fun `saveFavorite, different number, return UnknownError`() = runBlockingTest {
        val expected = FavoriteEntity(
            id = 123,
            name = "",
            imageUrl = null,
        )
        prepareScenario(Result.Success(0L))

        val result = dataSource.saveFavorite(expected)

        when (result) {
            is Result.Success -> fail("Should be Error")
            is Result.Error -> assert(result.value == DatabaseError.UnknownError)
        }
    }

    private fun <T> prepareScenario(
        safeDatabaseCallResult: Result<T, DatabaseError>
    ) {
        mockkStatic("com.joaogoes.marvelwiki.data.database.DatabaseFunctionsKt")
        coEvery { safeDatabaseCall<T>(any(), any()) } returns safeDatabaseCallResult
    }
}
