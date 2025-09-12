package com.example.playground.presentation.di

import com.example.playground.presentation.viewmodel.TodoEditViewModel
import com.example.playground.presentation.viewmodel.TodoListViewModel
import com.example.playground.shared.di.TodoModule

/**
 * ViewModelの依存性注入モジュール
 * Design Docの仕様に基づくシンプルなViewModel生成
 */
class ViewModelModule(private val todoModule: TodoModule) {
    
    fun createTodoListViewModel(): TodoListViewModel {
        return TodoListViewModel(
            getTodosUseCase = todoModule.getTodosUseCase,
            deleteTodoUseCase = todoModule.deleteTodoUseCase,
            toggleTodoCompletionUseCase = todoModule.toggleTodoCompletionUseCase
        )
    }
    
    fun createTodoEditViewModel(): TodoEditViewModel {
        return TodoEditViewModel(
            createTodoUseCase = todoModule.createTodoUseCase,
            updateTodoUseCase = todoModule.updateTodoUseCase
        )
    }
}