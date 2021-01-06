package com.joaogoes.marvelwiki.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val TIMESTAMP_KEY = "ts"
private const val HASH_KEY = "hash"
private const val APIKEY_KEY = "apikey"

class AuthInterceptor(
    private val publicKey: String,
    private val privateKey: String,
) : Interceptor {
    private val marvelAuthHashGenerator = MarvelAuthHashGenerator()

    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = System.currentTimeMillis()
        val hash: String? = try {
            marvelAuthHashGenerator.generate(timestamp.toString(), publicKey, privateKey)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        var request = chain.request()
        val url = request
            .url
            .newBuilder()
            .addQueryParameter(TIMESTAMP_KEY, timestamp.toString())
            .addQueryParameter(APIKEY_KEY, publicKey)
            .addQueryParameter(HASH_KEY, hash)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}