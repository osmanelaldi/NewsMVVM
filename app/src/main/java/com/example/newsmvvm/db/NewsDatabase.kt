package com.example.newsmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsmvvm.network.models.News

@Database(
    entities = [News::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase(){

    abstract fun getNewsDao(): NewsDao

    companion object{
        @Volatile
        private var instance : NewsDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "news_db.db").build()

    }

}