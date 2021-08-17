package com.joaogoes.marvelwiki.network.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.joaogoes.marvelwiki.network.MarvelService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideRetrofit(marvelService: MarvelService, moshi: Moshi): Retrofit =
        marvelService.createRetrofit(moshi)

    @Provides
    fun provideConnectivityManager(application: Application): ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}
