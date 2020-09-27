package com.example.newsmvvm.network.models

data class CategoriesResponse(
    val results: List<Category>,
    val status: String,
    val total: Int,
    val userTier: String
)