package com.example.playground.ui.components.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.playground.domain.model.TodoItem
import com.example.playground.ui.state.DeleteConfirmationDialogState
import com.example.playground.ui.state.TodoCreationUiState
import com.example.playground.ui.state.TodoListUiState
import kotlinx.datetime.Clock
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Main screen displaying the list of TODO items with creation and management functionality.
 *
 * @param uiState Current UI state
 * @param creationUiState UI state for the creation bottom sheet
 * @param onCreateTask Callback to show task creation bottom sheet
 * @param onToggleComplete Callback when a task completion is toggled
 * @param onRequestDelete Callback when a task deletion is requested
 * @param onConfirmDelete Callback when deletion is confirmed
 * @param onDismissDeleteDialog Callback to dismiss delete confirmation dialog
 * @param onEditTask Callback when a task is clicked for editing
 * @param onDismissCreation Callback to dismiss creation bottom sheet
 * @param onTitleChange Callback when creation title changes
 * @param onDescriptionChange Callback when creation description changes
 * @param onSaveTask Callback when new task is saved
 * @param onDismissSuccessMessage Callback to dismiss success message
 * @param modifier Modifier for styling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    uiState: TodoListUiState,
    creationUiState: TodoCreationUiState,
    onCreateTask: () -> Unit,
    onToggleComplete: (TodoItem) -> Unit,
    onRequestDelete: (TodoItem) -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDeleteDialog: () -> Unit,
    onEditTask: (TodoItem) -> Unit,
    onDismissCreation: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSaveTask: () -> Unit,
    onDismissSuccessMessage: () -> Unit,
    isEditMode: Boolean = false,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Show error messages as snackbars
    uiState.errorMessage?.let { errorMessage ->
        LaunchedEffect(errorMessage) {
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    // Show success messages as snackbars
    uiState.successMessage?.let { successMessage ->
        LaunchedEffect(successMessage) {
            snackbarHostState.showSnackbar(successMessage)
            onDismissSuccessMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Tasks",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTask,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.semantics {
                    contentDescription = "Create new task"
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    // Loading state
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.semantics {
                                contentDescription = "Loading tasks"
                            }
                        )
                    }
                }

                uiState.isEmpty -> {
                    // Empty state
                    EmptyStateComponent(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    // Todo list content
                    TodoListContent(
                        todos = uiState.todos,
                        onToggleComplete = onToggleComplete,
                        onRequestDelete = onRequestDelete,
                        onEditTask = onEditTask
                    )
                }
            }
        }

        // Creation bottom sheet
        TodoCreationBottomSheet(
            isVisible = uiState.isCreationBottomSheetVisible,
            uiState = creationUiState,
            onDismiss = onDismissCreation,
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            onSave = onSaveTask,
            isEditMode = isEditMode
        )

        // Delete confirmation dialog
        uiState.deleteConfirmationDialog?.let { dialogState ->
            DeleteConfirmationDialog(
                todoTitle = dialogState.todoToDelete.title,
                onConfirm = onConfirmDelete,
                onDismiss = onDismissDeleteDialog
            )
        }
    }
}

/**
 * Delete confirmation dialog component
 */
@Composable
private fun DeleteConfirmationDialog(
    todoTitle: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Delete Task?",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete \"$todoTitle\"? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Content displaying the list of todos
 */
@Composable
private fun TodoListContent(
    todos: List<TodoItem>,
    onToggleComplete: (TodoItem) -> Unit,
    onRequestDelete: (TodoItem) -> Unit,
    onEditTask: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Group todos by completion status
        val incompleteTodos = todos.filter { !it.isCompleted }
        val completedTodos = todos.filter { it.isCompleted }

        if (incompleteTodos.isNotEmpty()) {
            if (completedTodos.isNotEmpty()) {
                item {
                    SectionHeader(text = "To Do")
                }
            }

            items(
                items = incompleteTodos,
                key = { it.id }
            ) { todo ->
                TodoListItem(
                    todo = todo,
                    onToggleComplete = onToggleComplete,
                    onRequestDelete = onRequestDelete,
                    onClick = onEditTask,
                    modifier = Modifier.animateItem()
                )
            }
        }

        if (completedTodos.isNotEmpty()) {
            item {
                SectionHeader(
                    text = "Completed (${completedTodos.size})",
                    modifier = Modifier.padding(
                        top = if (incompleteTodos.isNotEmpty()) 24.dp else 0.dp
                    )
                )
            }

            items(
                items = completedTodos,
                key = { it.id }
            ) { todo ->
                TodoListItem(
                    todo = todo,
                    onToggleComplete = onToggleComplete,
                    onRequestDelete = onRequestDelete,
                    onClick = onEditTask,
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

/**
 * Section header for grouping todos
 */
@Composable
private fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Preview
@Composable
private fun TodoListScreenPreview() {
    MaterialTheme {
        val sampleTodos = listOf(
            TodoItem(
                id = 1,
                title = "Complete project documentation",
                description = "Write comprehensive documentation for the new feature implementation",
                isCompleted = false,
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now()
            ),
            TodoItem(
                id = 2,
                title = "Review pull request",
                description = "",
                isCompleted = false,
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now()
            ),
            TodoItem(
                id = 3,
                title = "Update dependencies",
                description = "Update all dependencies to latest versions",
                isCompleted = true,
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now()
            )
        )

        TodoListScreen(
            uiState = TodoListUiState(todos = sampleTodos),
            creationUiState = TodoCreationUiState(),
            onCreateTask = { },
            onToggleComplete = { },
            onRequestDelete = { },
            onConfirmDelete = { },
            onDismissDeleteDialog = { },
            onEditTask = { },
            onDismissCreation = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onSaveTask = { },
            onDismissSuccessMessage = { }
        )
    }
}

@Preview
@Composable
private fun TodoListScreenEmptyPreview() {
    MaterialTheme {
        TodoListScreen(
            uiState = TodoListUiState(),
            creationUiState = TodoCreationUiState(),
            onCreateTask = { },
            onToggleComplete = { },
            onRequestDelete = { },
            onConfirmDelete = { },
            onDismissDeleteDialog = { },
            onEditTask = { },
            onDismissCreation = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onSaveTask = { },
            onDismissSuccessMessage = { }
        )
    }
}

@Preview
@Composable
private fun TodoListScreenLoadingPreview() {
    MaterialTheme {
        TodoListScreen(
            uiState = TodoListUiState(isLoading = true),
            creationUiState = TodoCreationUiState(),
            onCreateTask = { },
            onToggleComplete = { },
            onRequestDelete = { },
            onConfirmDelete = { },
            onDismissDeleteDialog = { },
            onEditTask = { },
            onDismissCreation = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onSaveTask = { },
            onDismissSuccessMessage = { }
        )
    }
}

@Preview
@Composable
private fun TodoListScreenWithDeleteDialogPreview() {
    MaterialTheme {
        val sampleTodo = TodoItem(
            id = 1,
            title = "Sample task to delete",
            description = "This task will be deleted",
            isCompleted = false,
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )

        TodoListScreen(
            uiState = TodoListUiState(
                todos = listOf(sampleTodo),
                deleteConfirmationDialog = DeleteConfirmationDialogState(todoToDelete = sampleTodo)
            ),
            creationUiState = TodoCreationUiState(),
            onCreateTask = { },
            onToggleComplete = { },
            onRequestDelete = { },
            onConfirmDelete = { },
            onDismissDeleteDialog = { },
            onEditTask = { },
            onDismissCreation = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onSaveTask = { },
            onDismissSuccessMessage = { }
        )
    }
}