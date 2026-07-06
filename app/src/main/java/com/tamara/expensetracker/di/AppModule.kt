package com.tamara.expensetracker.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tamara.expensetracker.data.ExpenseTrackerDatabase
import com.tamara.expensetracker.data.category.CategoryDatabaseDAO
import com.tamara.expensetracker.data.category.CategoryDataSource
import com.tamara.expensetracker.data.transaction.TransactionDatabaseDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideExpenseTrackerDatabase(
        @ApplicationContext context: Context,
    ): ExpenseTrackerDatabase {
        lateinit var instance: ExpenseTrackerDatabase
        
        val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    CategoryDataSource().fetchCategories().forEach { category ->
                        instance.categoryDAO().insertCategory(category)
                    }
                }
            }
        }

        instance = Room.databaseBuilder(
            context = context,
            klass = ExpenseTrackerDatabase::class.java,
            name = "expense_tracker_database",
        ).addCallback(callback)
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()

        return instance
    }

    @Singleton
    @Provides
    fun provideCategoriesDAO(expenseTrackerDatabase: ExpenseTrackerDatabase): CategoryDatabaseDAO =
        expenseTrackerDatabase.categoryDAO()

    @Singleton
    @Provides
    fun provideTransactionsDAO(expenseTrackerDatabase: ExpenseTrackerDatabase): TransactionDatabaseDAO =
        expenseTrackerDatabase.transactionDAO()
}
