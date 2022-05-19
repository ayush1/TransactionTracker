package com.example.transactiontracker

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.transactiontracker.data.db.AppDatabase
import com.example.transactiontracker.data.db.dao.BalanceDao
import com.example.transactiontracker.data.db.dao.TransactionDao
import com.example.transactiontracker.data.db.entity.EntityBalance
import com.example.transactiontracker.data.db.entity.Transaction
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest : TestCase() {

    private lateinit var db : AppDatabase
    private lateinit var transactionDao : TransactionDao
    private lateinit var balanceDao : BalanceDao

    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        transactionDao = db.transactionDao()
        balanceDao = db.balanceDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadTransaction(): Unit = runBlocking {
        val transaction = Transaction(0, "expenses",
            "coffee","$20", "2022-05-15")
        transactionDao.insert(transaction)
        val transactions = transactionDao.getAll()
        assertThat(transactions.contains(transaction))
    }

    @Test
    fun writeAndReadBalance(): Unit = runBlocking {
        val entityBalance = EntityBalance(0, 30, 100,80)
        balanceDao.updateBalance(entityBalance)
        val balance = balanceDao.getBalance()
        assertThat(balance?.expense?.equals(entityBalance.expense))
    }

    @Test
    fun deleteTransaction() : Unit = runBlocking {
        val transaction = Transaction(0, "expenses",
            "coffee","$20", "2022-05-15")
        transactionDao.insert(transaction)
        val transactions = transactionDao.getAll()
        assertThat(transactions.contains(transaction))
        transactionDao.delete("expenses", "coffee", "$20")
        val latestTransactions = transactionDao.getAll()
        assertThat(!transactions.contains(transaction))
    }
}