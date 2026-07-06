package com.tamara.expensetracker.data.transaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tamara.expensetracker.models.transaction.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TransactionDatabaseDAO {
    // READ
    @Query("SELECT * FROM transactions_table ORDER BY created_at DESC")
    fun getTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions_table WHERE id =:id ORDER BY created_at DESC")
    suspend fun getTransactionBYID(id: UUID): Transaction

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    // UPDATE
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)

    // DELETE
    @Query("DELETE FROM transactions_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}
