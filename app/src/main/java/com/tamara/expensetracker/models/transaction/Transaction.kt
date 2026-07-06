package com.tamara.expensetracker.models.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.tamara.expensetracker.models.category.Category
import java.time.LocalDate
import java.util.UUID

@Entity(
    tableName = "transactions_table",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Transaction(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "category_id")
    val categoryId: UUID = UUID.randomUUID(),
    
    @ColumnInfo(name = "transaction_title")
    val title: String,

    @ColumnInfo(name = "transaction_type")
    val type: String,

    @ColumnInfo(name = "transaction_amount")
    val amount: Float,

    @ColumnInfo(name = "transaction_description")
    val description: String,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDate = LocalDate.now(),
)