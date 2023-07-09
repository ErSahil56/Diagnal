package com.example.myapplication.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(
    private val context: Context
) : ViewModelProvider.NewInstanceFactory(

) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(context) as T
    }
}

