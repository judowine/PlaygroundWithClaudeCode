package com.example.playground.domain.usecase

import com.example.playground.domain.model.TodoItem
import com.example.playground.domain.repository.TodoRepository
import kotlinx.datetime.Clock

/**
 * TODO作成UseCase
 * TODO作成時のビジネスロジックとバリデーションを担当
 */
class CreateTodoUseCase(
    private val repository: TodoRepository
) {

    /**
     * 新しいTODOアイテムを作成
     * @param title TODOのタイトル
     * @param description TODOの説明（オプション）
     * @return 作成されたTODOアイテム
     */
    suspend operator fun invoke(
        title: String,
        description: String = ""
    ): Result<TodoItem> {
        // バリデーション
        if (title.isBlank()) {
            return Result.failure(IllegalArgumentException("タイトルは必須です"))
        }

        if (title.length > TodoItem.MAX_TITLE_LENGTH) {
            return Result.failure(IllegalArgumentException("タイトルは${TodoItem.MAX_TITLE_LENGTH}文字以内で入力してください"))
        }

        if (description.length > TodoItem.MAX_DESCRIPTION_LENGTH) {
            return Result.failure(IllegalArgumentException("説明は${TodoItem.MAX_DESCRIPTION_LENGTH}文字以内で入力してください"))
        }

        // TODOアイテム作成
        val todoItem = TodoItem(
            title = title.trim(),
            description = description.trim(),
            createdAt = Clock.System.now()
        )

        return repository.createTodo(todoItem)
    }
}