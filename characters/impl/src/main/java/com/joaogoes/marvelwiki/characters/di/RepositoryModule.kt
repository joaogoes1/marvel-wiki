package com.joaogoes.marvelwiki.characters.di

import com.joaogoes.marvelwiki.characters.data.repository.CharacterRepository
import com.joaogoes.marvelwiki.characters.data.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(
        characterRepository: CharacterRepositoryImpl
    ): CharacterRepository
}