package com.example.playground.shared.domain.model

import kotlinx.datetime.Clock

/**
 * TODOアイテムのドメインモデル
 * Design Docの仕様に基づいた不変データクラス
 */
data class TodoItem(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val completedAt: Long? = null
) {
    companion object {
        /**
         * 新規TODOアイテムの作成用ファクトリメソッド
         */
        fun create(
            title: String,
            description: String = ""
        ): TodoItem = TodoItem(
            title = title.trim(),
            description = description.trim(),
            createdAt = Clock.System.now().epochSeconds
        )
    }
}