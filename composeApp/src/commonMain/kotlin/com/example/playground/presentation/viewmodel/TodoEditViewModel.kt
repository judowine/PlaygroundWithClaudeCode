package com.example.playground.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.presentation.state.TodoEditUiState
import com.example.playground.shared.domain.model.TodoItem
import com.example.playground.shared.domain.usecase.CreateTodoUseCase
import com.example.playground.shared.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * TODO編集画面のViewModel
 * Design Docの仕様に基づく編集状態管理
 */
class TodoEditViewModel(
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoEditUiState())
    val uiState: StateFlow<TodoEditUiState> = _uiState.asStateFlow()

    /**
     * 編集用TODOアイテムを設定（編集モード）
     */
    fun setTodoForEditing(todo: TodoItem) {
        _uiState.update {
            it.copy(
                todo = todo,
                title = todo.title,
                description = todo.description,
                isNewTodo = false,
                hasUnsavedChanges = false
            )
        }
    }

    /**
     * タイトルを更新
     */
    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                hasUnsavedChanges = title != (currentState.todo?.title ?: "") ||
                        currentState.description != (currentState.todo?.description ?: "")
            )
        }
    }

    /**
     * 説明を更新
     */
    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = description,
                hasUnsavedChanges = currentState.title != (currentState.todo?.title ?: "") ||
                        description != (currentState.todo?.description ?: "")
            )
        }
    }

    /**
     * TODOアイテムを保存
     */
    fun saveTodo(onSuccess: () -> Unit = {}) {
        val currentState = _uiState.value
        if (!currentState.canSave) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = if (currentState.isNewTodo) {
                createTodoUseCase(currentState.title, currentState.description)
            } else {
                val updatedTodo = currentState.todo!!.copy(
                    title = currentState.title,
                    description = currentState.description
                )
                updateTodoUseCase(updatedTodo)
            }

            result
                .onSuccess {
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            hasUnsavedChanges = false
                        )
                    }
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "保存に失敗しました"
                        )
                    }
                }
        }
    }

    /**
     * 編集をキャンセル
     */
    fun cancelEdit() {
        val currentState = _uiState.value
        _uiState.update {
            it.copy(
                title = currentState.todo?.title ?: "",
                description = currentState.todo?.description ?: "",
                hasUnsavedChanges = false,
                errorMessage = null
            )
        }
    }

    /**
     * エラーメッセージをクリア
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * 新規作成モードに設定
     */
    fun setNewTodoMode() {
        _uiState.update {
            TodoEditUiState(
                isNewTodo = true,
                title = "",
                description = ""
            )
        }
    }
}