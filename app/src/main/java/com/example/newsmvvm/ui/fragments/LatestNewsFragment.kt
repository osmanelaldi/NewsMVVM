package com.example.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newsmvvm.R
import com.example.newsmvvm.adapters.NewsAdapter
import com.example.newsmvvm.ui.MainActivity
import com.example.newsmvvm.ui.NewsViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.fragment_latest_news.*

class LatestNewsFragment : Fragment(R.layout.fragment_latest_news) {

    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter : NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = NewsAdapter()
        rv_latest_news.adapter = newsAdapter

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("news",it)
            findNavController().navigate(R.id.action_latestNewsFragment_to_webFragment, bundle)
        }

        viewModel.latestNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is NetworkResponse.Success -> {
                    newsAdapter.differ.submitList(response.body.response.news)
                }
            }
        })


    }

}