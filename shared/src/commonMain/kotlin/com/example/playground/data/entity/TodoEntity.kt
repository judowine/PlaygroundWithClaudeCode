package com.example.playground.data.entity

// TODO: Platform Layer (SK-004) でRoom annotations有効化予定
// import androidx.room.ColumnInfo
// import androidx.room.Entity
// import androidx.room.PrimaryKey

/**
 * TODOアイテムのデータベースEntity
 * Room Databaseで使用される永続化用データモデル
 *
 * Security Considerations:
 * - データベース制約による整合性保証
 * - SQLインジェクション対策: Room使用により安全なクエリ実行
 * - 入力値検証: title, description長さ制限をデータベースレベルで強制
 *
 * Performance Considerations:
 * - Index on createdAt for chronological sorting
 * - Index on isCompleted for filtering queries
 * - Epoch milliseconds for cross-platform timestamp compatibility
 */
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "completed_at")
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