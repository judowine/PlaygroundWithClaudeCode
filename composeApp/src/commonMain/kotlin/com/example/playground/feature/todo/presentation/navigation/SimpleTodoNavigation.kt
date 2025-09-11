package com.example.playground.feature.todo.presentation.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.playground.feature.todo.presentation.screen.TodoEditScreen
import com.example.playground.feature.todo.presentation.screen.TodoListScreen

/**
 * Simplified navigation for the Todo feature without external navigation dependencies
 * This is a skeleton implementation for PBI-001
 */
@Composable
fun SimpleTodoNavigation(
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf<TodoScreen>(TodoScreen.List) }
    var editingTodoId by remember { mutableStateOf<Long?>(null) }
    
    when (currentScreen) {
        TodoScreen.List -> {
            TodoListScreen(
                onNavigateToEdit = { todoId ->
                    editingTodoId = todoId
                    currentScreen = TodoScreen.Edit
                },
                modifier = modifier
            )
        }
        
        TodoScreen.Edit -> {
            TodoEditScreen(
                todoId = editingTodoId,
                onNavigateBack = {
                    currentScreen = TodoScreen.List
                    editingTodoId = null
                },
                modifier = modifier
            )
        }
    }
}

/**
 * Screen destinations for simple navigation
 */
sealed class TodoScreen {
    object List : TodoScreen()
    object Edit : TodoScreen()
}