package com.example.playground.ui.components.todo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.playground.domain.model.TodoItem
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * TodoListItem component that displays a single todo item with swipe-to-delete functionality.
 *
 * @param todo The todo item to display
 * @param onToggleComplete Callback when the completion checkbox is toggled
 * @param onRequestDelete Callback when the item is swiped to request deletion
 * @param onClick Callback when the item is clicked for editing
 * @param modifier Modifier for styling
 */
@Composable
fun TodoListItem(
    todo: TodoItem,
    onToggleComplete: (TodoItem) -> Unit,
    onRequestDelete: (TodoItem) -> Unit,
    onClick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDeleted by remember { mutableStateOf(false) }
    val swipeState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isDeleted = true
                return@rememberSwipeToDismissBoxState true
            }
            false
        }
    )

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            onRequestDelete(todo)
        }
    }

    SwipeToDismissBox(
        state = swipeState,
        backgroundContent = {
            SwipeDeleteBackground(
                swipeProgress = when (swipeState.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> swipeState.progress
                    else -> 0f
                }
            )
        },
        modifier = modifier
    ) {
        TodoItemCard(
            todo = todo,
            onToggleComplete = onToggleComplete,
            onClick = onClick
        )
    }
}

/**
 * The actual todo item card content
 */
@Composable
private fun TodoItemCard(
    todo: TodoItem,
    onToggleComplete: (TodoItem) -> Unit,
    onClick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val alpha = if (todo.isCompleted) 0.6f else 1f
    val textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None

    Card(
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = if (todo.isCompleted) {
                    "Completed task: ${todo.title}"
                } else {
                    "Incomplete task: ${todo.title}"
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .alpha(alpha),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggleComplete(todo) },
                modifier = Modifier.semantics {
                    contentDescription = if (todo.isCompleted) {
                        "Mark task as incomplete"
                    } else {
                        "Mark task as complete"
                    }
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onClick(todo)
                    }
                    .semantics {
                        contentDescription = "Tap to edit task: ${todo.title}"
                    }
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                    ),
                    color = if (todo.isCompleted) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    textDecoration = textDecoration,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (todo.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (todo.isCompleted) {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        textDecoration = textDecoration,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = formatTimestamp(todo.updatedAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Background content shown during swipe-to-delete with improved discoverability
 */
@Composable
private fun SwipeDeleteBackground(
    swipeProgress: Float,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            swipeProgress > 0.7f -> MaterialTheme.colorScheme.error
            swipeProgress > 0.3f -> MaterialTheme.colorScheme.errorContainer
            swipeProgress > 0.1f -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
            else -> Color.Transparent
        }
    )

    val iconScale by animateFloatAsState(
        targetValue = when {
            swipeProgress > 0.5f -> 1.2f
            swipeProgress > 0.2f -> 1f
            else -> 0.7f
        }
    )

    val iconTint by animateColorAsState(
        targetValue = when {
            swipeProgress > 0.7f -> MaterialTheme.colorScheme.onError
            swipeProgress > 0.3f -> MaterialTheme.colorScheme.onErrorContainer
            else -> MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.7f)
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (swipeProgress > 0.4f) {
                Text(
                    text = "Delete",
                    style = MaterialTheme.typography.labelMedium,
                    color = iconTint
                )
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete task",
                tint = iconTint,
                modifier = Modifier
                    .size(24.dp)
                    .scale(iconScale)
            )
        }
    }
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
private fun TodoListItemPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TodoListItem(
                todo = TodoItem(
                    id = 1,
                    title = "Complete project documentation",
                    description = "Write comprehensive documentation for the new feature implementation",
                    isCompleted = false,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                ),
                onToggleComplete = {},
                onRequestDelete = {},
                onClick = {}
            )

            TodoListItem(
                todo = TodoItem(
                    id = 2,
                    title = "Review pull request",
                    description = "",
                    isCompleted = true,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                ),
                onToggleComplete = {},
                onRequestDelete = {},
                onClick = {}
            )

            TodoListItem(
                todo = TodoItem(
                    id = 3,
                    title = "Very long task title that should wrap to multiple lines and show ellipsis",
                    description = "This is a very long description that should also wrap to multiple lines and show ellipsis when it exceeds the maximum number of lines allowed in the component",
                    isCompleted = false,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                ),
                onToggleComplete = {},
                onRequestDelete = {},
                onClick = {}
            )
        }
    }
}