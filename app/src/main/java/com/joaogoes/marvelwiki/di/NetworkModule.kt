package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.data.api.CharacterApi
import com.joaogoes.marvelwiki.data.api.MarvelService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit = MarvelService.createRetrofit(moshi)

    @Provides
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi =
        retrofit.create(CharacterApi::class.java)
}