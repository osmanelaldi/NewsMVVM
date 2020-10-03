package com.example.newsmvvm.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName = "news"
)
data class News(
    val apiUrl: String,
    @PrimaryKey (autoGenerate = false) val id: String,
    val isHosted: Boolean,
    val pillarId: String,
    val pillarName: String,
    val sectionId: String,
    val sectionName: String,
    val type: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val isSaved : Boolean = false
) : Serializable