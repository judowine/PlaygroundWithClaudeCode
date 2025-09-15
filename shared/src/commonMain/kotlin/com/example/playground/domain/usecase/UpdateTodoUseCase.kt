package com.example.playground.domain.usecase

import com.example.playground.domain.model.TodoItem
import com.example.playground.domain.repository.TodoRepository
import kotlinx.datetime.Clock

/**
 * TODO更新UseCase
 * TODO更新時のビジネスロジックとバリデーションを担当
 */
class UpdateTodoUseCase(
    private val repository: TodoRepository
) {

    /**
     * TODOアイテムを更新
     * @param updatedTodo 更新するTODOアイテム
     * @return 更新されたTODOアイテム
     */
    suspend operator fun invoke(updatedTodo: TodoItem): Result<TodoItem> {
        // バリデーション
        if (!updatedTodo.isValidTitle()) {
            return Result.failure(IllegalArgumentException("タイトルが無効です"))
        }

        if (!updatedTodo.isValidDescription()) {
            return Result.failure(IllegalArgumentException("説明が無効です"))
        }

        return repository.updateTodo(updatedTodo)
    }

    /**
     * TODOの完了状態を切り替え
     * @param todoId 対象TODOのID
     * @param isCompleted 完了状態
     * @return 更新されたTODOアイテム
     */
    suspend fun toggleCompletion(todoId: Long, isCompleted: Boolean): Result<TodoItem> {
        return repository.getTodoById(todoId)
            .mapCatching { existingTodo ->
                val updatedTodo = existingTodo.copy(
                    isCompleted = isCompleted,
                    completedAt = if (isCompleted) Clock.System.now() else null
                )
                repository.updateTodo(updatedTodo).getOrThrow()
            }
    }
}