package com.example.playground.shared.domain.usecase

import com.example.playground.shared.domain.model.TodoItem
import com.example.playground.shared.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

/**
 * 完了状態別TODO取得UseCase
 * Design Docの仕様に基づく実装
 */
class GetTodosByCompletionUseCase(
    private val repository: TodoRepository
) {
    /**
     * 完了状態でフィルタリングしたTODOアイテムを取得
     */
    operator fun invoke(isCompleted: Boolean): Flow<List<TodoItem>> =
        repository.getTodosByCompletion(isCompleted)
}