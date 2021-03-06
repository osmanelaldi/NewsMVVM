package com.example.newsmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.R
import com.example.newsmvvm.network.models.News
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(val differ : DiffUtil.ItemCallback<News> = differCallBack) : PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(differ)
{
    companion object{
         val differCallBack = object : DiffUtil.ItemCallback<News>(){
            override fun areItemsTheSame(oldItem: News, newItem: News) = newItem.id == oldItem.id

            override fun areContentsTheSame(oldItem: News, newItem: News) = newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent , false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        news?.let {
            holder.itemView.apply {
                tv_title.text = news.webTitle
                tv_section.text = news.sectionName
                setOnClickListener { onItemOnClickListener?.let { it(news) } }
            }
        }
    }

    private var onItemOnClickListener : ((News) -> Unit)? = null

    fun setOnItemClickListener(listener : (News) -> Unit){
        onItemOnClickListener = listener
    }

    fun provideItem(position: Int) = this.getItem(position)


    inner class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

}

