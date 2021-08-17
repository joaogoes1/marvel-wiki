package com.joaogoes.marvelwiki.favorites.di

import android.app.Application
import com.joaogoes.marvelwiki.favorites.data.database.MarvelDatabase
import com.joaogoes.marvelwiki.favorites.data.database.dao.FavoriteDao
import com.joaogoes.marvelwiki.favorites.data.datasource.LocalDataSource
import com.joaogoes.marvelwiki.favorites.data.datasource.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
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


@InstallIn(SingletonComponent::class)
@Module
interface LocalDatasourceModule {

    @Binds
    fun bindsLocalDataSource(
        bindsLocalDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource
}