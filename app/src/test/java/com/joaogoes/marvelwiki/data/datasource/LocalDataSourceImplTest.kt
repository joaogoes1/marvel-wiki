package com.joaogoes.marvelwiki.data.datasource

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.database.DatabaseError
import com.joaogoes.marvelwiki.data.database.dao.FavoriteDao
import com.joaogoes.marvelwiki.data.database.entity.FavoriteEntity
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class LocalDataSourceImplTest {

    private val coroutineContext: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val favoriteDao: FavoriteDao = mockk()
    private val dataSource = LocalDataSourceImpl(favoriteDao)

    @Test
    fun `saveFavorite, valid id, return Success`() = coroutineContext.runBlockingTest {
        val expected = FavoriteEntity(
            id = 123,
            name = "",
            imageUrl = null,
        )
        coEvery { favoriteDao.insertFavorite(any()) } returns expected.id.toLong()

        val result = dataSource.saveFavorite(expected)

        assert(result is Result.Success)
    }

    @Test
    fun `saveFavorite, diffferent number, return UnknownError`() = coroutineContext.runBlockingTest {
        val expected = FavoriteEntity(
            id = 123,
            name = "",
            imageUrl = null,
        )
        coEvery { favoriteDao.insertFavorite(any()) } returns 0L

        val result = dataSource.saveFavorite(expected)

        when (result) {
            is Result.Success -> fail("Should be Error")
            is Result.Error -> assert(result.value == DatabaseError.UnknownError)
        }
    }
}
