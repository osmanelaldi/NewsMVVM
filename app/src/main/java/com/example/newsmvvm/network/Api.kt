package com.example.newsmvvm.network

import com.example.newsmvvm.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object Api {
    private lateinit var dataApiInstance: Retrofit
    private fun getDataApi(): Retrofit {
        if (!::dataApiInstance.isInitialized) {
            val client = okHttpBuilder().build()
            dataApiInstance = Retrofit.Builder().client(client).baseUrl(Constants.API_BASE_URL).build()
        }
        return dataApiInstance
    }

    var client : ApiService = getDataApi().create(ApiService::class.java)

    private fun okHttpBuilder() = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(HeadersInterceptor())

}