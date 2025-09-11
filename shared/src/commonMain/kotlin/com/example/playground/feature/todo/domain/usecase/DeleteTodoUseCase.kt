package com.example.playground.feature.todo.domain.usecase

import com.example.playground.feature.todo.domain.repository.TodoRepository

/**
 * Use case for deleting TODO items.
 * Handles business logic for TODO deletion operations.
 */
class DeleteTodoUseCase(
    private val repository: TodoRepository
) {
    /**
     * Delete a TODO item by its ID
     * @param todoId The unique identifier of the TODO item to delete
     * @return Result indicating success (Unit) or error information
     */
    suspend operator fun invoke(todoId: Long): Result<Unit> {
        return repository.deleteTodo(todoId)
    }
}