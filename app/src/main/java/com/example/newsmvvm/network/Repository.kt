package com.example.newsmvvm.network

import com.example.newsmvvm.db.NewsDatabase
import com.example.newsmvvm.network.models.News

class Repository ( val db : NewsDatabase){

    suspend fun getNewsByDate(date : String, pageSize : Int = 10, page : Int) = Api.client.getNewsByDate(date,pageSize,page)

    suspend fun searchNews(searchTerm : String) = Api.client.searchNews(searchTerm)

    suspend fun getCategories() = Api.client.getCategories()

    suspend fun insertOrUpdateNews(news : News) = db.getNewsDao().insertOrUpdate(news)

    suspend fun deleteNewsItem (news : News) = db.getNewsDao().deleteNewsItem(news)

    fun getSavedNews() = db.getNewsDao().getSavedNews()

}