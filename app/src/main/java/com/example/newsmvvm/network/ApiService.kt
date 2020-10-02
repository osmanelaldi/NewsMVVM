package com.example.newsmvvm.network

import com.example.newsmvvm.network.models.CategoriesResponse
import com.example.newsmvvm.network.models.ErrorResponse
import com.example.newsmvvm.network.models.NewsResponse
import com.example.newsmvvm.network.models.NewsResponseWrapper
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getNewsByDate(@Query("use-date" ) date : String) : NetworkResponse<NewsResponseWrapper,ErrorResponse>

    @GET("search")
    suspend fun searchNews(@Query("q") searchTerm : String) : NetworkResponse<NewsResponseWrapper,ErrorResponse>

    @GET("sections")
    suspend fun getCategories() : NetworkResponse<CategoriesResponse,ErrorResponse>

}