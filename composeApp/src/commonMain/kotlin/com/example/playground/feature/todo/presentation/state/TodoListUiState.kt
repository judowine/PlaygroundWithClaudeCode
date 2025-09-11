package com.example.playground.feature.todo.presentation.state

import com.example.playground.feature.todo.domain.model.TodoItem

/**
 * UI state for the TodoListScreen
 * Represents all possible states of the todo list screen including loading, data, and error states
 */
data class TodoListUiState(
    val todos: List<TodoItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showDeleteConfirmation: TodoItem? = null,
    val isRefreshing: Boolean = false
) {
    /**
     * Indicates if the screen should show empty state UI
     */
    val isEmpty: Boolean
        get() = todos.isEmpty() && !isLoading
    
    /**
     * Indicates if there's an active error state
     */
    val hasError: Boolean
        get() = errorMessage != null
    
    companion object {
        /**
         * Initial loading state when the screen is first opened
         */
        fun loading() = TodoListUiState(isLoading = true)
        
        /**
         * Success state with todo data
         */
        fun success(todos: List<TodoItem>) = TodoListUiState(todos = todos)
        
        /**
         * Error state with error message
         */
        fun error(message: String) = TodoListUiState(errorMessage = message)
    }
}