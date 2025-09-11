package com.example.playground.feature.todo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.feature.todo.domain.usecase.GetTodosUseCase
import com.example.playground.feature.todo.domain.usecase.DeleteTodoUseCase
import com.example.playground.feature.todo.domain.usecase.ToggleTodoCompletionUseCase
import com.example.playground.feature.todo.presentation.state.TodoListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for TodoListScreen
 * Manages the state and business logic for the todo list display
 */
class TodoListViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val toggleCompletionUseCase: ToggleTodoCompletionUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TodoListUiState.loading())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()
    
    init {
        // TODO: Initialize with dependency injection
        observeTodos()
    }
    
    /**
     * Start observing todo items for real-time updates
     */
    private fun observeTodos() {
        viewModelScope.launch {
            getTodosUseCase.observeTodos()
                .catch { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load todos"
                        )
                    }
                }
                .collect { todos ->
                    _uiState.update { 
                        it.copy(
                            todos = todos,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }
    
    /**
     * Refresh the todo list manually
     */
    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            
            getTodosUseCase().fold(
                onSuccess = { todos ->
                    _uiState.update { 
                        it.copy(
                            todos = todos,
                            isRefreshing = false,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { 
                        it.copy(
                            isRefreshing = false,
                            errorMessage = error.message ?: "Failed to refresh todos"
                        )
                    }
                }
            )
        }
    }
    
    /**
     * Delete a todo item
     * @param todoId The ID of the todo to delete
     */
    fun deleteTodo(todoId: Long) {
        viewModelScope.launch {
            deleteTodoUseCase(todoId).fold(
                onSuccess = {
                    // Todo will be automatically updated via observeTodos()
                    clearError()
                },
                onFailure = { error ->
                    _uiState.update { 
                        it.copy(errorMessage = error.message ?: "Failed to delete todo")
                    }
                }
            )
        }
    }
    
    /**
     * Toggle the completion status of a todo item
     * @param todoId The ID of the todo to toggle
     */
    fun toggleCompletion(todoId: Long) {
        viewModelScope.launch {
            toggleCompletionUseCase(todoId).fold(
                onSuccess = {
                    // Todo will be automatically updated via observeTodos()
                    clearError()
                },
                onFailure = { error ->
                    _uiState.update { 
                        it.copy(errorMessage = error.message ?: "Failed to update todo")
                    }
                }
            )
        }
    }
    
    /**
     * Show delete confirmation dialog for a specific todo
     * @param todo The todo item to confirm deletion for
     */
    fun showDeleteConfirmation(todo: com.example.playground.feature.todo.domain.model.TodoItem) {
        _uiState.update { it.copy(showDeleteConfirmation = todo) }
    }
    
    /**
     * Hide the delete confirmation dialog
     */
    fun hideDeleteConfirmation() {
        _uiState.update { it.copy(showDeleteConfirmation = null) }
    }
    
    /**
     * Confirm deletion of the todo item in the confirmation dialog
     */
    fun confirmDelete() {
        val todoToDelete = _uiState.value.showDeleteConfirmation
        if (todoToDelete != null) {
            hideDeleteConfirmation()
            deleteTodo(todoToDelete.id)
        }
    }
    
    /**
     * Clear any error message
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}