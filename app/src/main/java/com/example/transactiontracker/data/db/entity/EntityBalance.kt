package com.example.transactiontracker.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.transactiontracker.utils.Constant.Companion.BALANCE_TABLE_NAME

@Entity(tableName = BALANCE_TABLE_NAME)
class EntityBalance (
    @PrimaryKey
    var id : Long = 0,
    @ColumnInfo(name = "expense") var expense : Long?,
    @ColumnInfo(name = "income") var income : Long?,
    @ColumnInfo(name = "balance") var balance : Long?)