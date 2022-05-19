package com.example.transactiontracker.ui.view.builder

import com.example.transactiontracker.data.model.TransactionModel

class AddTransaction {

    data class Builder (
        var transactionType: String? = null,
        var transactionDescription: String? = null,
        var transactionValue: Long = 0,
        var date : String? = null) {

        fun type(type : String) = apply { this.transactionType = type }
        fun description(description : String) = apply { this.transactionDescription = description }
        fun value(value : Long) = apply { this.transactionValue = value }
        fun date(date : String) = apply { this.date = date }
        fun build() = TransactionModel(transactionType, transactionDescription, transactionValue, date)
    }
}