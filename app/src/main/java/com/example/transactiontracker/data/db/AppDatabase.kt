package com.example.transactiontracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.transactiontracker.data.db.dao.BalanceDao
import com.example.transactiontracker.data.db.dao.TransactionDao
import com.example.transactiontracker.data.db.entity.EntityBalance
import com.example.transactiontracker.data.db.entity.Transaction
import com.example.transactiontracker.utils.Constant.Companion.DB_NAME

@Database(entities = [Transaction::class, EntityBalance::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao() : TransactionDao
    abstract fun balanceDao() : BalanceDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { appDatabase -> INSTANCE = appDatabase }
            }

        private fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, DB_NAME).build()
    }
}
