package com.tamara.expensetracker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tamara.expensetracker.models.category.Category
import com.tamara.expensetracker.models.transaction.Transaction
import com.tamara.expensetracker.screens.bottomnav.HomeScreenViewModel
import com.tamara.expensetracker.utils.TransactionIconRegistry
import java.time.format.DateTimeFormatter

@Composable
fun ProfileAvatar(
    radius: Int
){
    Card(
        modifier = Modifier
            .size(radius.dp)
            .padding(),
        shape = CircleShape.copy(
            all = CornerSize(radius.dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(red = 255, green = 100, blue = 0).copy(
                alpha = .35f
            )
        ),
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .size((radius - 5).dp)
                .padding()
                .clip(
                    shape = CircleShape.copy(
                        CornerSize(97.dp)
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            Card(
                modifier = Modifier
                    .size((radius - 5).dp)
                    .padding(),
                shape = CircleShape.copy(
                    all = CornerSize((radius - 5).dp)
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(red = 255, green = 100, blue = 0).copy(
                        alpha = .85f
                    )
                ),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "TK",
                        fontSize = (radius / 2).sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun AmountCard(
    label: String,
    filter: String,
    amount: Float,
    color: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
){
    Card(
        modifier = modifier.border(
            width = 2.dp,
            color = color.copy(
                alpha = .35f
            ),
            shape = CircleShape.copy(
                CornerSize(16.dp)
            ),
        ).fillMaxHeight(),
        shape = CircleShape.copy(
            CornerSize(16.dp)
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Black.copy(
                alpha = .85f
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 24.dp
            ).fillMaxHeight()
        ) {
            Row(
//                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card(
                    modifier = Modifier.size(25.dp).background(
                        color = Color.Transparent,
                    ),
                    shape = CircleShape,
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(
                            color = color.copy(
                                alpha = .15f
                            )
                        ),
                        contentAlignment = Alignment.Center,
                    ){
                        Icon(
                            imageVector = icon,
                            contentDescription = "Arrow Icon",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = label,
                    color = Color.White.copy(
                        alpha = .75f
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "KES " + "%.2f".format(amount),
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = filter,
                fontSize = 12.sp,
                color = Color.Gray.copy(
                    alpha = .75f,
                )
            )
        }
    }
}

@Composable
fun ShowAmountContainers(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>,
){
    Row(
        modifier = modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
        var incomeAmount: Float = 0f
        var expenseAmount: Float = 0f

        transactions.forEach { transaction ->
            if(transaction.type.lowercase() == "income"){
                incomeAmount += transaction.amount
            } else {
                expenseAmount += transaction.amount
            }
        }

        AmountCard(
            label = "Income",
            filter = "This Month",
            amount = incomeAmount,
            color = Color.Green,
            icon = Icons.Filled.ArrowOutward,
            modifier = Modifier.weight(1f).fillMaxHeight(),
        )
        Spacer(modifier = Modifier.width(20.dp))
        AmountCard(
            label = "Expense",
            filter = "This Month",
            amount = expenseAmount,
            color = Color.Red,
            icon = Icons.Filled.ArrowOutward,
            modifier = Modifier.weight(1f).fillMaxHeight(),
        )
    }
}

@Composable
fun ShowSpendingSlider(
    modifier: Modifier = Modifier,
    usedAmount: Float = 0f,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Monthly budget used",
            fontSize = 18.sp,
            color = Color.White.copy(
                alpha = .65f
            )
        )
        Slider(
            value = usedAmount,
            steps = 31,
            onValueChange = {},
            modifier = Modifier.padding(top = 16.dp).height(
                5.dp
            )
        )
    }
}

@Composable
fun ListRecentTransactions(
    modifier: Modifier = Modifier,
    transactionViewModel: HomeScreenViewModel = viewModel(),
){
    val transactions = transactionViewModel.transactionList.collectAsState().value
    Column(
        modifier = modifier.fillMaxHeight().background(
            color = Color.Black
        ),
        verticalArrangement = Arrangement.Top,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Recent Transactions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                text = "See All",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Yellow,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            transactions.forEach {
                TransactionCard(
                    modifier = Modifier.padding(
                        top = 10.dp
                    ),
                    transaction = it
                )
            }
        }
    }
}

@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    homeScreenViewModel: HomeScreenViewModel = viewModel()
) {
    val category = homeScreenViewModel.categoryList
        .collectAsState().value.filter { category ->
            category.id == transaction.categoryId
        }[0]
    Row(
        modifier = modifier.fillMaxWidth().background(
            color = Color.White.copy(
                alpha = .1f
            ),
            shape = CircleShape.copy(
                CornerSize(12.dp)
            )
        ).clip(
            shape = CircleShape.copy(
                CornerSize(12.dp)
            )
        ).padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.padding(
                top = 12.dp, end = 12.dp, bottom = 12.dp
            ),
            shape = CircleShape.copy(
                CornerSize(8.dp)
            ),
            colors = CardDefaults.cardColors().copy(
                Color(category.color).copy(
                    alpha = .175f
                )
            ),
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = TransactionIconRegistry.getVector(
                        category.icon,
                    ),
                    contentDescription = "Transaction Icon",
                    modifier = Modifier.size(40.dp).padding(
                        all = 8.dp
                    ),
                    tint = Color(category.color)
                )
            }
        }

        Column(
            modifier = Modifier.padding(start = 12.dp).weight(
                1f
            ),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = transaction.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = DateTimeFormatter.ofPattern("EEE, d MMM y").format(transaction.createdAt),
                fontSize = 12.sp,
                color = Color.White.copy(
                    alpha = .75f
                ),
            )
        }

        Text(
            text = "KES " + "%,.2f".format(transaction.amount),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}