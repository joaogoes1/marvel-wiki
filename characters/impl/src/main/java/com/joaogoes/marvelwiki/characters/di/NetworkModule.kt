package com.joaogoes.marvelwiki.characters.di

import com.joaogoes.marvelwiki.characters.data.service.CharacterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@InstallIn(SingletonComponent::class)
@Module
class CharacterApiModule {

    @Provides
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi =
        retrofit.create(CharacterApi::class.java)
}