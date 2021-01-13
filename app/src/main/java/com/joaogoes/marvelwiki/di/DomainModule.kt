package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.domain.GetCharacterUseCase
import com.joaogoes.marvelwiki.domain.GetCharacterUseCaseImpl
import com.joaogoes.marvelwiki.domain.GetCharactersUseCase
import com.joaogoes.marvelwiki.domain.GetCharactersUseCaseImpl
import com.joaogoes.marvelwiki.domain.GetFavoritesUseCase
import com.joaogoes.marvelwiki.domain.GetFavoritesUseCaseImpl
import com.joaogoes.marvelwiki.domain.RemoveFavoriteUseCase
import com.joaogoes.marvelwiki.domain.RemoveFavoriteUseCaseImpl
import com.joaogoes.marvelwiki.domain.RemoveSavedFavoriteUseCase
import com.joaogoes.marvelwiki.domain.RemoveSavedFavoriteUseCaseImpl
import com.joaogoes.marvelwiki.domain.SaveFavoriteUseCase
import com.joaogoes.marvelwiki.domain.SaveFavoriteUseCaseImpl
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