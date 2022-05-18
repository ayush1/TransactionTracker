package com.example.transactiontracker.data.repository

import android.app.Application
import com.example.transactiontracker.data.db.AppDatabase
import com.example.transactiontracker.data.db.entity.EntityBalance
import com.example.transactiontracker.data.db.entity.Transaction
import com.example.transactiontracker.data.model.BalanceModel
import com.example.transactiontracker.data.model.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainRepository(context: Application) {

    private val transactionDao by lazy {
        AppDatabase.getDatabase(context).transactionDao()
    }

    private val balanceDao by lazy {
        AppDatabase.getDatabase(context).balanceDao()
    }

    fun saveTransactionInDB(transaction: TransactionModel) {
        val transactionDaoModel = convertToDaoModel(transaction)
        runBlocking(Dispatchers.Default){
            withContext(Dispatchers.Default) {
                transactionDao.insert(transactionDaoModel)
            }
            return@runBlocking
        }
    }

    private fun convertToDaoModel(transaction: TransactionModel) : Transaction {
        return Transaction(transactionType = transaction.transactionType,
            transactionDescription = transaction.transactionDescription,
            transactionValue = transaction.transactionValue.toString(), transactionDate = transaction.date)
    }

    fun getTransactionFromDb() : ArrayList<Transaction> = runBlocking(Dispatchers.Default) {
        val result = withContext(Dispatchers.Default) {
            transactionDao.getAll()
        }
        return@runBlocking result as ArrayList<Transaction>
    }

    fun updateBalanceInDb(balanceModel: BalanceModel) {
        runBlocking(Dispatchers.Default){
            withContext(Dispatchers.Default) {
                balanceDao.updateBalance(
                    EntityBalance(expense = balanceModel.expense,
                        income = balanceModel.income, balance = balanceModel.balance)
                )
            }
            return@runBlocking
        }
    }

    fun getBalanceFromDB() = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            val entityBalance = balanceDao.getBalance()
            entityBalance?.let {
                BalanceModel(entityBalance.expense!!, entityBalance.income!!, entityBalance.balance!!)
            }
        }
    }

    fun deleteTransactionFromDb(transaction: Transaction) {
        runBlocking(Dispatchers.Default){
            withContext(Dispatchers.Default) {
                transactionDao.delete(transaction.transactionDescription, transaction.transactionType, transaction.transactionValue)
            }
            return@runBlocking
        }
    }
}