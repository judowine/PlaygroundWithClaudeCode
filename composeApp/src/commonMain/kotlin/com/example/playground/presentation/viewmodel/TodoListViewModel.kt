package com.example.playground.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.presentation.state.TodoListUiState
import com.example.playground.shared.domain.usecase.GetTodosUseCase
import com.example.playground.shared.domain.usecase.DeleteTodoUseCase
import com.example.playground.shared.domain.usecase.ToggleTodoCompletionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * TODO一覧画面のViewModel
 * Design Docの仕様に基づく状態管理とリアクティブデータ処理
 */
class TodoListViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val toggleTodoCompletionUseCase: ToggleTodoCompletionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        loadTodos()
    }

    /**
     * TODOリストを読み込み（リアルタイム更新）
     */
    private fun loadTodos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getTodosUseCase()
                .catch { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = error.message ?: "予期しないエラーが発生しました"
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
     * TODOアイテムを削除
     */
    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            deleteTodoUseCase(id)
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(errorMessage = error.message ?: "削除に失敗しました")
                    }
                }
        }
    }

    /**
     * TODOアイテムの完了状態を切り替え
     */
    fun toggleTodoCompletion(id: Long) {
        viewModelScope.launch {
            toggleTodoCompletionUseCase(id)
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(errorMessage = error.message ?: "状態の更新に失敗しました")
                    }
                }
        }
    }

    /**
     * 完了フィルターを切り替え
     */
    fun toggleCompletionFilter() {
        _uiState.update { it.copy(showCompletedOnly = !it.showCompletedOnly) }
    }

    /**
     * エラーメッセージをクリア
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * 手動でリフレッシュ
     */
    fun refresh() {
        loadTodos()
    }
}