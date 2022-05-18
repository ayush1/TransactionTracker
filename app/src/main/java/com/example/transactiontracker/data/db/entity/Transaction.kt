package com.example.transactiontracker.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.transactiontracker.utils.Constant.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class Transaction (
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    @ColumnInfo(name = "transactionType") var transactionType : String?,
    @ColumnInfo(name = "transactionDescription") var transactionDescription : String?,
    @ColumnInfo(name = "transactionValue") var transactionValue : String?,
    @ColumnInfo(name = "transactionDate") var transactionDate : String?)