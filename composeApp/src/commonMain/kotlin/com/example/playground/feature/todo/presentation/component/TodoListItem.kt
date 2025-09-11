package com.example.playground.feature.todo.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.playground.feature.todo.domain.model.TodoItem

/**
 * Reusable component for displaying a single todo item in a list
 * Supports completion toggling, editing, deletion, and accessibility
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListItem(
    todo: TodoItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onToggleCompletion: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Static styling for completion state (animations will be added when dependencies are available)
    val staticAlpha = if (todo.isCompleted) 0.6f else 1f
    val staticBackgroundColor = if (todo.isCompleted) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.surface
    }
    
    ElevatedCard(
        modifier = modifier, // TODO: Add accessibility semantics when available
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (todo.isCompleted) 2.dp else 4.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = staticBackgroundColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(staticAlpha)
                .clickable(
                    onClick = onEditClick,
                    role = Role.Button
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Completion Checkbox
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggleCompletion() }
                // TODO: Add accessibility description when available
            )
            
            // Todo Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Title
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (todo.isCompleted) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Description (if not empty)
                if (todo.description.isNotBlank()) {
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = if (todo.isCompleted) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        },
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // Metadata
                TodoItemMetadata(
                    todo = todo,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            // Action Buttons
            TodoItemActions(
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                isCompleted = todo.isCompleted
            )
        }
    }
}

/**
 * Metadata display for the todo item (timestamps, etc.)
 */
@Composable
private fun TodoItemMetadata(
    todo: TodoItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Created date
        Text(
            text = "Created: ${formatTimestamp(todo.createdAt)}", // TODO: Implement proper date formatting
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        
        // Completed date (if applicable)
        if (todo.isCompleted) {
            todo.completedAt?.let { completedTime ->
                Text(
                    text = "Completed: ${formatTimestamp(completedTime)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

/**
 * Action buttons for the todo item
 */
@Composable
private fun TodoItemActions(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Edit Button
        TextButton(
            onClick = onEditClick,
            modifier = Modifier.height(40.dp)
        ) {
            Text(
                text = "✏️",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        // Delete Button
        TextButton(
            onClick = onDeleteClick,
            modifier = Modifier.height(40.dp)
        ) {
            Text(
                text = "🗑️",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/**
 * Compact version of TodoListItem for dense lists
 */
@Composable
fun CompactTodoListItem(
    todo: TodoItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onToggleCompletion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val staticAlpha = if (todo.isCompleted) 0.6f else 1f
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .alpha(staticAlpha)
            .clickable(onClick = onEditClick),
        color = if (todo.isCompleted) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            Color.Transparent
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggleCompletion() },
                modifier = Modifier.size(20.dp)
            )
            
            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textDecoration = if (todo.isCompleted) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            
            TextButton(
                onClick = onDeleteClick,
                modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = "🗑️",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

/**
 * Placeholder function for timestamp formatting
 * TODO: Replace with proper date/time formatting utility
 */
private fun formatTimestamp(timestamp: Long): String {
    // Simplified formatting - replace with proper date formatting
    return "TODO: Format $timestamp"
}