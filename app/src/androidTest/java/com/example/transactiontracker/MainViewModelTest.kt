package com.example.transactiontracker

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.transactiontracker.data.model.TransactionModel
import com.example.transactiontracker.ui.main.viewmodel.MainViewModel
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    fun testBalanceCalculate() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val viewModel = MainViewModel(context as Application)

        val transactionModel = TransactionModel("expenses", "abc", 30, "2022-05-15")
    }


}