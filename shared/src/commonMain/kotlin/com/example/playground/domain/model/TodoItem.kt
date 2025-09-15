package com.example.playground.domain.model

import kotlinx.datetime.Instant

/**
 * TODOアイテムのドメインモデル
 * 不変オブジェクトとして設計され、アプリケーション全体で使用される
 */
data class TodoItem(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Instant,
    val completedAt: Instant? = null,
    val updatedAt: Instant = createdAt
) {
    /**
     * タイトルが有効かどうかをチェック
     */
    fun isValidTitle(): Boolean = title.isNotBlank() && title.length <= MAX_TITLE_LENGTH

    /**
     * 説明が有効かどうかをチェック
     */
    fun isValidDescription(): Boolean = description.length <= MAX_DESCRIPTION_LENGTH

    /**
     * タスクを完了状態にする
     */
    fun markCompleted(): TodoItem = copy(
        isCompleted = true,
        completedAt = kotlinx.datetime.Clock.System.now(),
        updatedAt = kotlinx.datetime.Clock.System.now()
    )

    /**
     * タスクを未完了状態にする
     */
    fun markIncomplete(): TodoItem = copy(
        isCompleted = false,
        completedAt = null,
        updatedAt = kotlinx.datetime.Clock.System.now()
    )

    /**
     * タスクを更新する
     */
    fun update(title: String, description: String): TodoItem = copy(
        title = title,
        description = description,
        updatedAt = kotlinx.datetime.Clock.System.now()
    )

    companion object {
        const val MAX_TITLE_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 500
    }
}