package com.example.playground.shared.platform

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.playground.shared.database.TodoDatabase

/**
 * Android固有のDatabaseDriver実装
 * Design Docの仕様に基づくSQLite Driver生成
 */
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = TodoDatabase.Schema,
            context = context,
            name = "todo.db"
        )
    }
}