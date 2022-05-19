package com.example.transactiontracker.data.repository

import com.example.transactiontracker.data.db.entity.Transaction
import com.example.transactiontracker.data.model.BalanceModel
import com.example.transactiontracker.data.model.TransactionModel

interface IMainRepository {

    fun saveTransactionInDB(transaction: TransactionModel)

    fun getTransactionFromDb() : ArrayList<Transaction>

    fun updateBalanceInDb(balanceModel: BalanceModel)

    fun deleteTransactionFromDb(transaction: Transaction)

}