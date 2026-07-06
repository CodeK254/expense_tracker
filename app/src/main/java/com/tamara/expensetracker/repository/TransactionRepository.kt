package com.tamara.expensetracker.repository

import com.tamara.expensetracker.data.transaction.TransactionDatabaseDAO
import com.tamara.expensetracker.models.transaction.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDatabaseDAO: TransactionDatabaseDAO
) {
    suspend fun insert(transaction: Transaction) = transactionDatabaseDAO.insertTransaction(transaction)
    suspend fun update(transaction: Transaction) = transactionDatabaseDAO.updateTransaction(transaction)
    suspend fun delete(transaction: Transaction) = transactionDatabaseDAO.deleteTransaction(transaction)
    suspend fun clear() = transactionDatabaseDAO.deleteAll()
    suspend fun readTransaction(id: UUID): Transaction = transactionDatabaseDAO.getTransactionBYID(id)
    fun readAll(): Flow<List<Transaction>> = transactionDatabaseDAO.getTransactions()
}