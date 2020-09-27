package com.example.newsmvvm.network.models

data class Category(
    val apiUrl: String,
    val editions: List<Edition>,
    val id: String,
    val webTitle: String,
    val webUrl: String
)