package com.example.playground.shared.platform

import app.cash.sqldelight.db.SqlDriver

/**
 * プラットフォーム固有のDatabaseDriver生成ファクトリ
 * Design Docのexpect/actual戦略に基づく実装
 */
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}