package com.example.playground.feature.todo.domain.usecase

import com.example.playground.feature.todo.domain.model.TodoItem
import com.example.playground.feature.todo.domain.repository.TodoRepository

/**
 * Use case for toggling the completion status of TODO items.
 * Handles business logic for marking TODO items as completed or uncompleted.
 */
class ToggleTodoCompletionUseCase(
    private val repository: TodoRepository
) {
    /**
     * Toggle the completion status of a TODO item
     * @param todoId The unique identifier of the TODO item to toggle
     * @return Result containing the updated TodoItem with toggled completion status
     */
    suspend operator fun invoke(todoId: Long): Result<TodoItem> {
        return repository.toggleTodoCompletion(todoId)
    }
}