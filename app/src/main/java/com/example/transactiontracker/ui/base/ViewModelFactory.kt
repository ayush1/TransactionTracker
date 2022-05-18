package com.example.transactiontracker.ui.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.transactiontracker.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val context : Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(context) as T
        throw IllegalArgumentException("Unknown class name")
    }
}
