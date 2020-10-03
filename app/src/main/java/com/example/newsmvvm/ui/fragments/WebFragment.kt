package com.example.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsmvvm.R
import com.example.newsmvvm.network.models.News
import com.example.newsmvvm.ui.MainActivity
import com.example.newsmvvm.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : Fragment(R.layout.fragment_web){

    lateinit var viewModel : NewsViewModel
    val args : WebFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
        val news = args.news
        btn_save.isEnabled = !news.isSaved

        wv_news.apply {
            webViewClient = WebViewClient()
            loadUrl(news.webUrl)
        }

        btn_save.setOnClickListener {
            viewModel.saveNews(news)
            btn_save.isEnabled = false
            Toast.makeText(context,getString(R.string.news_saved_successfully), Toast.LENGTH_LONG).show()
        }

    }


}