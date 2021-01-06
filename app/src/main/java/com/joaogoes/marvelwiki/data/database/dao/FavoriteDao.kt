package com.joaogoes.marvelwiki.data.database.dao

import androidx.room.*
import com.joaogoes.marvelwiki.data.database.entity.CharacterEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM character")
    fun getAllFavorites(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: CharacterEntity): Long

    @Delete
    fun deleteFavorite(favorite: CharacterEntity): Int
}