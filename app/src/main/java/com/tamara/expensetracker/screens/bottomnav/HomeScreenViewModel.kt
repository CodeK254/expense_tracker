package com.tamara.expensetracker.screens.bottomnav

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamara.expensetracker.data.category.CategoryDataSource
import com.tamara.expensetracker.models.category.Category
import com.tamara.expensetracker.models.transaction.Transaction
import com.tamara.expensetracker.repository.CategoryRepository
import com.tamara.expensetracker.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

enum class Param {
    BALANCE, INCOME, EXPENSE
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: CategoryRepository,
    private val transactionRepository: TransactionRepository
): ViewModel() {
    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    private val _expandedTile = mutableStateOf(UUID.randomUUID())
    private val _viewMore = mutableStateOf(false)
    fun viewMore(): Boolean { return _viewMore.value }
    val categoryList = _categoryList.asStateFlow()

    fun getExpandedTile(): UUID { return _expandedTile.value }

    private val _transactionList = MutableStateFlow<List<Transaction>>(emptyList())
    val transactionList = _transactionList.asStateFlow()

    fun toggleViewMore(value: Boolean) {
        _viewMore.value = value
    }

    fun getAvailableBalance(param: Param, transactions: List<Transaction>): Float {
        var availableBalance = 0f
        var totalIncome = 0f
        var totalExpense = 0f
        transactions.forEach { transaction ->
            if(transaction.type.lowercase() == "income"){
                availableBalance += transaction.amount
                totalIncome += transaction.amount
            } else {
                availableBalance -= transaction.amount
                totalExpense -= transaction.amount
            }
        }
        return when (param) {
            Param.BALANCE -> {
                availableBalance
            }
            Param.INCOME -> {
                totalIncome
            }
            else -> {
                totalExpense
            }
        }
    }

    fun getSpentPercentage(transactions: List<Transaction>): Float {
        val totalIncome = getAvailableBalance(
            Param.INCOME,
            transactions
        )

        val balance = getAvailableBalance(
            Param.BALANCE,
            transactions
        )

        return 100 - ((balance / totalIncome) * 100)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.readAll().distinctUntilChanged().collect { categories ->
                if(categories.isEmpty()){
                    Log.d("EXPENSES-APP", "Empty Category List")
                } else {
                    _categoryList.value = categories
                }
                
                // Sync new categories from DataSource if they don't exist in the database
                val defaultCategories = CategoryDataSource().fetchCategories()
                defaultCategories.forEach { defaultCat ->
                    if (categories.none { it.label == defaultCat.label }) {
                        repository.insert(defaultCat)
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.readAll().distinctUntilChanged().collect { transactions ->
                if(transactions.isEmpty()){
                    Log.d("EXPENSES-APP", "Empty Transaction List")
                } else {
                    _transactionList.value = transactions
                }
            }
        }
    }

    fun addNewTransaction(
        transaction: Transaction,
        onComplete: () -> Unit,
    ) {
        viewModelScope.launch {
            transactionRepository.insert(transaction)
        }
        onComplete.invoke()
    }

    fun expandTile(transaction: Transaction){
        Log.d("EXPENSE-APP", "expandTile: ${transaction.id}")
        _expandedTile.value = transaction.id
    }

    fun clearExpandedTile(){
        _expandedTile.value = UUID.randomUUID()
    }

    private val selectedState = mutableIntStateOf(value = 1)

    fun toggleSelection(value: Int) {
        selectedState.intValue = value
        Log.d("EXPENSE-APP", "expandTile: $_expandedTile")
        clearExpandedTile()
        toggleViewMore(false)
    }

    fun selectedIndex(): Int {
        return selectedState.intValue
    }

    fun getCategoryValues(
        transactions: List<Transaction>,
        categories: List<Category>
    ): List<Category>{
        for(category in categories) {
            category.value = 0f
        }
        val categoryModels = mutableListOf<Category>();
        for(category in categories) {
            val categoryTransactions = transactions.filter { trans ->
                trans.categoryId == category.id
            }
            for (catTran in categoryTransactions) {
                category.value += catTran.amount
            }
            categoryModels.add(category)
        }

        return categoryModels.filter {
            it.icon != "income"
        }
    }
}