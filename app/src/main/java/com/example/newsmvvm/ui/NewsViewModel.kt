package com.example.newsmvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.newsmvvm.network.Repository
import com.example.newsmvvm.network.models.*
import com.example.newsmvvm.util.ErrorHandler
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NewsViewModel (val repository: Repository) : ViewModel(){

    val error : MutableLiveData<String?> = MutableLiveData()

    val categories : MutableLiveData<CategoriesResponseWrapper> = MutableLiveData()

    val latestNews =  Pager(PagingConfig(pageSize = 10)){
        NewsDataSource(repository)
    }.flow.cachedIn(viewModelScope)

    fun searchNews(searchTerm : String) =
         Pager(PagingConfig(pageSize = 10)) {
            NewsDataSource(repository, searchTerm)
        }.flow.cachedIn(viewModelScope)

    fun dismissError(){
        error.postValue(null)
    }

    fun getCategories() = viewModelScope.launch {
        when(val response = repository.getCategories()){
            is NetworkResponse.Success ->{
                categories.postValue(response.body)
            }
            is NetworkResponse.NetworkError -> error.postValue(ErrorHandler.handleError(response))
            is NetworkResponse.ServerError -> error.postValue(ErrorHandler.handleError(response))
            is NetworkResponse.UnknownError -> error.postValue(ErrorHandler.handleError(response))
        }
    }

    fun getSavedNews() = repository.getSavedNews()
    fun deleteNews(news : News) = viewModelScope.launch {
        news.isSaved = false
        repository.deleteNewsItem(news)
    }

    fun saveNews(news : News) = viewModelScope.launch {
        news.isSaved = true
        repository.insertOrUpdateNews(news)
    }

    fun postError(error : Throwable){
        this.error.postValue(error.message ?: ErrorHandler.UNKNOWN_ERROR)
    }
}