package com.example.playground.shared.domain.usecase

import com.example.playground.shared.domain.model.TodoItem
import com.example.playground.shared.domain.repository.TodoRepository
import kotlinx.datetime.Clock

/**
 * TODO作成UseCase
 * Design Docの仕様に基づくバリデーション付き実装
 */
class CreateTodoUseCase(
    private val repository: TodoRepository
) {
    /**
     * 新規TODOアイテムを作成
     * @param title タスクタイトル（必須）
     * @param description タスク説明（オプション）
     * @return Result<TodoItem> - 作成されたTODOアイテム
     */
    suspend operator fun invoke(title: String, description: String = ""): Result<TodoItem> {
        return if (title.isBlank()) {
            Result.failure(IllegalArgumentException("タイトルは必須です"))
        } else {
            val todo = TodoItem(
                title = title.trim(),
                description = description.trim(),
                createdAt = Clock.System.now().epochSeconds
            )
            repository.createTodo(todo)
        }
    }
}