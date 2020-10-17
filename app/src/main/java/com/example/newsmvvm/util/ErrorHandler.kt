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
    var UNKNOWN_THROWABLE_ERROR = Throwable(UNKNOWN_ERROR)

    fun <T : Any> handleError(response : NetworkResponse<T, ErrorResponse>) : String{
        when(response){
            is NetworkResponse.NetworkError -> return response.error.cause?.message ?: UNKNOWN_ERROR
            is NetworkResponse.ServerError -> return  "${response.body?.message ?: ""} + ${response.code}"
            is NetworkResponse.UnknownError -> return response.error.cause?.message ?: UNKNOWN_ERROR
        }
        return UNKNOWN_ERROR
    }

    fun  <T : Any> provideError(response : NetworkResponse<T, ErrorResponse>) : Throwable{
        when(response){
            is NetworkResponse.NetworkError -> return response.error.cause ?: UNKNOWN_THROWABLE_ERROR
            is NetworkResponse.ServerError -> return  Throwable("${response.body?.message ?: ""} + ${response.code}")
            is NetworkResponse.UnknownError -> return response.error.cause ?: UNKNOWN_THROWABLE_ERROR
        }
        return UNKNOWN_THROWABLE_ERROR
    }
}