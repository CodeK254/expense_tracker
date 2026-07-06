package com.tamara.expensetracker.data.category

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.tamara.expensetracker.models.category.Category

class CategoryDataSource {
    fun fetchCategories(): List<Category> {
        return listOf(
            Category(
                label = "Food",
                value = 0f,
                icon = "food",
                color = Color(0xFF4CAF50).toArgb(),
            ),
            Category(
                label = "Transport",
                value = 0f,
                icon = "transport",
                color = Color.Blue.toArgb()
            ),
            Category(
                label = "Entertainment",
                value = 0f,
                icon = "movies",
                color = Color.Magenta.toArgb()
            ),
            Category(
                label = "Shopping",
                value = 0f,
                icon = "grocery",
                color = Color.Green.toArgb()
            ),
            Category(
                label = "Utilities",
                value = 0f,
                icon = "electricity",
                color = Color.Cyan.toArgb()
            ),
            Category(
                label = "Health",
                value = 0f,
                icon = "gym",
                color = Color.Yellow.toArgb()
            ),
            Category(
                label = "Education",
                value = 0f,
                icon = "education",
                color = Color.Gray.toArgb()
            ),
            Category(
                label = "Savings",
                value = 0f,
                icon = "savings",
                color = Color(0xFF4CAF50).toArgb()
            ),
            Category(
                label = "Income",
                value = 0f,
                icon = "income",
                color = Color.Green.toArgb()
            ),
            Category(
                label = "Monthly Debt",
                value = 0f,
                icon = "monthly_debt",
                color = Color.Red.toArgb()
            ),
        )
    }
}