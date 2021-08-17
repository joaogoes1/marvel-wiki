package com.joaogoes.marvelwiki.favorites.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joaogoes.marvelwiki.favorites.data.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM character ORDER BY name ASC")
    fun getAllFavorites(): List<FavoriteEntity>

    @Query("SELECT id FROM character")
    fun getAllFavoritesId(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity): Long

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity): Int
}