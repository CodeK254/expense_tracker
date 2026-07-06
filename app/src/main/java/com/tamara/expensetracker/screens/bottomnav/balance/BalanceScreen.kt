package com.tamara.expensetracker.screens.bottomnav.balance

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tamara.expensetracker.components.ListRecentTransactions
import com.tamara.expensetracker.components.ProfileAvatar
import com.tamara.expensetracker.components.ShowAmountContainers
import com.tamara.expensetracker.components.ShowSpendingSlider
import com.tamara.expensetracker.models.category.Category
import com.tamara.expensetracker.models.transaction.Transaction
import com.tamara.expensetracker.screens.bottomnav.HomeScreenViewModel
import com.tamara.expensetracker.screens.bottomnav.Param

@Composable
fun BalanceScreen(homeScreenViewModel: HomeScreenViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black,
    ) {
        val transactions: List<Transaction>
            = homeScreenViewModel.transactionList.collectAsState().value
        val categories: List<Category>
                = homeScreenViewModel.categoryList.collectAsState().value
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        "Available Balance",
                        fontSize = 25.sp,
                        color = Color.White.copy(alpha = .75f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "KES ${"%,.2f".format(homeScreenViewModel.getAvailableBalance(Param.BALANCE, transactions))}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                ProfileAvatar(radius = 40)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
            ) {
                homeScreenViewModel.getCategoryValues(transactions, categories)
                AnimatedVisibility(
                    visible = !homeScreenViewModel.viewMore()
                ) {
                    Column {
                        ShowAmountContainers(
                            modifier = Modifier
                                .padding(
                                    top = 24.dp
                                ),
                            transactions = transactions
                        )
                        ShowSpendingSlider(
                            modifier = Modifier.padding(
                                top = 20.dp
                            ),
                            usedAmount = homeScreenViewModel.getSpentPercentage(
                                transactions,
                            ) / 100
                        )
                    }
                }
                ListRecentTransactions(
                    modifier = Modifier.padding(
                        top = 20.dp
                    ),
                    viewMore = homeScreenViewModel.viewMore()
                ){
                    homeScreenViewModel.toggleViewMore(
                        !homeScreenViewModel.viewMore()
                    )
                }
            }
        }
    }
}