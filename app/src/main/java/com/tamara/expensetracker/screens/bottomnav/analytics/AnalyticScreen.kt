package com.tamara.expensetracker.screens.bottomnav.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tamara.expensetracker.components.CategoryBreakdown
import com.tamara.expensetracker.components.DetailsCard
import com.tamara.expensetracker.components.FilterRow
import com.tamara.expensetracker.components.LiveUpdateChart
import com.tamara.expensetracker.models.category.Category
import com.tamara.expensetracker.models.transaction.Transaction
import com.tamara.expensetracker.screens.bottomnav.HomeScreenViewModel
import com.tamara.expensetracker.screens.bottomnav.Param

@Preview(showBackground = true, backgroundColor = 0)
@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
) {
    val categories: List<Category> =
        homeScreenViewModel.categoryList.collectAsState().value
    val transactions: List<Transaction> =
        homeScreenViewModel.transactionList.collectAsState().value
    val categoryValues: List<Category> =
        homeScreenViewModel.getCategoryValues(
            transactions,
            categories,
        )
    val totalIncome: Float =
        homeScreenViewModel.getAvailableBalance(
            param = Param.INCOME,
            transactions
        )
    val totalExpense: Float =
        homeScreenViewModel.getAvailableBalance(
            param = Param.EXPENSE,
            transactions
        )
    val savingCategory: List<Category> =
        categoryValues.filter {
            it.icon == "savings"
        }
    Column(
        modifier = modifier.padding(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp,
        ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Analytics",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Track spending patterns over time",
            fontSize = 16.sp,
            color = Color.White.copy(
                alpha = .75f
            )
        )
        FilterRow(
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                DetailsCard(
                    modifier = Modifier.weight(1f),
                    label = "INCOME",
                    amount = "KES ${"%.1f".format(totalIncome / 1000)}k",
                    textColor = Color.Green,
                )
                Spacer(modifier = Modifier.width(10.dp))
                DetailsCard(
                    modifier = Modifier.weight(1f),
                    label = "EXPENSE",
                    amount = "KES ${"%.1f".format(totalExpense / 1000)}k",
                    textColor = Color.Red,
                )
                Spacer(modifier = Modifier.width(10.dp))
                DetailsCard(
                    modifier = Modifier.weight(1f),
                    label = "SAVED",
                    amount = "${savingCategory.first().value}%",
                    textColor = Color.White,
                )
            }
            LiveUpdateChart(
                modifier = Modifier.padding(
                    top = 20.dp
                ),
                categories = homeScreenViewModel.getCategoryValues(
                    transactions = transactions,
                    categories = categories,
                )
            )
            CategoryBreakdown(
                modifier = Modifier.padding(
                    top = 20.dp
                ),
                categories = homeScreenViewModel.getCategoryValues(
                    transactions = transactions,
                    categories = categories,
                ),
                income = totalIncome
            )
        }
    }
}