package com.example.playground.feature.todo.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Reusable form component for todo creation and editing
 * Provides Material Design 3 text fields with validation, keyboard handling, and accessibility
 */
@Composable
fun TodoForm(
    title: String,
    description: String,
    titleError: String? = null,
    descriptionError: String? = null,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    autoFocusTitle: Boolean = true
) {
    val focusManager = LocalFocusManager.current
    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }
    
    // Auto-focus title field when form loads
    LaunchedEffect(autoFocusTitle) {
        if (autoFocusTitle && enabled) {
            titleFocusRequester.requestFocus()
        }
    }
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title Field
        TodoTitleField(
            value = title,
            onValueChange = onTitleChange,
            error = titleError,
            enabled = enabled,
            focusRequester = titleFocusRequester,
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
        
        // Description Field  
        TodoDescriptionField(
            value = description,
            onValueChange = onDescriptionChange,
            error = descriptionError,
            enabled = enabled,
            focusRequester = descriptionFocusRequester,
            onDone = {
                focusManager.clearFocus()
            }
        )
        
        // Form hints and tips
        TodoFormHints(
            titleLength = title.length,
            descriptionLength = description.length
        )
    }
}

/**
 * Title input field with validation
 */
@Composable
private fun TodoTitleField(
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    enabled: Boolean,
    focusRequester: FocusRequester,
    onNext: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Title") },
        placeholder = { Text("Enter todo title...") },
        supportingText = {
            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Text("Required field")
            }
        },
        isError = error != null,
        enabled = enabled,
        singleLine = true,
        trailingIcon = {
            if (value.isNotEmpty() && enabled) {
                TextButton(onClick = { onValueChange("") }) {
                    Text(
                        text = "✖️",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            // TODO: Add accessibility semantics when available
    )
}

/**
 * Description input field with multi-line support
 */
@Composable
private fun TodoDescriptionField(
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    enabled: Boolean,
    focusRequester: FocusRequester,
    onDone: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Description") },
        placeholder = { Text("Enter additional details (optional)...") },
        supportingText = {
            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Text("Optional field")
            }
        },
        isError = error != null,
        enabled = enabled,
        minLines = 3,
        maxLines = 6,
        trailingIcon = {
            if (value.isNotEmpty() && enabled) {
                TextButton(onClick = { onValueChange("") }) {
                    Text(
                        text = "✖️",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            // TODO: Add accessibility semantics when available
    )
}

/**
 * Form hints and character count indicators
 */
@Composable
private fun TodoFormHints(
    titleLength: Int,
    descriptionLength: Int
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Form Guidelines",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$titleLength / 100 characters",
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            titleLength == 0 -> MaterialTheme.colorScheme.error
                            titleLength > 100 -> MaterialTheme.colorScheme.error
                            titleLength > 80 -> MaterialTheme.colorScheme.tertiary
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
                
                Column {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$descriptionLength / 500 characters",
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            descriptionLength > 500 -> MaterialTheme.colorScheme.error
                            descriptionLength > 400 -> MaterialTheme.colorScheme.tertiary
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
            
            // Validation indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ValidationIndicator(
                    label = "Title required",
                    isValid = titleLength > 0,
                    modifier = Modifier.weight(1f)
                )
                ValidationIndicator(
                    label = "Length limits",
                    isValid = titleLength <= 100 && descriptionLength <= 500,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Visual indicator for form validation status
 */
@Composable
private fun ValidationIndicator(
    label: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Surface(
            modifier = Modifier.size(8.dp),
            shape = androidx.compose.foundation.shape.CircleShape,
            color = if (isValid) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.error
            }
        ) {}
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = if (isValid) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.error
            }
        )
    }
}

/**
 * Compact version of TodoForm for smaller screens or modal usage
 */
@Composable
fun CompactTodoForm(
    title: String,
    description: String,
    titleError: String? = null,
    descriptionError: String? = null,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Simplified title field
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            placeholder = { Text("Todo title...") },
            isError = titleError != null,
            enabled = enabled,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Simplified description field
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            placeholder = { Text("Details (optional)...") },
            isError = descriptionError != null,
            enabled = enabled,
            maxLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Error messages
        if (titleError != null || descriptionError != null) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                titleError?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                descriptionError?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}