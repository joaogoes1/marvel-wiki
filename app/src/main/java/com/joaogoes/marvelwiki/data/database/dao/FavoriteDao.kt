package com.joaogoes.marvelwiki.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.joaogoes.marvelwiki.data.database.entity.CharacterEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM character")
    fun getAllFavorites(): List<CharacterEntity>

    @Insert
    fun insertFavorite(favorite: CharacterEntity)

    @Delete
    fun deleteFavorite(favorite: CharacterEntity)
}