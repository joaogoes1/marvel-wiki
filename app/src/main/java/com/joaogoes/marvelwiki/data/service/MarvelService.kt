package com.joaogoes.marvelwiki.data.service

import com.joaogoes.marvelwiki.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class MarvelService @Inject constructor(
    private val connectionInterceptor: ConnectionInterceptor
) {

    fun createRetrofit(moshi: Moshi): Retrofit {

        val client = OkHttpClient
            .Builder()
            .addInterceptor(
                AuthInterceptor(
                    publicKey = BuildConfig.PUBLIC_KEY,
                    privateKey = BuildConfig.PRIVATE_KEY
                )
            )
            .addInterceptor(connectionInterceptor)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }

        return Retrofit
            .Builder()
            .client(client.build())
            .baseUrl("https://gateway.marvel.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}