package com.example.newsmvvm.network

import com.example.newsmvvm.util.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeadersInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("api-key", Constants.API_KEY)
        return chain.proceed(builder.build())
    }

}