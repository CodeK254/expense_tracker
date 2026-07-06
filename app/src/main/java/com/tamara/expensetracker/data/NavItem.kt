package com.tamara.expensetracker.data

import com.tamara.expensetracker.models.navitem.NavItem
import com.tamara.expensetracker.R

val bottomItemsList: List<NavItem> = listOf<NavItem>(
    NavItem(label = "Home", icon = R.drawable.lightning),
    NavItem(label = "Balance", icon = R.drawable.balance),
    NavItem(label = "Analytics", icon = R.drawable.analytics),
    NavItem(label = "Create", icon = R.drawable.create),
)