package com.example.newsmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsmvvm.network.models.News

@Dao
interface NewsDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(news : News) : Long

    @Query("SELECT * from news")
    fun getSavedNews() : LiveData<List<News>>

    @Delete
    suspend fun deleteNewsItem(news: News)
}