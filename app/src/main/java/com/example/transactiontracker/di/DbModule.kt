package com.example.transactiontracker.di

import android.content.Context
import androidx.room.Room
import com.example.transactiontracker.data.db.AppDatabase
import com.example.transactiontracker.data.db.dao.BalanceDao
import com.example.transactiontracker.data.db.dao.TransactionDao
import com.example.transactiontracker.utils.Constant.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    private var INSTANCE: AppDatabase? = null

    @Provides
    fun provideRepositoryDao(database : AppDatabase) : TransactionDao = database.transactionDao()

    @Provides
    fun provideBalanceDao(database : AppDatabase) : BalanceDao = database.balanceDao()

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
        INSTANCE ?: provideDatabase(context).also { appDatabase -> INSTANCE = appDatabase }
    }

    private fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(context,
        AppDatabase::class.java, DB_NAME).build()

}