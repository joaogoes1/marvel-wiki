package com.joaogoes.marvelwiki.favorites.di

import com.joaogoes.marvelwiki.favorites.data.repository.FavoritesRepository
import com.joaogoes.marvelwiki.favorites.data.repository.FavoritesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindsFavoriteRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository
}