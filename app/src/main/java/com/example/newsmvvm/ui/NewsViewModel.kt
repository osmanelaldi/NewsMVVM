package com.example.newsmvvm.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.network.Repository
import com.example.newsmvvm.network.models.*
import com.example.newsmvvm.util.ErrorHandler
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NewsViewModel (val repository: Repository) : ViewModel(){

    val error : MutableLiveData<String?> = MutableLiveData()

    val latestNews : MutableLiveData<NewsResponseWrapper> = MutableLiveData()
    var latestNewsPage = 1

    var searchedNews : MutableLiveData<NewsResponseWrapper> = MutableLiveData()
    var searchedNewsPage = 1

    val categories : MutableLiveData<CategoriesResponseWrapper> = MutableLiveData()

    fun clearSearch(){
        searchedNews = MutableLiveData()
    }

    fun dismissError(){
        error.postValue(null)
    }

    fun getLatestNews() = viewModelScope.launch {
        when(val response = repository.getNewsByDate(getNow())){
            is NetworkResponse.Success ->{
                latestNews.postValue(response.body)
            }
            is NetworkResponse.NetworkError -> error.postValue(ErrorHandler.handleError(response))
            is NetworkResponse.ServerError -> error.postValue(ErrorHandler.handleError(response))
            is NetworkResponse.UnknownError -> error.postValue(ErrorHandler.handleError(response))
        }
    }

    fun searchNews(searchTerm : String) = viewModelScope.launch {
        when(val response = repository.searchNews(searchTerm)){
            is NetworkResponse.Success ->{
                searchedNews.postValue(response.body)
            }
            is NetworkResponse.NetworkError -> error.postValue(ErrorHandler.handleError(response))
            is NetworkResponse.ServerError -> error.postValue(ErrorHandler.handleError(response))
            is NetworkResponse.UnknownError -> error.postValue(ErrorHandler.handleError(response))
        }
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

    private fun getNow() : String{
        val date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return simpleDateFormat.format(date)
    }

}