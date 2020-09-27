package com.example.newsmvvm.network.models

data class NewsResponse(
    val currentPage: Int,
    val orderBy: String,
    val pageSize: Int,
    val pages: Int,
    val news: List<News>,
    val startIndex: Int,
    val status: String,
    val total: Int,
    val userTier: String)