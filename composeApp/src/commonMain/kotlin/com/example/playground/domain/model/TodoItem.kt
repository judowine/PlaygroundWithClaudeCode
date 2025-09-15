package com.example.playground.domain.model

import kotlinx.datetime.Instant

/**
 * Domain model representing a TODO item.
 *
 * @param id Unique identifier for the TODO item
 * @param title Required title/name of the task
 * @param description Optional detailed description
 * @param isCompleted Completion status of the task
 * @param createdAt Timestamp when the task was created
 * @param updatedAt Timestamp when the task was last modified
 */
data class TodoItem(
    val id: Long,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant = createdAt
) {
    /**
     * Creates a copy of this TodoItem with updated timestamp
     */
    fun markCompleted(): TodoItem = copy(
        isCompleted = true,
        updatedAt = kotlinx.datetime.Clock.System.now()
    )

    /**
     * Creates a copy of this TodoItem marked as incomplete with updated timestamp
     */
    fun markIncomplete(): TodoItem = copy(
        isCompleted = false,
        updatedAt = kotlinx.datetime.Clock.System.now()
    )

    /**
     * Updates the todo item with new title and description
     */
    fun update(title: String, description: String): TodoItem = copy(
        title = title,
        description = description,
        updatedAt = kotlinx.datetime.Clock.System.now()
    )
}