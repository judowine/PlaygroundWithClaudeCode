package com.example.playground.feature.todo.domain.usecase

import com.example.playground.feature.todo.domain.model.TodoItem
import com.example.playground.feature.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for retrieving TODO items.
 * Handles business logic for fetching TODO data including both one-time queries
 * and continuous observation for real-time updates.
 */
class GetTodosUseCase(
    private val repository: TodoRepository
) {
    /**
     * Get all TODO items as a one-time operation
     * @return Result containing list of TodoItem or error information
     */
    suspend operator fun invoke(): Result<List<TodoItem>> {
        return repository.getAllTodos()
    }
    
    /**
     * Observe all TODO items for real-time updates
     * @return Flow that emits the current list of TodoItem whenever data changes
     */
    fun observeTodos(): Flow<List<TodoItem>> {
        return repository.observeTodos()
    }
    
    /**
     * Get a specific TODO item by ID
     * @param id The unique identifier of the TODO item
     * @return Result containing TodoItem if found, null if not found, or error information
     */
    suspend fun getTodoById(id: Long): Result<TodoItem?> {
        return repository.getTodoById(id)
    }
}