package com.example.newsmvvm.network

import com.example.newsmvvm.network.models.CategoriesResponse
import com.example.newsmvvm.network.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getNewsByDate( @Query("use-date") date : String ) : Response<NewsResponse>

    @GET("search")
    suspend fun searchNews(@Query("q") searchTerm : String) : Response<NewsResponse>

    @GET("sections")
    suspend fun getCategories() : Response<CategoriesResponse>

}