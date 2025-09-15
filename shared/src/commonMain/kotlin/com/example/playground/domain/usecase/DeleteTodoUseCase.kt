package com.example.playground.domain.usecase

import com.example.playground.domain.repository.TodoRepository

/**
 * TODO削除UseCase
 * TODO削除時のビジネスロジックを担当
 */
class DeleteTodoUseCase(
    private val repository: TodoRepository
) {

    /**
     * TODOアイテムを削除
     * @param todoId 削除するTODOアイテムのID
     * @return 削除結果
     */
    suspend operator fun invoke(todoId: Long): Result<Unit> {
        // ビジネスロジック：存在確認を行ってから削除
        return repository.getTodoById(todoId)
            .mapCatching { existingTodo ->
                repository.deleteTodo(existingTodo.id).getOrThrow()
            }
    }
}