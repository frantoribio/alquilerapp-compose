package com.example.alquilerapp.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider()
        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        Log.d("AuthInterceptor", "Token enviado: $token")
        Log.d("AuthInterceptor", "Request con Authorization: ${request.headers["Authorization"]}")

        return chain.proceed(request)
    }
}