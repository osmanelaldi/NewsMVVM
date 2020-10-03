package com.example.newsmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.R
import com.example.newsmvvm.network.models.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>()
{

    private val differCallBack = object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category) = newItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) = newItem == oldItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent , false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = this.differ.currentList[position]
        holder.itemView.apply {
            tv_category.text = category.webTitle
            setOnClickListener { onItemOnClickListener?.let { it(category) } }
        }
    }

    val differ  = AsyncListDiffer(this, differCallBack)

    private var onItemOnClickListener : ((Category) -> Unit)? = null

    fun setOnItemClickListener(listener : (Category) -> Unit){
        onItemOnClickListener = listener
    }

    inner class CategoriesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

}

