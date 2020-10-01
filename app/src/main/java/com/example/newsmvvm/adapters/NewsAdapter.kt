package com.example.newsmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.R
import com.example.newsmvvm.network.models.News
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()
{

    private val differCallBack = object : DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News) = newItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: News, newItem: News) = newItem == oldItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent , false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = this.differ.currentList[position]
        holder.itemView.tv_title.text = news.webTitle
        holder.itemView.tv_section.text = news.sectionName
    }

    val differ  = AsyncListDiffer(this, differCallBack)

    private var onItemOnClickListener : ((News) -> Unit)? = null

    fun setOnItemClickListener(listener : (News) -> Unit){
        onItemOnClickListener = listener
    }

    inner class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

}

