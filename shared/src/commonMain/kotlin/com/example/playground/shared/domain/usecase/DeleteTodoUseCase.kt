package com.example.playground.shared.domain.usecase

import com.example.playground.shared.domain.repository.TodoRepository

/**
 * TODO削除UseCase
 * Design Docの仕様に基づく実装
 */
class DeleteTodoUseCase(
    private val repository: TodoRepository
) {
    /**
     * TODOアイテムを削除
     * @param id 削除するTODOアイテムのID
     * @return Result<Unit>
     */
    suspend operator fun invoke(id: Long): Result<Unit> = repository.deleteTodo(id)
}