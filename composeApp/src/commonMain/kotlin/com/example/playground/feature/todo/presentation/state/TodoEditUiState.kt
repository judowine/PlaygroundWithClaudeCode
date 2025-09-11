package com.example.playground.feature.todo.presentation.state

import com.example.playground.feature.todo.domain.model.TodoItem

/**
 * UI state for the TodoEditScreen
 * Represents the form state for creating/editing a todo item
 */
data class TodoEditUiState(
    val todo: TodoItem? = null,
    val title: String = "",
    val description: String = "",
    val titleError: String? = null,
    val descriptionError: String? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
) {
    /**
     * Indicates if the form is in edit mode (updating existing todo)
     */
    val isEditMode: Boolean
        get() = todo != null
    
    /**
     * Indicates if the form has validation errors
     */
    val hasValidationErrors: Boolean
        get() = titleError != null || descriptionError != null
    
    /**
     * Indicates if the save button should be enabled
     */
    val canSave: Boolean
        get() = title.isNotBlank() && !hasValidationErrors && !isSaving
    
    /**
     * Indicates if form data has been modified from the original todo
     */
    val isModified: Boolean
        get() = when (todo) {
            null -> title.isNotBlank() || description.isNotBlank()
            else -> title != todo.title || description != todo.description
        }
    
    companion object {
        /**
         * Initial state for creating a new todo
         */
        fun createMode() = TodoEditUiState()
        
        /**
         * Initial state for editing an existing todo
         */
        fun editMode(todo: TodoItem) = TodoEditUiState(
            todo = todo,
            title = todo.title,
            description = todo.description
        )
        
        /**
         * Loading state while fetching todo data
         */
        fun loading() = TodoEditUiState(isLoading = true)
        
        /**
         * Error state with error message
         */
        fun error(message: String) = TodoEditUiState(errorMessage = message)
    }
}