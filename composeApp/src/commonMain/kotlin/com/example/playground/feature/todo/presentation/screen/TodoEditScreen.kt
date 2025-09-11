package com.example.playground.feature.todo.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.playground.feature.todo.presentation.component.TodoForm
import com.example.playground.feature.todo.presentation.state.TodoEditUiState
import com.example.playground.feature.todo.presentation.viewmodel.TodoEditViewModel

/**
 * Screen for creating new todos or editing existing ones
 * Supports form validation, loading states, and proper keyboard handling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(
    todoId: Long? = null,
    viewModel: TodoEditViewModel = TODO("Inject via DI"), // TODO: Replace with actual DI
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    // Load todo if editing existing one
    LaunchedEffect(todoId) {
        if (todoId != null) {
            viewModel.loadTodo(todoId)
        }
    }
    
    // Handle save success navigation
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            // TODO: Hide keyboard when available
            viewModel.resetSavedState()
            onNavigateBack()
        }
    }
    
    TodoEditContent(
        uiState = uiState,
        onTitleChange = viewModel::updateTitle,
        onDescriptionChange = viewModel::updateDescription,
        onSaveClick = viewModel::saveTodo,
        onBackClick = onNavigateBack,
        onDismissError = viewModel::clearError,
        modifier = modifier
    )
}

/**
 * Content composable for TodoEditScreen
 * Separated for easier testing and preview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditContent(
    uiState: TodoEditUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TodoEditTopBar(
                isEditMode = uiState.isEditMode,
                canSave = uiState.canSave,
                isSaving = uiState.isSaving,
                onSaveClick = onSaveClick,
                onBackClick = onBackClick
            )
        },
        modifier = modifier
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            
            else -> {
                FormContent(
                    uiState = uiState,
                    onTitleChange = onTitleChange,
                    onDescriptionChange = onDescriptionChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
        
        // Error Snackbar
        if (uiState.errorMessage != null) {
            LaunchedEffect(uiState.errorMessage) {
                // TODO: Show snackbar with error message
                onDismissError()
            }
        }
    }
}

/**
 * Top app bar for the edit screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoEditTopBar(
    isEditMode: Boolean,
    canSave: Boolean,
    isSaving: Boolean,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = if (isEditMode) "Edit Todo" else "New Todo",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            TextButton(onClick = onBackClick) {
                Text(
                    text = "⬅️",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },
        actions = {
            if (isSaving) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            } else {
                TextButton(
                    onClick = onSaveClick,
                    enabled = canSave
                ) {
                    Text(
                        text = "✅",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

/**
 * Loading content while fetching todo data
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
                text = "Loading todo...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Form content for editing todo
 */
@Composable
private fun FormContent(
    uiState: TodoEditUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Instructions/Hints
        if (!uiState.isEditMode) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Create a new todo",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Fill in the details below to create your todo item",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        
        // Todo Form
        TodoForm(
            title = uiState.title,
            description = uiState.description,
            titleError = uiState.titleError,
            descriptionError = uiState.descriptionError,
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Form state indicators
        if (uiState.isModified) {
            Text(
                text = "• Unsaved changes",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        
        // Character count hints
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Title: ${uiState.title.length}/100",
                style = MaterialTheme.typography.bodySmall,
                color = if (uiState.title.length > 100) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            Text(
                text = "Description: ${uiState.description.length}/500",
                style = MaterialTheme.typography.bodySmall,
                color = if (uiState.description.length > 500) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
        
        // Action buttons at bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { /* TODO: Handle cancel with unsaved changes confirmation */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            
            Button(
                onClick = { /* TODO: Save handled by top bar */ },
                enabled = uiState.canSave,
                modifier = Modifier.weight(1f)
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (uiState.isEditMode) "Update" else "Create")
            }
        }
    }
}