package com.example.newsmvvm.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.network.Repository
import com.example.newsmvvm.network.models.ErrorResponse
import com.example.newsmvvm.network.models.News
import com.example.newsmvvm.network.models.NewsResponse
import com.example.newsmvvm.network.models.NewsResponseWrapper
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class NewsViewModel (val repository: Repository) : ViewModel(){

    val latestNews : MutableLiveData<NetworkResponse<NewsResponseWrapper,ErrorResponse>> = MutableLiveData()
    var latestNewsPage = 1

    val searchedNews : MutableLiveData<NetworkResponse<NewsResponseWrapper,ErrorResponse>> = MutableLiveData()
    var searchedNewsPage = 1

    val savedNews : MutableLiveData<NewsResponseWrapper> = MutableLiveData()
    var savedNewsPage = 1

    init {
        getLatestNews()
    }
    private fun getLatestNews() = viewModelScope.launch {
        when(val response = repository.getNewsByDate(getNow())){
            is NetworkResponse.Success ->{
                latestNews.postValue(response)
            }
            is NetworkResponse.NetworkError -> {}
            is NetworkResponse.ServerError -> {}
            is NetworkResponse.UnknownError -> {}
        }
    }

    fun searchNews(searchTerm : String) = viewModelScope.launch {
        when(val response = repository.searchNews(searchTerm)){
            is NetworkResponse.Success ->{
                searchedNews.postValue(response)
            }
            is NetworkResponse.NetworkError -> {}
            is NetworkResponse.ServerError -> {}
            is NetworkResponse.UnknownError -> {}
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