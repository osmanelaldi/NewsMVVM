package com.example.newsmvvm.network

import com.example.newsmvvm.network.models.*
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getNewsByDate(
        @Query("published" ) date : String,
        @Query("page-size" ) pageSize : Int,
        @Query("page" ) page : Int) : NetworkResponse<NewsResponseWrapper,ErrorResponse>

    @GET("search")
    suspend fun searchNews(@Query("q") searchTerm : String) : NetworkResponse<NewsResponseWrapper,ErrorResponse>

    @GET("sections")
    suspend fun getCategories() : NetworkResponse<CategoriesResponseWrapper,ErrorResponse>

}