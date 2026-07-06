package com.tamara.expensetracker.navigation

enum class ExpenseTrackerScreens{
    BottomNavigation;

    companion object {
        fun fromRoute(route: String?): ExpenseTrackerScreens
                = when (route?.substringBefore("/")) {
            BottomNavigation.name -> BottomNavigation
            null -> BottomNavigation
            else  -> throw IllegalArgumentException("Route $route is not recognised")
        }
    }
}