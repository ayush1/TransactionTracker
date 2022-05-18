package com.example.transactiontracker.data.model

import java.io.Serializable

data class TransactionModel(
    var transactionType : String?,
    var transactionDescription : String?,
    var transactionValue : Long,
    var date : String? = null) : Serializable

data class BalanceModel(
    var expense : Long = 0,
    var income : Long = 0,
    var balance : Long = 0)
