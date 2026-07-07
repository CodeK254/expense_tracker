package com.tamara.expensetracker.screens.bottomnav

import android.content.Context
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
import java.io.BufferedReader
import java.util.UUID
import javax.inject.Inject
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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
    private val _inputState = mutableStateOf("")
    private val _viewMore = mutableStateOf(false)
    private val _isBarGraph = mutableStateOf(false)
    private val _isMonthly = mutableStateOf(true)
    fun viewMore(): Boolean { return _viewMore.value }
    fun isBarGraph(): Boolean { return _isBarGraph.value }
    fun isMonthly(): Boolean { return _isMonthly.value }
    fun inputValue(): String { return _inputState.value }
    fun onInputChanges(value: String) { _inputState.value = value }
    val categoryList = _categoryList.asStateFlow()

    fun getExpandedTile(): UUID { return _expandedTile.value }

    private val _transactionList = MutableStateFlow<List<Transaction>>(emptyList())
    val transactionList = _transactionList.asStateFlow()

    fun getFilteredTransaction(
        transactions: List<Transaction>
    ): List<Transaction>{
        return transactions.filter{ transaction ->
            transaction.title.contains(
                other = _inputState.value, ignoreCase = true
            )
        }
    }

    fun toggleViewMore(value: Boolean) {
        _viewMore.value = value
    }

    fun toggleBarGraph(value: Boolean) {
        _isBarGraph.value = value
    }

    fun toggleIsMonthly(value: Boolean) {
        _isMonthly.value = value
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

    fun getWeekCount(transaction: Transaction): Int {
        // 1. Get the current date dynamically
        val today = LocalDate.now()
        if(transaction.createdAt.month.value != today.month.value){
            return 0
        }

        // 2. Define 25th of last month and 25th of this month
        val lastMonth25th = if(transaction.createdAt.dayOfMonth < 25){
            today.minusMonths(1).withDayOfMonth(25)
        } else {
            today.withDayOfMonth(25)
        }

        val transactionDate = today.withDayOfMonth(transaction.createdAt.dayOfMonth)

        // 3. Calculate full weeks between them
        val fullWeeks =
            ChronoUnit.WEEKS.between(lastMonth25th, transactionDate) + 1

        Log.d("EXPENSE-APP", "Transaction ${transaction.title} of date ${transaction.createdAt} of week => $transactionDate - $lastMonth25th = $fullWeeks")
        return fullWeeks.toInt()
    }

    fun getMonthCount(transaction: Transaction): Int {
        return transaction.createdAt.month.value
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

    private val selectedState = mutableIntStateOf(value = 0)

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