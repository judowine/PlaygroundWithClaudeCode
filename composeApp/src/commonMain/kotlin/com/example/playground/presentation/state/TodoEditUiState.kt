package com.example.playground.presentation.state

import com.example.playground.shared.domain.model.TodoItem

/**
 * TODO編集画面のUIState
 * Design Docの仕様に基づく状態管理
 */
data class TodoEditUiState(
    val todo: TodoItem? = null,
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasUnsavedChanges: Boolean = false,
    val isNewTodo: Boolean = true
) {
    /**
     * エラー状態かどうか
     */
    val hasError: Boolean
        get() = errorMessage != null
        
    /**
     * 保存可能かどうか
     */
    val canSave: Boolean
        get() = title.isNotBlank() && !isLoading
        
    /**
     * 編集中のタイトルが有効かどうか
     */
    val isTitleValid: Boolean
        get() = title.isNotBlank()
}