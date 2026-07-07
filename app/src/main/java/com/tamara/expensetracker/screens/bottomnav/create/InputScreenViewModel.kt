package com.tamara.expensetracker.screens.bottomnav.create

import android.util.Log
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import java.util.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.tamara.expensetracker.data.category.CategoryDataSource
import com.tamara.expensetracker.models.category.Category
import com.tamara.expensetracker.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class InputScreenViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {
    private val inputState = mutableIntStateOf(1)
    private val titleInputState = mutableStateOf("")
    private val descriptionInputState = mutableStateOf("")
    private val amountInput = mutableStateOf("")
    private val showDialog = mutableStateOf(false)
    private val categoryInputState = mutableStateOf(CategoryDataSource().fetchCategories()[0])

    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    private val _showDatePicker = mutableStateOf(false)
    private val _selectedDateText = mutableStateOf("Select date...")

    fun showDatePicker(): Boolean {
        return _showDatePicker.value
    }

    fun selectedDateText(): String {
        return _selectedDateText.value
    }

    fun selectDate(text: String){
        _selectedDateText.value = text
    }

    fun toggleShowPicker(value: Boolean){
        _showDatePicker.value = value
    }
    @OptIn(ExperimentalMaterial3Api::class)
    val datePickerState = DatePickerState(locale = Locale("en_US"), initialSelectedDate = LocalDate.now())

    init {
        clearAllFields()
        viewModelScope.launch(Dispatchers.IO) {
            repository.readAll().distinctUntilChanged()
                .collect { notes ->
                    if(notes.isEmpty()){
                        Log.d("EXPENSES-APP", "Empty List")
                    } else {
                        _categoryList.value = notes
                        if(notes.isNotEmpty()) {
                            setCategory(notes.first())
                        }
                    }
                }
        }
    }

    fun toggleTab(value: Int) {
        inputState.intValue = value
    }

    fun setShowDialog(value: Boolean) {
        showDialog.value = value
    }

    fun setTitle(value: String) {
        titleInputState.value = value
    }

    fun setDescription(value: String) {
        descriptionInputState.value = value
    }

    fun setCategory(value: Category) {
        categoryInputState.value = value
    }

    fun setAmount(value: String) {
        amountInput.value = value
    }

    fun selectedTab(): Int {
        return inputState.intValue
    }

    fun getTitle(): String {
        return titleInputState.value
    }

    fun getDescription(): String {
        return descriptionInputState.value
    }

    fun getCategory(): Category {
        if (inputState.intValue == 0) {
            return _categoryList.value.firstOrNull {
                it.icon == "income"
            } ?: CategoryDataSource().fetchCategories().first { it.icon == "income" }
        }
        return categoryInputState.value
    }

    fun getAmount(): String {
        return amountInput.value
    }

    fun getShowDialog(): Boolean {
        return showDialog.value
    }

    fun clearAllFields() {
        toggleTab(1)
        setTitle("")
        setDescription("")
        setAmount("")
        setShowDialog(false)
    }
}