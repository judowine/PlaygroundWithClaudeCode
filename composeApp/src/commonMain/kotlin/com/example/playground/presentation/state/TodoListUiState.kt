package com.example.playground.presentation.state

import com.example.playground.shared.domain.model.TodoItem

/**
 * TODO一覧画面のUIState
 * Design Docの仕様に基づく状態管理
 */
data class TodoListUiState(
    val todos: List<TodoItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showCompletedOnly: Boolean = false
) {
    /**
     * フィルタリング済みのTODOリストを取得
     */
    val filteredTodos: List<TodoItem>
        get() = if (showCompletedOnly) {
            todos.filter { it.isCompleted }
        } else {
            todos
        }
        
    /**
     * エラー状態かどうか
     */
    val hasError: Boolean
        get() = errorMessage != null
        
    /**
     * 空状態かどうか
     */
    val isEmpty: Boolean
        get() = todos.isEmpty() && !isLoading
}