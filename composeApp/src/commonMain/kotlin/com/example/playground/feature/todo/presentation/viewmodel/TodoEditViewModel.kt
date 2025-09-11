package com.example.playground.feature.todo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.feature.todo.domain.exception.TodoException
import com.example.playground.feature.todo.domain.usecase.GetTodosUseCase
import com.example.playground.feature.todo.domain.usecase.CreateTodoUseCase
import com.example.playground.feature.todo.domain.usecase.UpdateTodoUseCase
import com.example.playground.feature.todo.presentation.state.TodoEditUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for TodoEditScreen
 * Manages the state and business logic for creating/editing todo items
 */
class TodoEditViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TodoEditUiState.createMode())
    val uiState: StateFlow<TodoEditUiState> = _uiState.asStateFlow()
    
    init {
        // TODO: Initialize with dependency injection
    }
    
    /**
     * Load an existing todo for editing
     * @param todoId The ID of the todo to load for editing
     */
    fun loadTodo(todoId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getTodosUseCase.getTodoById(todoId).fold(
                onSuccess = { todo ->
                    if (todo != null) {
                        _uiState.update { 
                            TodoEditUiState.editMode(todo).copy(isLoading = false)
                        }
                    } else {
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                errorMessage = "Todo not found"
                            )
                        }
                    }
                },
                onFailure = { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load todo"
                        )
                    }
                }
            )
        }
    }
    
    /**
     * Update the title field with validation
     * @param newTitle The new title value
     */
    fun updateTitle(newTitle: String) {
        val titleError = validateTitle(newTitle)
        _uiState.update { 
            it.copy(
                title = newTitle,
                titleError = titleError
            )
        }
    }
    
    /**
     * Update the description field with validation
     * @param newDescription The new description value
     */
    fun updateDescription(newDescription: String) {
        val descriptionError = validateDescription(newDescription)
        _uiState.update { 
            it.copy(
                description = newDescription,
                descriptionError = descriptionError
            )
        }
    }
    
    /**
     * Save the todo (create new or update existing)
     */
    fun saveTodo() {
        val currentState = _uiState.value
        
        // Final validation before saving
        val titleError = validateTitle(currentState.title)
        val descriptionError = validateDescription(currentState.description)
        
        if (titleError != null || descriptionError != null) {
            _uiState.update { 
                it.copy(
                    titleError = titleError,
                    descriptionError = descriptionError
                )
            }
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }
            
            val result = if (currentState.isEditMode) {
                updateTodoUseCase(
                    todoId = currentState.todo!!.id,
                    title = currentState.title,
                    description = currentState.description
                )
            } else {
                createTodoUseCase(
                    title = currentState.title,
                    description = currentState.description
                )
            }
            
            result.fold(
                onSuccess = { 
                    _uiState.update { 
                        it.copy(
                            isSaving = false,
                            isSaved = true,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    when (error) {
                        is TodoException.ValidationException -> {
                            // Handle field-specific validation errors
                            when (error.field) {
                                "title" -> _uiState.update { 
                                    it.copy(
                                        isSaving = false,
                                        titleError = error.reason
                                    )
                                }
                                "description" -> _uiState.update { 
                                    it.copy(
                                        isSaving = false,
                                        descriptionError = error.reason
                                    )
                                }
                                else -> _uiState.update { 
                                    it.copy(
                                        isSaving = false,
                                        errorMessage = error.message
                                    )
                                }
                            }
                        }
                        else -> _uiState.update { 
                            it.copy(
                                isSaving = false,
                                errorMessage = error.message ?: "Failed to save todo"
                            )
                        }
                    }
                }
            )
        }
    }
    
    /**
     * Reset the saved state (called after navigation)
     */
    fun resetSavedState() {
        _uiState.update { it.copy(isSaved = false) }
    }
    
    /**
     * Clear any error message
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
    
    /**
     * Clear field-specific errors
     */
    fun clearFieldErrors() {
        _uiState.update { 
            it.copy(
                titleError = null,
                descriptionError = null
            )
        }
    }
    
    /**
     * Validate title field
     * @param title The title to validate
     * @return Error message if validation fails, null otherwise
     */
    private fun validateTitle(title: String): String? {
        return when {
            title.isBlank() -> "Title is required"
            title.length > 100 -> "Title cannot exceed 100 characters"
            else -> null
        }
    }
    
    /**
     * Validate description field
     * @param description The description to validate
     * @return Error message if validation fails, null otherwise
     */
    private fun validateDescription(description: String): String? {
        return when {
            description.length > 500 -> "Description cannot exceed 500 characters"
            else -> null
        }
    }
}