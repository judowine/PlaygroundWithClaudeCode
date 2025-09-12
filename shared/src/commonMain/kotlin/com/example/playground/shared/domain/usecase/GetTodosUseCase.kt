package com.example.playground.shared.domain.usecase

import com.example.playground.shared.domain.model.TodoItem
import com.example.playground.shared.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

/**
 * TODO取得UseCase
 * Design Docの仕様に基づく実装
 */
class GetTodosUseCase(
    private val repository: TodoRepository
) {
    /**
     * 全TODOアイテムを取得
     */
    operator fun invoke(): Flow<List<TodoItem>> = repository.getAllTodos()
}