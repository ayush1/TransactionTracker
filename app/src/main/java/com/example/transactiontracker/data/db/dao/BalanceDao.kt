package com.example.transactiontracker.data.db.dao

import androidx.room.*
import com.example.transactiontracker.data.db.entity.EntityBalance
import com.example.transactiontracker.utils.Constant.Companion.BALANCE_TABLE_NAME

@Dao
interface BalanceDao {

    @Query("SELECT * FROM $BALANCE_TABLE_NAME")
    fun getBalance() : EntityBalance?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateBalance(balance: EntityBalance)

}