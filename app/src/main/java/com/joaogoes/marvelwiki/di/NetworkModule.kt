package com.joaogoes.marvelwiki.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.joaogoes.marvelwiki.data.service.CharacterApi
import com.joaogoes.marvelwiki.data.service.MarvelService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideRetrofit(marvelService: MarvelService, moshi: Moshi): Retrofit = marvelService.createRetrofit(moshi)

    @Provides
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi =
        retrofit.create(CharacterApi::class.java)

    @Provides
    fun provideConectivityManager(application: Application): ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}