package com.joaogoes.marvelwiki.characters.di

import com.joaogoes.marvelwiki.characters.domain.GetCharacterUseCase
import com.joaogoes.marvelwiki.characters.domain.GetCharacterUseCaseImpl
import com.joaogoes.marvelwiki.characters.domain.GetCharactersUseCase
import com.joaogoes.marvelwiki.characters.domain.GetCharactersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@InstallIn(ViewModelComponent::class)
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
}
