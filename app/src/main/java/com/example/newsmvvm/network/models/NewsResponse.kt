package com.example.newsmvvm.network.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val currentPage: Int,
    val orderBy: String,
    val pageSize: Int,
    val pages: Int,
    @SerializedName("results")
    val news: ArrayList<News>,
    val startIndex: Int,
    val status: String,
    val total: Int,
    val userTier: String)