package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.domain.*
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindGetCharacterUseCase(
        getCharacterUseCase: GetCharacterUseCaseImpl
    ): GetCharacterUseCase

    @Binds
    fun bindGetCharactersUseCase(
        getCharactersUseCase: GetCharactersUseCaseImpl
    ): GetCharactersUseCase

    @Binds
    fun bindGetFavoritesUseCase(
        getFavoritesUseCase: GetFavoritesUseCaseImpl
    ): GetFavoritesUseCase

    @Binds
    fun bindSaveFavoriteUseCase(
        saveFavoriteUseCase: SaveFavoriteUseCaseImpl
    ): SaveFavoriteUseCase

    @Binds
    fun bindRemoveSavedFavoriteUseCase(
        removeSavedFavoriteUseCase: RemoveSavedFavoriteUseCaseImpl
    ): RemoveSavedFavoriteUseCase

    @Binds
    fun bindRemoveFavoriteUseCase(
        removeFavoriteUseCase: RemoveFavoriteUseCaseImpl
    ): RemoveFavoriteUseCase
}