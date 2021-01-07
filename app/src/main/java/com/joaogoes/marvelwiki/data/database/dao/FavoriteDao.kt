package com.joaogoes.marvelwiki.data.database.dao

import androidx.room.*
import com.joaogoes.marvelwiki.data.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM character ORDER BY name ASC")
    fun getAllFavorites(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity): Long

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity): Int
}