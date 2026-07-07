package com.tamara.expensetracker.models.category

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "categories_table")
class Category (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "category_label")
    val label: String,

    @ColumnInfo(name = "category_value")
    var value: Float,

    @ColumnInfo(name = "category_icon")
    val icon: String,

    @ColumnInfo(name = "category_color")
    val color: Int = Color(0xFFE0E0E0).toArgb(),

    @ColumnInfo(name = "category_entry_date")
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    val composeColor: Color
        get() = Color(color)

    companion object {
        fun default(): Category {
            return Category(
                id = UUID.randomUUID(),
                value = 0f,
                label = "UnKnown",
                icon = "unknown",
                color = Color.White.value.toInt(),
                createdAt = LocalDateTime.now()
            )
        }
    }
}