package com.example.playground.feature.todo.domain.repository

import com.example.playground.feature.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for TODO operations.
 * This interface defines the contract for data access operations and is implemented
 * by platform-specific data layer components (e.g., Room on Android).
 * Uses Result<T> pattern for error handling and Flow for reactive data streams.
 */
interface TodoRepository {
    
    /**
     * Retrieve all TODO items from the data source
     * @return Result containing list of TodoItem or error information
     */
    suspend fun getAllTodos(): Result<List<TodoItem>>
    
    /**
     * Retrieve a specific TODO item by its ID
     * @param id The unique identifier of the TODO item
     * @return Result containing TodoItem if found, null if not found, or error information
     */
    suspend fun getTodoById(id: Long): Result<TodoItem?>
    
    /**
     * Create a new TODO item in the data source
     * @param todo The TodoItem to create (ID will be generated)
     * @return Result containing the created TodoItem with generated ID or error information
     */
    suspend fun createTodo(todo: TodoItem): Result<TodoItem>
    
    /**
     * Update an existing TODO item in the data source
     * @param todo The TodoItem with updated information
     * @return Result containing the updated TodoItem or error information
     */
    suspend fun updateTodo(todo: TodoItem): Result<TodoItem>
    
    /**
     * Delete a TODO item from the data source
     * @param id The unique identifier of the TODO item to delete
     * @return Result indicating success (Unit) or error information
     */
    suspend fun deleteTodo(id: Long): Result<Unit>
    
    /**
     * Toggle the completion status of a TODO item
     * @param id The unique identifier of the TODO item to toggle
     * @return Result containing the updated TodoItem with toggled completion status
     */
    suspend fun toggleTodoCompletion(id: Long): Result<TodoItem>
    
    /**
     * Observe all TODO items for real-time updates
     * @return Flow that emits the current list of TodoItem whenever data changes
     */
    fun observeTodos(): Flow<List<TodoItem>>
}