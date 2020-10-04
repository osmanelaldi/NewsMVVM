package com.example.newsmvvm.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsmvvm.R
import com.example.newsmvvm.db.NewsDatabase
import com.example.newsmvvm.network.Repository
import com.example.newsmvvm.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_error.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    private var errorDialog : Dialog? = null
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository =  Repository(NewsDatabase(this))
        val viewModelProviderFactory = NewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)


        bn_menu.setupWithNavController(fv_news.findNavController())

    }

    fun showError(message: String?){
        errorDialog = errorDialog ?: Dialog(this)
        message?.let {
            val view = LayoutInflater.from(this).inflate(R.layout.item_error, null)
            view.tv_error.text = message
            errorDialog?.setContentView(view)
            errorDialog?.show()
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.ERROR_DELAY)
                viewModel.dismissError()
            }
            errorDialog
        } ?: kotlin.run {
            errorDialog?.dismiss()
        }

    }

}