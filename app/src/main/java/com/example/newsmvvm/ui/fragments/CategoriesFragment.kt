package com.example.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsmvvm.R
import com.example.newsmvvm.adapters.CategoriesAdapter
import com.example.newsmvvm.ui.MainActivity
import com.example.newsmvvm.ui.NewsViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    lateinit var viewModel : NewsViewModel
    lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        categoriesAdapter = CategoriesAdapter()

        categoriesAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putString(getString(R.string.searchTerm), it.webTitle)
            findNavController().navigate(R.id.action_categoriesFragment_to_searchNewsFragment, bundle)
        }

        rv_categories.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        viewModel.categories.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is NetworkResponse.Success->{
                    categoriesAdapter.differ.submitList(response.body.response.results)
                }
            }
        })
    }
}