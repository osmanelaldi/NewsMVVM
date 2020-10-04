package com.example.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsmvvm.R
import com.example.newsmvvm.adapters.NewsAdapter
import com.example.newsmvvm.ui.MainActivity
import com.example.newsmvvm.ui.NewsViewModel
import com.example.newsmvvm.util.Constants
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

     lateinit var viewModel : NewsViewModel
     lateinit var newsAdapter: NewsAdapter
     var job : Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = NewsAdapter()
        rv_search_news.adapter = newsAdapter

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("news",it)
            findNavController().navigate(R.id.action_searchNewsFragment_to_webFragment, bundle)
        }


        et_search_news.addTextChangedListener {editable->
            job?.cancel()
            job = MainScope().launch {
               delay(Constants.SEARCH_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }else{
                        viewModel.clearSearch()
                    }
                }
            }
        }

        arguments?.get(getString(R.string.searchTerm))?.let {
            viewModel.clearSearch()
            et_search_news.setText(it as String)
        }

        viewModel.searchedNews.observe(viewLifecycleOwner, Observer { wrapper->
            newsAdapter.differ.submitList(wrapper.response.news)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {message->
            (activity as MainActivity).showError(message)
        })
    }


}