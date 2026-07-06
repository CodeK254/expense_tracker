package com.tamara.expensetracker.screens.bottomnav

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamara.expensetracker.R
import androidx.navigation.NavController
import com.tamara.expensetracker.data.bottomItemsList
import com.tamara.expensetracker.screens.bottomnav.analytics.AnalyticsScreen
import com.tamara.expensetracker.screens.bottomnav.balance.BalanceScreen
import com.tamara.expensetracker.screens.bottomnav.create.InputScreen
import com.tamara.expensetracker.screens.bottomnav.home.HomeScreen

@Composable
fun BottomNavigationScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.background(color = Color.Black),
        bottomBar = {
            NavigationBar(
                modifier = Modifier,
                containerColor = Color.Black
            ) {
                bottomItemsList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == viewModel.selectedIndex(),
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedIconColor = Color.Transparent,
                            selectedIndicatorColor = Color.Transparent
                        ),
                        alwaysShowLabel = true,
                        icon = {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        shape = CircleShape.copy(
                                            all = CornerSize(16.dp)
                                        )
                                    ).height(
                                        35.dp
                                    )
                                    .background(
                                        color = if(viewModel.selectedIndex() == index) Color(
                                            red = 236, green = 88, blue = 0).copy(alpha = .15f
                                        ) else Color.Transparent
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 4.dp
                                    ),
                                    painter = painterResource(item.icon),
                                    contentDescription = "Icon $index",
                                )
                            }
                        },
                        label = {
                            Text(
                                item.label,
                                style = TextStyle(
                                    color = if(viewModel.selectedIndex() == index) Color(
                                        red = 236, green = 88, blue = 0
                                    ) else Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = if(viewModel.selectedIndex() == index) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        onClick = {
                            viewModel.toggleSelection(index)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.background(color = Color.Black)
                .padding(innerPadding)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if(viewModel.selectedIndex() == 0) {
                HomeScreen()
            } else if (viewModel.selectedIndex() == 1) {
                BalanceScreen()
            } else if (viewModel.selectedIndex() == 2) {
                AnalyticsScreen(
                    modifier = Modifier.background(
                        color = Color.Black,
                    )
                )
            } else {
                InputScreen()
            }
        }
    }
}