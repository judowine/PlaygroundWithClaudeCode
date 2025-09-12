package com.example.playground.shared.data.mapper

import com.example.playground.shared.database.Todo_items
import com.example.playground.shared.domain.model.TodoItem

/**
 * Entity ⟷ Domain Model変換マッパー
 * Design Docの仕様に基づくマッピング実装
 */

/**
 * SQLDelightエンティティからドメインモデルへの変換
 */
fun Todo_items.toDomainModel(): TodoItem = TodoItem(
    id = id,
    title = title,
    description = description,
    isCompleted = is_completed == 1L,
    createdAt = created_at,
    completedAt = completed_at
)

/**
 * ドメインモデルからSQLDelightエンティティへの変換用データクラス
 * （SQLDelightはinsert/update用のデータ構造が異なるため、パラメータとして使用）
 */
object TodoMapper {
    fun fromDomainToInsertParams(todo: TodoItem) = Triple(
        todo.title,
        todo.description,
        todo.createdAt
    )
    
    fun fromDomainToUpdateParams(todo: TodoItem) = listOf(
        todo.title,
        todo.description,
        if (todo.isCompleted) 1L else 0L,
        todo.completedAt,
        todo.id
    )
}