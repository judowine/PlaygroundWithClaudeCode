package com.example.playground.feature.todo.data.database.dao

import androidx.room.*
import com.example.playground.feature.todo.data.database.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO (Data Access Object) for TODO item database operations.
 * Provides suspend functions for async database operations and Flow for reactive queries.
 */
@Dao
interface TodoDao {
    
    /**
     * Get all TODO items ordered by creation date (newest first)
     * @return List of all TodoEntity items
     */
    @Query("SELECT * FROM todo_items ORDER BY created_at DESC")
    suspend fun getAllTodos(): List<TodoEntity>
    
    /**
     * Observe all TODO items with real-time updates ordered by creation date (newest first)
     * @return Flow that emits updated list whenever data changes
     */
    @Query("SELECT * FROM todo_items ORDER BY created_at DESC")
    fun observeAllTodos(): Flow<List<TodoEntity>>
    
    /**
     * Get a specific TODO item by its ID
     * @param id The unique identifier of the TODO item
     * @return TodoEntity if found, null otherwise
     */
    @Query("SELECT * FROM todo_items WHERE id = :id")
    suspend fun getTodoById(id: Long): TodoEntity?
    
    /**
     * Insert a new TODO item into the database
     * @param todo The TodoEntity to insert
     * @return The generated ID of the inserted item
     */
    @Insert
    suspend fun insertTodo(todo: TodoEntity): Long
    
    /**
     * Update an existing TODO item in the database
     * @param todo The TodoEntity with updated information
     */
    @Update
    suspend fun updateTodo(todo: TodoEntity)
    
    /**
     * Delete a TODO item from the database using the entity
     * @param todo The TodoEntity to delete
     */
    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
    
    /**
     * Delete a TODO item from the database by its ID
     * @param id The unique identifier of the TODO item to delete
     */
    @Query("DELETE FROM todo_items WHERE id = :id")
    suspend fun deleteTodoById(id: Long)
    
    /**
     * Get count of all TODO items
     * @return Total number of TODO items in the database
     */
    @Query("SELECT COUNT(*) FROM todo_items")
    suspend fun getTodoCount(): Int
    
    /**
     * Get count of completed TODO items
     * @return Number of completed TODO items in the database
     */
    @Query("SELECT COUNT(*) FROM todo_items WHERE is_completed = 1")
    suspend fun getCompletedTodoCount(): Int
    
    /**
     * Get count of pending (not completed) TODO items
     * @return Number of pending TODO items in the database
     */
    @Query("SELECT COUNT(*) FROM todo_items WHERE is_completed = 0")
    suspend fun getPendingTodoCount(): Int
}