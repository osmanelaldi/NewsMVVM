package com.example.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.R
import com.example.newsmvvm.adapters.NewsAdapter
import com.example.newsmvvm.ui.MainActivity
import com.example.newsmvvm.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter : NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = NewsAdapter()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable(getString(R.string.news), it)
            findNavController().navigate(R.id.action_savedNewsFragment_to_webFragment, bundle)
        }
        rv_saved_news.adapter = newsAdapter

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {news->
            newsAdapter.differ.submitList(news)
        })

        val itemTouchHelperCallback = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.START or ItemTouchHelper.END
        ){
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val news = newsAdapter.differ.currentList[position]
                viewModel.deleteNews(news)
                Snackbar.make(view, getString(R.string.news_deleted_successfully), Snackbar.LENGTH_LONG).apply {
                    setAction(getString(R.string.undo)){
                        viewModel.saveNews(news)
                    }
                }.show()
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_saved_news)
        }

    }


}