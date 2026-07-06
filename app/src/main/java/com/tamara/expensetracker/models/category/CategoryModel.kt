package com.tamara.expensetracker.models.category

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.time.LocalDateTime
import java.util.UUID

class CategoryModel (
    val id: UUID = UUID.randomUUID(),

    val label: String,

    val value: Float,

    val icon: String,

    val color: Int = Color(0xFFE0E0E0).toArgb(),

    var percent: Float = 0f,

    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    val composeColor: Color
        get() = Color(color)

    companion object {
        fun fromCategory(category: Category): CategoryModel {
            return CategoryModel(
                id = category.id,
                label = category.label,
                value = category.value,
                icon = category.icon,
                color = category.color,
                createdAt = category.createdAt,
            )
        }
    }
}