package com.example.newsmvvm.ui

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import com.example.newsmvvm.network.Repository
import com.example.newsmvvm.network.models.News
import com.example.newsmvvm.util.ErrorHandler
import com.haroldadmin.cnradapter.NetworkResponse
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewsDataSource(val repository: Repository, var searchTerm : String = "") : PagingSource<Int, News>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = if (searchTerm.isEmpty()) repository.getNewsByDate(getNow(),page = currentLoadingPageKey)
                else repository.searchNews(searchTerm)
            when(response){
                is NetworkResponse.Success -> {
                    val responseData = mutableListOf<News>()
                    val data = response.body.response.news ?: emptyList<News>()
                    responseData.addAll(data)
                    val prevKKey = if(currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
                    return LoadResult.Page(
                        data = responseData,
                        prevKey = prevKKey,
                        nextKey = currentLoadingPageKey.plus(1)
                    )
                }
                is NetworkResponse.NetworkError -> return LoadResult.Error(ErrorHandler.provideError(response))
                is NetworkResponse.ServerError -> return LoadResult.Error(ErrorHandler.provideError(response))
                is NetworkResponse.UnknownError -> return LoadResult.Error(ErrorHandler.provideError(response))
            }
        }catch (e : Exception){
            return LoadResult.Error(e)
        }
    }

    private fun getNow() : String{
        val date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return simpleDateFormat.format(date)
    }
}