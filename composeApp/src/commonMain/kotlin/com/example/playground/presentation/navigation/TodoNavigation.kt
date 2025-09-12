package com.example.playground.presentation.navigation

/**
 * TODO アプリのナビゲーション定義
 * Design Docの仕様に基づくシンプルなナビゲーション（スケルトン実装）
 */

/**
 * ナビゲーション画面定義
 */
sealed class TodoScreen(val route: String) {
    object TodoList : TodoScreen("todo_list")
    object CreateTodo : TodoScreen("create_todo")
    data class EditTodo(val todoId: Long) : TodoScreen("edit_todo/{todoId}") {
        companion object {
            const val ROUTE_TEMPLATE = "edit_todo/{todoId}"
            fun createRoute(todoId: Long): String = "edit_todo/$todoId"
        }
    }
}

/**
 * ナビゲーションアクション
 */
interface TodoNavigationActions {
    fun navigateToTodoList()
    fun navigateToCreateTodo()
    fun navigateToEditTodo(todoId: Long)
    fun navigateBack()
}

/**
 * シンプルなナビゲーション実装（スケルトン）
 */
class SimpleTodoNavigationActions : TodoNavigationActions {
    override fun navigateToTodoList() {
        // TODO: 実際のナビゲーション実装
    }
    
    override fun navigateToCreateTodo() {
        // TODO: 実際のナビゲーション実装
    }
    
    override fun navigateToEditTodo(todoId: Long) {
        // TODO: 実際のナビゲーション実装
    }
    
    override fun navigateBack() {
        // TODO: 実際のナビゲーション実装
    }
}