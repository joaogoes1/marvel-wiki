package com.joaogoes.marvelwiki.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joaogoes.marvelwiki.data.database.dao.FavoriteDao
import com.joaogoes.marvelwiki.data.database.entity.FavoriteEntity

private const val DATABASE_NAME = "marvel_database"

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        fun buildDatabase(context: Context): MarvelDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                MarvelDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}