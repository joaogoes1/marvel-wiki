package com.joaogoes.marvelwiki.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
interface NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://gateway.marvel.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}