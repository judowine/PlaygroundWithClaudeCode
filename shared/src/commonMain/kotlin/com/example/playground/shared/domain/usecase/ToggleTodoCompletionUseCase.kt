package com.example.playground.shared.domain.usecase

import com.example.playground.shared.domain.model.TodoItem
import com.example.playground.shared.domain.repository.TodoRepository

/**
 * TODO完了状態切り替えUseCase
 * Design Docの仕様に基づく実装
 */
class ToggleTodoCompletionUseCase(
    private val repository: TodoRepository
) {
    /**
     * TODOアイテムの完了状態を切り替え
     * @param id TODOアイテムID
     * @return Result<TodoItem> - 更新されたTODOアイテム
     */
    suspend operator fun invoke(id: Long): Result<TodoItem> =
        repository.toggleTodoCompletion(id)
}