package com.joaogoes.marvelwiki.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?,
)