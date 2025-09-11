package com.example.playground.feature.todo.domain.usecase

import com.example.playground.feature.todo.domain.exception.TodoException
import com.example.playground.feature.todo.domain.model.TodoItem
import com.example.playground.feature.todo.domain.repository.TodoRepository

/**
 * Use case for creating new TODO items.
 * Handles business logic and validation for TODO creation operations.
 */
class CreateTodoUseCase(
    private val repository: TodoRepository
) {
    /**
     * Create a new TODO item with validation
     * @param title The title of the TODO item (required, cannot be blank)
     * @param description Optional description for the TODO item
     * @return Result containing the created TodoItem with generated ID or error information
     */
    suspend operator fun invoke(title: String, description: String = ""): Result<TodoItem> {
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
        
        // Create the todo item using the factory method
        val todo = TodoItem.create(title, description)
        
        // Delegate to repository for persistence
        return repository.createTodo(todo)
    }
}