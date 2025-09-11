package com.example.playground.feature.todo.domain.usecase

import com.example.playground.feature.todo.domain.exception.TodoException
import com.example.playground.feature.todo.domain.model.TodoItem
import com.example.playground.feature.todo.domain.repository.TodoRepository

/**
 * Use case for updating existing TODO items.
 * Handles business logic and validation for TODO update operations.
 */
class UpdateTodoUseCase(
    private val repository: TodoRepository
) {
    /**
     * Update an existing TODO item with validation
     * @param todoId The ID of the TODO item to update
     * @param title The new title for the TODO item (required, cannot be blank)
     * @param description The new description for the TODO item
     * @return Result containing the updated TodoItem or error information
     */
    suspend operator fun invoke(todoId: Long, title: String, description: String): Result<TodoItem> {
        // Validate title is not blank
        if (title.isBlank()) {
            return Result.failure(
                TodoException.ValidationException(
                    field = "title",
                    reason = "Title cannot be empty or blank"
                )
            )
        }
        
        // Validate title length (max 100 characters as per design doc)
        if (title.length > 100) {
            return Result.failure(
                TodoException.ValidationException(
                    field = "title",
                    reason = "Title cannot exceed 100 characters"
                )
            )
        }
        
        // Validate description length (max 500 characters as per design doc)
        if (description.length > 500) {
            return Result.failure(
                TodoException.ValidationException(
                    field = "description",
                    reason = "Description cannot exceed 500 characters"
                )
            )
        }
        
        // Get the existing TODO item
        return repository.getTodoById(todoId).fold(
            onSuccess = { todo ->
                if (todo == null) {
                    Result.failure(TodoException.NotFoundException(todoId))
                } else {
                    // Update the todo item using the domain model method
                    val updatedTodo = todo.update(title, description)
                    repository.updateTodo(updatedTodo)
                }
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }
}