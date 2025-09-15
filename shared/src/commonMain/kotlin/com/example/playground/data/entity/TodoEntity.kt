package com.example.playground.data.entity

/**
 * TODOアイテムのデータベースEntity
 * Room Databaseで使用される永続化用データモデル
 *
 * Security Considerations:
 * - データベース制約による整合性保証
 * - SQLインジェクション対策: Room使用により安全なクエリ実行
 * - 入力値検証: title, description長さ制限をデータベースレベルで強制
 *
 * TODO: Platform Layer実装時にRoom annotations有効化
 * - @Entity(tableName = "todos")
 * - @PrimaryKey(autoGenerate = true)
 * - @ColumnInfo constraints追加
 */
// @Entity(tableName = "todos")
data class TodoEntity(
    // @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // @ColumnInfo(name = "title")
    // TODO: Add NOT NULL constraint and max length validation
    val title: String,

    // @ColumnInfo(name = "description")
    // TODO: Add max length constraint (500 chars)
    val description: String,

    // @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean,

    // @ColumnInfo(name = "created_at")
    // TODO: Store as epoch milliseconds for cross-platform compatibility
    val createdAt: Long,

    // @ColumnInfo(name = "completed_at")
    // TODO: Nullable timestamp for completion tracking
    val completedAt: Long?
) {
    companion object {
        // Database constraints for security and data integrity
        const val TABLE_NAME = "todos"
        const val MAX_TITLE_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 500

        // TODO: Add database indexes for performance
        // - Index on createdAt for chronological sorting
        // - Index on isCompleted for filtering
    }
}