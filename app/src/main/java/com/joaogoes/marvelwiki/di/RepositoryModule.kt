package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.data.repository.CharacterRepository
import com.joaogoes.marvelwiki.data.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindCharacterRepository(
        characterRepository: CharacterRepositoryImpl
    ): CharacterRepository
}