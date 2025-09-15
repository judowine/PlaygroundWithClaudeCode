package com.example.playground.domain.usecase

import com.example.playground.domain.model.TodoItem
import com.example.playground.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

/**
 * TODO一覧取得UseCase
 * ビジネスロジックを含む場合は、このクラス内で処理を行う
 */
class GetTodosUseCase(
    private val repository: TodoRepository
) {

    /**
     * 全TODOアイテムを一度だけ取得
     * @return TODO一覧取得結果
     */
    suspend operator fun invoke(): Result<List<TodoItem>> {
        return repository.getAllTodos()
            .mapCatching { todos ->
                // ビジネスロジック：作成日時でソート
                todos.sortedBy { it.createdAt }
            }
    }

    /**
     * TODOアイテムをリアルタイムで監視
     * @return TODOアイテムリストのFlow（作成日時順）
     */
    fun observe(): Flow<List<TodoItem>> {
        return repository.observeAllTodos()
    }
}