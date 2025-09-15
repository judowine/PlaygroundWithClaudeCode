package com.example.playground.ui.components.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.playground.ui.state.TodoCreationUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Bottom sheet modal for creating new TODO items.
 *
 * @param isVisible Whether the bottom sheet is visible
 * @param uiState Current UI state for the creation form
 * @param onDismiss Callback when the bottom sheet is dismissed
 * @param onTitleChange Callback when title text changes
 * @param onDescriptionChange Callback when description text changes
 * @param onSave Callback when save button is clicked
 * @param modifier Modifier for styling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCreationBottomSheet(
    isVisible: Boolean,
    uiState: TodoCreationUiState,
    onDismiss: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            modifier = modifier
        ) {
            TodoCreationContent(
                uiState = uiState,
                onTitleChange = onTitleChange,
                onDescriptionChange = onDescriptionChange,
                onSave = onSave,
                onCancel = onDismiss
            )
        }
    }
}

/**
 * Content of the todo creation bottom sheet
 */
@Composable
private fun TodoCreationContent(
    uiState: TodoCreationUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val titleFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Create New Task",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

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
            label = { Text("Description (Optional)") },
            placeholder = { Text("Enter task description") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            maxLines = 3,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Task description input field"
                }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onCancel,
                enabled = !uiState.isLoading,
                modifier = Modifier.semantics {
                    contentDescription = "Cancel task creation"
                }
            ) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onSave,
                enabled = uiState.canSave,
                modifier = Modifier.semantics {
                    contentDescription = if (uiState.canSave) {
                        "Save new task"
                    } else {
                        "Save button disabled. Enter a task title to enable."
                    }
                }
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text("Save")
            }
        }
    }

    // Auto-focus title field when bottom sheet opens
    LaunchedEffect(Unit) {
        titleFocusRequester.requestFocus()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TodoCreationBottomSheetPreview() {
    MaterialTheme {
        var isVisible by remember { mutableStateOf(true) }
        var uiState by remember {
            mutableStateOf(
                TodoCreationUiState(
                    title = "Sample task",
                    description = "This is a sample description"
                )
            )
        }

        TodoCreationBottomSheet(
            isVisible = isVisible,
            uiState = uiState,
            onDismiss = { isVisible = false },
            onTitleChange = { uiState = uiState.copy(title = it) },
            onDescriptionChange = { uiState = uiState.copy(description = it) },
            onSave = { isVisible = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TodoCreationBottomSheetLoadingPreview() {
    MaterialTheme {
        var isVisible by remember { mutableStateOf(true) }
        val uiState = TodoCreationUiState(
            title = "Sample task",
            description = "This is a sample description",
            isLoading = true
        )

        TodoCreationBottomSheet(
            isVisible = isVisible,
            uiState = uiState,
            onDismiss = { isVisible = false },
            onTitleChange = { },
            onDescriptionChange = { },
            onSave = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TodoCreationBottomSheetErrorPreview() {
    MaterialTheme {
        var isVisible by remember { mutableStateOf(true) }
        val uiState = TodoCreationUiState(
            title = "",
            description = "",
            titleError = "Title is required"
        )

        TodoCreationBottomSheet(
            isVisible = isVisible,
            uiState = uiState,
            onDismiss = { isVisible = false },
            onTitleChange = { },
            onDescriptionChange = { },
            onSave = { }
        )
    }
}