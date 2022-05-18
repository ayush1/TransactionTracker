package com.example.transactiontracker.data.db.dao

import androidx.room.*
import com.example.transactiontracker.data.db.entity.Transaction
import com.example.transactiontracker.utils.Constant.Companion.TABLE_NAME

@Dao
interface TransactionDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll() : List<Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction : Transaction)

    @Query("DELETE FROM $TABLE_NAME WHERE transactionType = :type AND transactionDescription = :description AND transactionValue = :value" )
    fun delete(description : String?, type : String?, value : String?)

}