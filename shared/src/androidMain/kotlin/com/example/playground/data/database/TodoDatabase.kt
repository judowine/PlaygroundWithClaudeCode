package com.example.playground.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playground.data.dao.TodoDao
import com.example.playground.data.entity.TodoEntity

/**
 * TODO アプリケーション用Room Database
 *
 * Security Considerations:
 * - WALモード有効化によるパフォーマンスと整合性両立
 * - データベース暗号化準備（SQLCipher統合可能）
 * - 適切なバックアップ除外設定
 *
 * Performance Considerations:
 * - Version管理による適切なマイグレーション戦略
 * - インデックス最適化
 * - Connection pooling設定
 *
 * TODO: Phase 2でインスタンス化とDI設定を実装予定
 */
@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = true
)
abstract class TodoDatabase : RoomDatabase() {

    /**
     * TodoDAO インスタンス取得
     */
    abstract fun todoDao(): TodoDao

    companion object {
        const val DATABASE_NAME = "todo_database"

        // Security: Database file configuration
        const val DATABASE_PASSPHRASE_LENGTH = 32

        // Performance: Connection pool settings
        const val MAX_CONNECTION_POOL_SIZE = 4
        const val CONNECTION_TIMEOUT_MS = 30000L

        /**
         * Database configuration keys for security hardening
         */
        object Config {
            const val ENABLE_WAL_MODE = "enable_wal_mode"
            const val ENABLE_FOREIGN_KEYS = "enable_foreign_keys"
            const val BACKUP_EXCLUDE = "android:allowBackup"
        }

        /**
         * Database creation parameters for Phase 2 implementation
         * TODO: Implement in Platform-specific DatabaseModule
         */
        object CreationParams {
            // Security: Database encryption settings (SQLCipher ready)
            const val ENCRYPTION_ENABLED = false // TODO: Enable in production

            // Performance: Database optimization settings
            const val ENABLE_AUTO_VACUUM = true
            const val JOURNAL_MODE_WAL = true
            const val SYNCHRONOUS_NORMAL = true

            // Backup and recovery settings
            const val ENABLE_AUTOMATIC_BACKUP = false
            const val BACKUP_RETENTION_DAYS = 30
        }
    }
}