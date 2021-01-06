package com.joaogoes.marvelwiki.data.service

import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class ConnectionInterceptor @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectionException()
        }

        val r: Request.Builder = chain.request().newBuilder()
        return chain.proceed(r.build())
    }

    private fun isConnected(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}

class NoConnectionException(message: String = "No connection found") : IOException(message)