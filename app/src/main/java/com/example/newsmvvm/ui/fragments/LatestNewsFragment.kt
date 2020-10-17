package com.example.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.newsmvvm.R
import com.example.newsmvvm.adapters.NewsAdapter
import com.example.newsmvvm.ui.MainActivity
import com.example.newsmvvm.ui.NewsViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.fragment_latest_news.*
import kotlinx.android.synthetic.main.item_news.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LatestNewsFragment : Fragment(R.layout.fragment_latest_news) {

    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter : NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        newsAdapter = NewsAdapter().apply {
            setOnItemClickListener {
                val bundle = Bundle()
                bundle.putSerializable(getString(R.string.news), it)
                findNavController().navigate(R.id.action_latestNewsFragment_to_webFragment, bundle)
            }
            addLoadStateListener {loadState->
                val errorState = when{
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {loadStateError->
                    viewModel.postError(loadStateError.error)
                }
            }
        }
        rv_latest_news.adapter = newsAdapter

        lifecycleScope.launch {
            viewModel.latestNews.collectLatest {data->
                newsAdapter.submitData(data)
            }
        }

        viewModel.error.observe(viewLifecycleOwner, Observer {message->
            (activity as MainActivity).showError(message)
        })
        

    }

}