package com.tamara.expensetracker.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector

object TransactionIconRegistry {
    private val iconMap = mapOf(
        "income" to Icons.Default.ArrowUpward,
        "expense" to Icons.Default.ArrowDownward,
        "food" to Icons.Default.Fastfood,
        "transport" to Icons.Default.DirectionsCar,
        "grocery" to Icons.Filled.LocalGroceryStore,
        "salary" to Icons.Filled.AttachMoney,
        "movies" to Icons.Filled.Tv,
        "gym" to Icons.Filled.FitnessCenter,
        "freelance" to Icons.Filled.AddCard,
        "electricity" to Icons.Filled.ElectricBolt,
        "savings" to Icons.Filled.Savings,
        "education" to Icons.Filled.MenuBook,
        "monthly_debt" to Icons.Filled.Checklist,
    )

    // Helper to get vector, falling back to a default if not found
    fun getVector(id: String): ImageVector {
        return iconMap[id] ?: Icons.Default.ArrowDownward
    }

    // Optional: Get the ID from a vector if you need to map backwards
    fun getId(vector: ImageVector): String {
        return iconMap.entries.firstOrNull { it.value == vector }?.key ?: "expense"
    }
}