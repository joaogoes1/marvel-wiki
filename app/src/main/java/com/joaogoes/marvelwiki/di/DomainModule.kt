package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.domain.GetCharacters
import com.joaogoes.marvelwiki.domain.GetCharactersUseCase
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindGetCharacterUseCase(
        getCharactersUseCase: GetCharacters
    ): GetCharactersUseCase
}