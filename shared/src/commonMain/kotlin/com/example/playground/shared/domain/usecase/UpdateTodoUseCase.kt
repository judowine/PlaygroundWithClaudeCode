package com.example.playground.shared.domain.usecase

import com.example.playground.shared.domain.model.TodoItem
import com.example.playground.shared.domain.repository.TodoRepository

/**
 * TODO更新UseCase
 * Design Docの仕様に基づくバリデーション付き実装
 */
class UpdateTodoUseCase(
    private val repository: TodoRepository
) {
    /**
     * TODOアイテムを更新
     * @param todo 更新するTODOアイテム
     * @return Result<TodoItem> - 更新されたTODOアイテム
     */
    suspend operator fun invoke(todo: TodoItem): Result<TodoItem> {
        return if (todo.title.isBlank()) {
            Result.failure(IllegalArgumentException("タイトルは必須です"))
        } else {
            repository.updateTodo(todo)
        }
    }
}