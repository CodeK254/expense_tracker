package com.tamara.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tamara.expensetracker.screens.bottomnav.BottomNavigationScreen

@Composable
fun ExpenseNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ExpenseTrackerScreens.BottomNavigation.name
    ){
        composable(ExpenseTrackerScreens.BottomNavigation.name){
            BottomNavigationScreen(navController = navController)
        }
    }
}