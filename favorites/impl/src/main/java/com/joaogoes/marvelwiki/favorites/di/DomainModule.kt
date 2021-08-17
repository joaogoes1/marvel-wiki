package com.joaogoes.marvelwiki.favorites.di

import com.joaogoes.marvelwiki.favorites.domain.GetFavoritesUseCase
import com.joaogoes.marvelwiki.favorites.domain.GetFavoritesUseCaseImpl
import com.joaogoes.marvelwiki.favorites.domain.RemoveFavoriteUseCase
import com.joaogoes.marvelwiki.favorites.domain.RemoveFavoriteUseCaseImpl
import com.joaogoes.marvelwiki.favorites.domain.RemoveSavedFavoriteUseCase
import com.joaogoes.marvelwiki.favorites.domain.RemoveSavedFavoriteUseCaseImpl
import com.joaogoes.marvelwiki.favorites.domain.SaveFavoriteUseCase
import com.joaogoes.marvelwiki.favorites.domain.SaveFavoriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@InstallIn(ViewModelComponent::class)
@Module
interface DomainModule {

    @Binds
    fun bindsGetFavoritesUseCase(
        getFavoritesUseCaseImpl: GetFavoritesUseCaseImpl
    ): GetFavoritesUseCase

    @Binds
    fun bindsSaveFavoriteUseCase(
        saveFavoriteUseCaseImpl: SaveFavoriteUseCaseImpl
    ): SaveFavoriteUseCase

    @Binds
    fun bindsRemoveFavoriteUseCase(
        saveRemoveUseCaseImpl: RemoveFavoriteUseCaseImpl
    ): RemoveFavoriteUseCase

    @Binds
    fun bindsRemoveSavedFavoriteUseCase(
        removeSavedFavoriteUseCaseImpl: RemoveSavedFavoriteUseCaseImpl
    ): RemoveSavedFavoriteUseCase
}
