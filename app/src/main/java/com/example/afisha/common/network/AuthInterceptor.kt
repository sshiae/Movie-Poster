package com.example.afisha.common.network

import com.example.afisha.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor для подстановки в каждый запрос API ключа
 */
class AuthInterceptor : Interceptor {

    private val apikey = BuildConfig.API_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(ApiConstants.API_KEY_HEADER, apikey)
            .build()
        return chain.proceed(request)
    }
}