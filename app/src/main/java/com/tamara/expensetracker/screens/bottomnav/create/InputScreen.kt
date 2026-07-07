package com.tamara.expensetracker.screens.bottomnav.create

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamara.expensetracker.components.AmountInputContainer
import com.tamara.expensetracker.components.CategoryDropDown
import com.tamara.expensetracker.components.DialogWithTextField
import com.tamara.expensetracker.components.InputTabRow
import com.tamara.expensetracker.components.InputTextField
import com.tamara.expensetracker.components.SaveInputButton
import com.tamara.expensetracker.models.transaction.Transaction
import com.tamara.expensetracker.screens.bottomnav.HomeScreenViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//@Preview(showBackground = true, backgroundColor = 0)
@Composable
fun InputScreen(
    inputViewModel: InputScreenViewModel = hiltViewModel(),
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp,
        ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Add Transaction",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Record a new income or expense",
            fontSize = 16.sp,
            color = Color.White.copy(
                alpha = .75f
            )
        )
        Column(
            modifier = Modifier.padding(
                vertical = 20.dp,
            ).verticalScroll(rememberScrollState())
        ) {
            InputTabRow(
                selectedIndex = inputViewModel.selectedTab()
            ){
                inputViewModel.toggleTab(it)
            }
            Column {
                AmountInputContainer(
                    modifier = Modifier.padding(
                        top = 20.dp
                    ),
                    isIncome = inputViewModel.selectedTab() == 0,
                    amount = if (inputViewModel.getAmount() == "") "0.00" else inputViewModel.getAmount(),
                ){
                    inputViewModel.setShowDialog(true)
                }
                TransactionDatePicker(
                    onDateSelected = {

                    },
                    inputViewModel = inputViewModel
                )
            }
            DialogWithTextField(
                value = inputViewModel.getAmount(),
                showDialog = inputViewModel.getShowDialog(),
                onDismiss = {inputViewModel.setShowDialog(false)},
            ){
                inputViewModel.setAmount(it)
            }
            InputTextField(
                modifier = Modifier.padding(
                    top = 20.dp
                ),
                value = inputViewModel.getTitle(),
                label = "TITLE",
                hint = "e.g. Weekly groceries",
                keyboardCapitalization = KeyboardCapitalization.Words,
            ){
                inputViewModel.setTitle(value = it)
            }
            InputTextField(
                modifier = Modifier.padding(
                    top = 20.dp
                ),
                value = inputViewModel.getDescription(),
                label = "DESCRIPTION",
                hint = "Optional Description...",
                maxLines = 3,
            ){
                inputViewModel.setDescription(value = it)
            }
            CategoryDropDown(
//                modifier = Modifier.padding(
//                    top = 20.dp
//                ),
                label = "CATEGORY",
                categories = homeScreenViewModel.categoryList.collectAsState().value,
                value = inputViewModel.getCategory(),
            ){
                inputViewModel.setCategory(value = it)
            }
            SaveInputButton(
                modifier = Modifier.padding(
                    top = 20.dp
                ),
                label = "Save ${if (inputViewModel.selectedTab() == 0) "Income" else "Expense"}"
            ) {
                homeScreenViewModel.addNewTransaction(Transaction(
                    type = if (inputViewModel.selectedTab() == 0) "Income" else "Expense",
                    categoryId = inputViewModel.getCategory().id,
                    title = inputViewModel.getTitle(),
                    description = inputViewModel.getDescription(),
                    amount = inputViewModel.getAmount().toFloat(),
                    createdAt = Instant.ofEpochMilli(inputViewModel.datePickerState.selectedDateMillis as Long)
                        .atZone(ZoneId.systemDefault()) // Or ZoneId.of("UTC") depending on your needs
                        .toLocalDate()
                )){
                    inputViewModel.clearAllFields()
                    homeScreenViewModel.toggleSelection(1)
                }
            }
        }
    }
}

@Composable
fun TransactionDatePicker(
    onDateSelected: (LocalDate) -> Unit,
    inputViewModel: InputScreenViewModel,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Trigger Button
        OutlinedButton(
            onClick = { inputViewModel.toggleShowPicker(true) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = inputViewModel.selectedDateText())
        }

        // 4. Show the Dialog overlay when state is true
        if (inputViewModel.showDatePicker()) {
            DatePickerDialog(
                onDismissRequest = { inputViewModel.toggleShowPicker(false) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // Extract milliseconds from state
                            val selectedMillis =
                                inputViewModel.datePickerState.selectedDateMillis
                            if (selectedMillis != null) {
                                // Convert Milliseconds -> Instant -> LocalDate
                                val localDate = Instant.ofEpochMilli(selectedMillis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()

                                // Format text for button display
                                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                inputViewModel.selectDate(localDate.format(formatter))

                                // Pass the selected LocalDate back to your parent view/ViewModel
                                onDateSelected(localDate)
                            }
                            inputViewModel.toggleShowPicker(false)
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { inputViewModel.toggleShowPicker(false) }) {
                        Text("Cancel")
                    }
                }
            ) {
                // Inside the dialog layout, place the actual picker graphic wrapper
                DatePicker(state = inputViewModel.datePickerState)
            }
        }
    }
}