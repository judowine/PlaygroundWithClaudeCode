package com.example.playground.shared.di

import com.example.playground.shared.data.repository.TodoRepositoryImpl
import com.example.playground.shared.database.TodoDatabase
import com.example.playground.shared.domain.repository.TodoRepository
import com.example.playground.shared.domain.usecase.*
import com.example.playground.shared.platform.DatabaseDriverFactory

/**
 * TODO機能の依存性注入モジュール
 * Design Docの仕様に基づくシンプルなDIコンテナ
 */
class TodoModule(private val driverFactory: DatabaseDriverFactory) {
    
    // Database
    private val database: TodoDatabase by lazy {
        TodoDatabase(driverFactory.createDriver())
    }
    
    // Repository
    private val todoRepository: TodoRepository by lazy {
        TodoRepositoryImpl(database)
    }
    
    // UseCases
    val getTodosUseCase: GetTodosUseCase by lazy {
        GetTodosUseCase(todoRepository)
    }
    
    val getTodosByCompletionUseCase: GetTodosByCompletionUseCase by lazy {
        GetTodosByCompletionUseCase(todoRepository)
    }
    
    val createTodoUseCase: CreateTodoUseCase by lazy {
        CreateTodoUseCase(todoRepository)
    }
    
    val updateTodoUseCase: UpdateTodoUseCase by lazy {
        UpdateTodoUseCase(todoRepository)
    }
    
    val deleteTodoUseCase: DeleteTodoUseCase by lazy {
        DeleteTodoUseCase(todoRepository)
    }
    
    val toggleTodoCompletionUseCase: ToggleTodoCompletionUseCase by lazy {
        ToggleTodoCompletionUseCase(todoRepository)
    }
}