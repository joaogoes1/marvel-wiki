package com.joaogoes.marvelwiki.di

import android.app.Application
import android.content.Context
import com.joaogoes.marvelwiki.data.database.MarvelDatabase
import com.joaogoes.marvelwiki.data.database.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): MarvelDatabase =
        MarvelDatabase.buildDatabase(application)

    @Provides
    @Singleton
    fun providesFavoriteDao(database: MarvelDatabase): FavoriteDao = database.favoriteDao()
}