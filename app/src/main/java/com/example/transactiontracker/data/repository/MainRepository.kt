package com.example.transactiontracker.data.repository

import com.example.transactiontracker.data.db.dao.BalanceDao
import com.example.transactiontracker.data.db.dao.TransactionDao
import com.example.transactiontracker.data.db.entity.EntityBalance
import com.example.transactiontracker.data.db.entity.Transaction
import com.example.transactiontracker.data.model.BalanceModel
import com.example.transactiontracker.data.model.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val transactionDao : TransactionDao,
    private val balanceDao : BalanceDao): IMainRepository {

    override fun saveTransactionInDB(transaction: TransactionModel) {
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

    override fun getTransactionFromDb() : ArrayList<Transaction> = runBlocking(Dispatchers.Default) {
        val result = withContext(Dispatchers.Default) {
            transactionDao.getAll()
        }
        return@runBlocking result as ArrayList<Transaction>
    }

    override fun updateBalanceInDb(balanceModel: BalanceModel) {
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

    override fun deleteTransactionFromDb(transaction: Transaction) {
        runBlocking(Dispatchers.Default){
            withContext(Dispatchers.Default) {
                transactionDao.delete(transaction.transactionDescription, transaction.transactionType, transaction.transactionValue)
            }
            return@runBlocking
        }
    }
}