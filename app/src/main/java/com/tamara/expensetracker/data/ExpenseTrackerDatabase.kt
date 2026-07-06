package com.tamara.expensetracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tamara.expensetracker.data.category.CategoryDatabaseDAO
import com.tamara.expensetracker.data.transaction.TransactionDatabaseDAO
import com.tamara.expensetracker.models.category.Category
import com.tamara.expensetracker.models.transaction.Transaction

@Database(entities = [Transaction::class, Category::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ExpenseTrackerDatabase: RoomDatabase() {
    abstract fun transactionDAO(): TransactionDatabaseDAO
    abstract fun categoryDAO(): CategoryDatabaseDAO
}
