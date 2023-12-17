package com.example.afisha.common.network

import com.example.afisha.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for injecting the API key into each API request.
 */
class AuthInterceptor : Interceptor {

    private val apiKey = BuildConfig.API_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(ApiConstants.API_KEY_HEADER, apiKey)
            .build()
        return chain.proceed(request)
    }
}