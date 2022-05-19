package com.example.transactiontracker.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.transactiontracker.data.db.entity.Transaction
import com.example.transactiontracker.data.model.BalanceModel
import com.example.transactiontracker.data.model.TransactionModel
import com.example.transactiontracker.data.repository.MainRepository
import com.example.transactiontracker.ui.view.builder.AddTransaction
import com.example.transactiontracker.utils.Constant.Companion.SPINNER_EXPENSE
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : MainRepository) : ViewModel() {

    private val transactionMapping : HashMap<String, ArrayList<TransactionModel>> = HashMap()
    private val allTransactionList : ArrayList<TransactionModel> = ArrayList()
    private var currentDate : String
    private var expense : Long = 0
    private var income : Long = 0
    private var balance : Long = 0

    var transactionLiveData : MutableLiveData<ArrayList<TransactionModel>> = MutableLiveData()
    var balanceLiveData : MutableLiveData<BalanceModel> = MutableLiveData()

    init {
        val calendar: Calendar = Calendar.getInstance()
        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time)
    }

    fun addAndSaveTransaction(transactionType : String, transactionDescription : String, transactionValue : Long) {
        val transactionList : ArrayList<TransactionModel> = ArrayList()

        if(null != transactionMapping[currentDate] && transactionMapping[currentDate]!!.isNotEmpty())
            transactionList.addAll(transactionMapping[currentDate]!!)
        else {
            val transaction = AddTransaction.Builder()
                .date(currentDate)
                .build()

            transactionList.add(transaction)
            allTransactionList.add(transaction)
            saveTransaction(transaction)
        }

        val transactionModel = prepareTransactionModel(transactionType, transactionDescription, transactionValue)
        transactionList.add(transactionModel)
        transactionMapping[currentDate] = transactionList
        saveTransaction(transactionModel)
        allTransactionList.add(transactionModel)

        updateList()
        updateBalance(transactionModel)
    }

    private fun saveTransaction(transaction: TransactionModel) {
        repository.saveTransactionInDB(transaction)
    }

    private fun updateBalance(transactionModel: TransactionModel, toDelete : Boolean = false) {
        calculateBalance(transactionModel, toDelete)

        val balanceModel = BalanceModel(expense, income, balance)
        balanceLiveData.value = balanceModel

        repository.updateBalanceInDb(balanceModel)
     }

    private fun calculateBalance(transactionModel: TransactionModel, isDelete : Boolean = false) {
        if(transactionModel.transactionType.equals(SPINNER_EXPENSE))
            if(!isDelete) this.expense += transactionModel.transactionValue else this.expense -= transactionModel.transactionValue
        else
            if(!isDelete) this.income += transactionModel.transactionValue else this.income -= transactionModel.transactionValue

        this.balance = income - expense
    }

    private fun updateList() {
        transactionLiveData.value = allTransactionList
    }

    private fun prepareTransactionModel(transactionType : String, transactionDescription : String,
                                        transactionValue : Long) : TransactionModel {
        return AddTransaction.Builder()
                .type(transactionType)
                .description(transactionDescription)
                .value(transactionValue)
                .build()
    }

    fun getTransaction() {
        val transactionList = repository.getTransactionFromDb()
        if(transactionList.size > 0) {
            val transactionModelList = convertDaoToModel(transactionList)
            transactionLiveData.value = transactionModelList
            allTransactionList.addAll(transactionModelList)
        }
    }

    private fun convertDaoToModel(transactionList: ArrayList<Transaction>) : ArrayList<TransactionModel> {
        lateinit var transactionModelList : ArrayList<TransactionModel>
        val tempTransactionList : ArrayList<TransactionModel> = ArrayList()

        transactionList.forEachIndexed { index, transaction ->
            if(transaction.transactionDate != null) {
                transactionModelList = ArrayList()
                transactionMapping[transaction.transactionDate!!] = transactionModelList
            }

            val model = TransactionModel(transaction.transactionType, transaction.transactionDescription,
                transaction.transactionValue?.toLong()!!, transaction.transactionDate)

            transactionModelList.add(model)
            tempTransactionList.add(model)
        }
        return tempTransactionList
    }

    fun getBalanceInfo() {
        val balanceModel = repository.getBalanceFromDB()
        if(balanceModel != null) {
            balanceLiveData.value = balanceModel
            income = balanceModel.income
            expense = balanceModel.expense
            balance = balanceModel.balance
        }
    }

    fun removeTransaction(pos: Int) {
        val transactionModel = allTransactionList[pos]

        repository.deleteTransactionFromDb(Transaction(transactionType = transactionModel.transactionType,
            transactionDescription = transactionModel.transactionDescription, transactionValue = transactionModel.transactionValue.toString(),
            transactionDate = transactionModel.date))

        allTransactionList.remove(transactionModel)
        transactionLiveData.value = allTransactionList

        updateBalance(transactionModel, true)
    }

}