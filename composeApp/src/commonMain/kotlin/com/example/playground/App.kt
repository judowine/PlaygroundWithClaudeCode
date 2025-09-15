package com.example.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playground.ui.components.todo.TodoListScreen
import com.example.playground.ui.theme.TodoAppTheme
import com.example.playground.ui.viewmodel.TodoListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    TodoAppTheme {
        val viewModel: TodoListViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()

        TodoListScreen(
            uiState = uiState,
            creationUiState = viewModel.creationUiState,
            onCreateTask = viewModel::showCreateTaskDialog,
            onToggleComplete = viewModel::toggleTaskCompletion,
            onRequestDelete = viewModel::requestDeleteTask,
            onConfirmDelete = viewModel::confirmDeleteTask,
            onDismissDeleteDialog = viewModel::dismissDeleteDialog,
            onEditTask = { /* TODO: Navigate to edit screen */ },
            onDismissCreation = viewModel::dismissCreateTaskDialog,
            onTitleChange = viewModel::updateCreationTitle,
            onDescriptionChange = viewModel::updateCreationDescription,
            onSaveTask = viewModel::saveNewTask,
            onDismissSuccessMessage = viewModel::dismissSuccessMessage
        )
    }
}