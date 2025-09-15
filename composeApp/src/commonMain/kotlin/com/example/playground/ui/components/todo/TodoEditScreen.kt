package com.example.playground.ui.components.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.playground.domain.model.TodoItem
import com.example.playground.ui.state.TodoEditUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Screen for editing an existing TODO item.
 *
 * @param uiState Current UI state for editing
 * @param onNavigateBack Callback to navigate back
 * @param onTitleChange Callback when title changes
 * @param onDescriptionChange Callback when description changes
 * @param onSave Callback when save is clicked
 * @param modifier Modifier for styling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(
    uiState: TodoEditUiState,
    onNavigateBack: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showDiscardDialog by remember { mutableStateOf(false) }

    val handleBackPress = {
        if (uiState.shouldShowDiscardDialog) {
            showDiscardDialog = true
        } else {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Task",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = handleBackPress,
                        modifier = Modifier.semantics {
                            contentDescription = "Navigate back"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onSave,
                        enabled = uiState.canSave,
                        modifier = Modifier.semantics {
                            contentDescription = if (uiState.canSave) {
                                "Save changes"
                            } else {
                                "Save disabled. Make changes or enter a valid title to save."
                            }
                        }
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.height(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Save"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { paddingValues ->
        TodoEditContent(
            uiState = uiState,
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )

        // Discard changes confirmation dialog
        if (showDiscardDialog) {
            DiscardChangesDialog(
                onConfirm = {
                    showDiscardDialog = false
                    onNavigateBack()
                },
                onDismiss = {
                    showDiscardDialog = false
                }
            )
        }
    }
}

/**
 * Content of the edit screen with form fields
 */
@Composable
private fun TodoEditContent(
    uiState: TodoEditUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val titleFocusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Task ID info (if available)
        uiState.originalTodo?.let { todo ->
            Text(
                text = "Task ID: ${todo.id}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Title field
        OutlinedTextField(
            value = uiState.title,
            onValueChange = onTitleChange,
            label = { Text("Task Title") },
            placeholder = { Text("Enter task title") },
            isError = uiState.titleError != null,
            supportingText = uiState.titleError?.let { { Text(it) } },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(titleFocusRequester)
                .semantics {
                    contentDescription = "Task title input field"
                }
        )

        // Description field
        OutlinedTextField(
            value = uiState.description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            placeholder = { Text("Enter task description") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            minLines = 3,
            maxLines = 6,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Task description input field"
                }
        )

        // Metadata (if available)
        uiState.originalTodo?.let { todo ->
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Task Information",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                MetadataRow(
                    label = "Status:",
                    value = if (todo.isCompleted) "Completed" else "Incomplete"
                )

                MetadataRow(
                    label = "Created:",
                    value = formatTimestamp(todo.createdAt)
                )

                if (todo.updatedAt != todo.createdAt) {
                    MetadataRow(
                        label = "Last updated:",
                        value = formatTimestamp(todo.updatedAt)
                    )
                }
            }
        }
    }

    // Auto-focus title field when screen opens
    LaunchedEffect(Unit) {
        titleFocusRequester.requestFocus()
    }
}

/**
 * Metadata row display component
 */
@Composable
private fun MetadataRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Dialog to confirm discarding unsaved changes
 */
@Composable
private fun DiscardChangesDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Discard changes?",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = "You have unsaved changes. Are you sure you want to go back and lose these changes?",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                modifier = Modifier.semantics {
                    contentDescription = "Confirm discard changes"
                }
            ) {
                Text("Discard")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.semantics {
                    contentDescription = "Keep editing"
                }
            ) {
                Text("Keep editing")
            }
        }
    )
}

/**
 * Formats timestamp for display
 */
private fun formatTimestamp(instant: kotlinx.datetime.Instant): String {
    return try {
        val localDateTime = instant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
        val dayOfWeek = when (localDateTime.dayOfWeek) {
            kotlinx.datetime.DayOfWeek.MONDAY -> "月"
            kotlinx.datetime.DayOfWeek.TUESDAY -> "火"
            kotlinx.datetime.DayOfWeek.WEDNESDAY -> "水"
            kotlinx.datetime.DayOfWeek.THURSDAY -> "木"
            kotlinx.datetime.DayOfWeek.FRIDAY -> "金"
            kotlinx.datetime.DayOfWeek.SATURDAY -> "土"
            kotlinx.datetime.DayOfWeek.SUNDAY -> "日"
            else -> "?"
        }
        "${localDateTime.year}/${localDateTime.monthNumber.toString().padStart(2, '0')}/${localDateTime.dayOfMonth.toString().padStart(2, '0')}(${dayOfWeek}) ${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
    } catch (e: Exception) {
        "Recently"
    }
}

@Preview
@Composable
private fun TodoEditScreenPreview() {
    MaterialTheme {
        val sampleTodo = TodoItem(
            id = 1,
            title = "Complete project documentation",
            description = "Write comprehensive documentation for the new feature implementation",
            isCompleted = false,
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )

        TodoEditScreen(
            uiState = TodoEditUiState.fromTodo(sampleTodo),
            onNavigateBack = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onSave = { }
        )
    }
}

@Preview
@Composable
private fun TodoEditScreenWithChangesPreview() {
    MaterialTheme {
        val sampleTodo = TodoItem(
            id = 1,
            title = "Complete project documentation",
            description = "Write comprehensive documentation for the new feature implementation",
            isCompleted = false,
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )

        TodoEditScreen(
            uiState = TodoEditUiState.fromTodo(sampleTodo).copy(
                title = "Updated project documentation",
                hasUnsavedChanges = true
            ),
            onNavigateBack = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onSave = { }
        )
    }
}

@Preview
@Composable
private fun DiscardChangesDialogPreview() {
    MaterialTheme {
        DiscardChangesDialog(
            onConfirm = { },
            onDismiss = { }
        )
    }
}