package com.example.transactiontracker.ui.main.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.transactiontracker.data.model.TransactionModel

class TransactionDiffCallback(oldList: ArrayList<TransactionModel>,
                              newList: ArrayList<TransactionModel>) : DiffUtil.Callback() {

    private var oldTransactionList = ArrayList<TransactionModel>()
    private var newTransactionList = ArrayList<TransactionModel>()

    init {
        oldTransactionList = oldList
        newTransactionList = newList
    }

    override fun getOldListSize(): Int {
        return oldTransactionList.size
    }

    override fun getNewListSize(): Int {
        return newTransactionList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTransactionList[oldItemPosition].transactionDescription
            .equals(newTransactionList[newItemPosition].transactionDescription)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTransactionData = oldTransactionList[oldItemPosition]
        val newTransactionData = newTransactionList[newItemPosition]

        return oldTransactionData.transactionDescription
            .equals(newTransactionData.transactionDescription, true)
    }
}