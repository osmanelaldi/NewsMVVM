package com.example.newsmvvm.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.newsmvvm.R
import com.example.newsmvvm.network.models.ErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.item_error.view.*
import java.lang.Exception

object ErrorHandler {

    var UNKNOWN_ERROR = "UNKNOWN_ERROR"

    fun <T : Any> handleError(response : NetworkResponse<T, ErrorResponse>) : String?{
        when(response){
            is NetworkResponse.NetworkError -> return response.error.cause?.message
            is NetworkResponse.ServerError -> return  "${response.body?.message ?: ""} + ${response.code}"
            is NetworkResponse.UnknownError -> return response.error.cause?.message
        }
        return UNKNOWN_ERROR
    }
}