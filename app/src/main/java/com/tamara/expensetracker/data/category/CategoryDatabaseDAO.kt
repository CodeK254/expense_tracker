package com.tamara.expensetracker.data.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tamara.expensetracker.models.category.Category
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CategoryDatabaseDAO {
    // READ
    @Query("SELECT * FROM categories_table ORDER BY category_entry_date DESC")
    fun getCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories_table WHERE id =:id ORDER BY category_entry_date DESC")
    suspend fun getCategoryBYID(id: UUID): Category

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    // UPDATE
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategory(category: Category)

    // DELETE
    @Query("DELETE FROM categories_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteCategory(category: Category)
}
