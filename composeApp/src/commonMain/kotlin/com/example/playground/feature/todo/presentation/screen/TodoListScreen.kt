package com.example.playground.feature.todo.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.playground.feature.todo.presentation.component.TodoListItem
import com.example.playground.feature.todo.presentation.state.TodoListUiState
import com.example.playground.feature.todo.presentation.viewmodel.TodoListViewModel

/**
 * Main screen displaying the list of todo items
 * Supports Material Design 3, pull-to-refresh, and empty states
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = TODO("Inject via DI"), // TODO: Replace with actual DI
    onNavigateToEdit: (Long?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Handle save confirmation result if needed
    LaunchedEffect(Unit) {
        // TODO: Handle any side effects from navigation
    }
    
    TodoListContent(
        uiState = uiState,
        onCreateClick = { onNavigateToEdit(null) },
        onEditClick = { todoId -> onNavigateToEdit(todoId) },
        onDeleteClick = viewModel::showDeleteConfirmation,
        onToggleCompletion = viewModel::toggleCompletion,
        onRefresh = viewModel::refresh,
        onDismissError = viewModel::clearError,
        onConfirmDelete = viewModel::confirmDelete,
        onDismissDeleteDialog = viewModel::hideDeleteConfirmation,
        modifier = modifier
    )
}

/**
 * Content composable for TodoListScreen
 * Separated for easier testing and preview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListContent(
    uiState: TodoListUiState,
    onCreateClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (com.example.playground.feature.todo.domain.model.TodoItem) -> Unit,
    onToggleCompletion: (Long) -> Unit,
    onRefresh: () -> Unit,
    onDismissError: () -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDeleteDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TodoListTopBar()
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onCreateClick
                    // TODO: Add accessibility semantics when available
                ) {
                    Text(
                        text = "➕",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        ) { paddingValues ->
            when {
                uiState.isLoading -> {
                    LoadingContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
                
                uiState.isEmpty -> {
                    EmptyContent(
                        onCreateClick = onCreateClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
                
                else -> {
                    TodoListWithPullRefresh(
                        todos = uiState.todos,
                        isRefreshing = uiState.isRefreshing,
                        onRefresh = onRefresh,
                        onEditClick = onEditClick,
                        onDeleteClick = onDeleteClick,
                        onToggleCompletion = onToggleCompletion,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }
        }
        
        // Error Snackbar
        if (uiState.hasError) {
            LaunchedEffect(uiState.errorMessage) {
                // TODO: Show snackbar with error message
                onDismissError()
            }
        }
        
        // Delete Confirmation Dialog
        if (uiState.showDeleteConfirmation != null) {
            DeleteConfirmationDialog(
                todo = uiState.showDeleteConfirmation,
                onConfirm = onConfirmDelete,
                onDismiss = onDismissDeleteDialog
            )
        }
    }
}

/**
 * Top app bar for the todo list screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoListTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "My Todos",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

/**
 * Loading state content
 */
@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator()
            Text(
                text = "Loading todos...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Empty state content when no todos are available
 */
@Composable
private fun EmptyContent(
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "No todos yet",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Create your first todo to get started",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            OutlinedButton(
                onClick = onCreateClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("➕")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Create Todo")
            }
        }
    }
}

/**
 * Todo list with pull-to-refresh functionality
 */
@Composable
private fun TodoListWithPullRefresh(
    todos: List<com.example.playground.feature.todo.domain.model.TodoItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (com.example.playground.feature.todo.domain.model.TodoItem) -> Unit,
    onToggleCompletion: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implement actual pull-to-refresh when available in Compose Multiplatform
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = todos,
            key = { it.id }
        ) { todo ->
            TodoListItem(
                todo = todo,
                onEditClick = { onEditClick(todo.id) },
                onDeleteClick = { onDeleteClick(todo) },
                onToggleCompletion = { onToggleCompletion(todo.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

/**
 * Delete confirmation dialog
 */
@Composable
private fun DeleteConfirmationDialog(
    todo: com.example.playground.feature.todo.domain.model.TodoItem,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Delete Todo")
        },
        text = {
            Text("Are you sure you want to delete \"${todo.title}\"? This action cannot be undone.")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}