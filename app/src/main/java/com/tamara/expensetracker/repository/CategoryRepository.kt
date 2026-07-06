package com.tamara.expensetracker.repository

import com.tamara.expensetracker.data.category.CategoryDatabaseDAO
import com.tamara.expensetracker.models.category.Category
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDatabaseDAO: CategoryDatabaseDAO
) {
    suspend fun insert(category: Category) = categoryDatabaseDAO.insertCategory(category)
    suspend fun update(category: Category) = categoryDatabaseDAO.updateCategory(category)
    suspend fun delete(category: Category) = categoryDatabaseDAO.deleteCategory(category)
    suspend fun clear() = categoryDatabaseDAO.deleteAll()
    suspend fun readCategory(id: UUID): Category = categoryDatabaseDAO.getCategoryBYID(id)
    fun readAll(): Flow<List<Category>> = categoryDatabaseDAO.getCategories()
}