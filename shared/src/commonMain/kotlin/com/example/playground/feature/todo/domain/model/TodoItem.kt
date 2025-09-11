package com.example.playground.feature.todo.domain.model

import com.example.playground.getCurrentTimeMillis

/**
 * Domain model representing a TODO item in the business logic layer.
 * This model is platform-agnostic and contains all business rules and validation logic.
 */
data class TodoItem(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val completedAt: Long? = null
) {
    companion object {
        /**
         * Factory method to create a new TodoItem with validated input
         * @param title The title of the todo item (will be trimmed)
         * @param description Optional description (will be trimmed)
         * @return New TodoItem with current timestamp as createdAt
         */
        fun create(title: String, description: String = ""): TodoItem {
            return TodoItem(
                title = title.trim(),
                description = description.trim(),
                createdAt = getCurrentTimeMillis()
            )
        }
    }
    
    /**
     * Mark this todo item as completed
     * @return New TodoItem with isCompleted=true and completedAt set to current time
     */
    fun markCompleted(): TodoItem = copy(
        isCompleted = true,
        completedAt = getCurrentTimeMillis()
    )
    
    /**
     * Mark this todo item as uncompleted
     * @return New TodoItem with isCompleted=false and completedAt=null
     */
    fun markUncompleted(): TodoItem = copy(
        isCompleted = false,
        completedAt = null
    )
    
    /**
     * Update the title and description of this todo item
     * @param title New title (will be trimmed)
     * @param description New description (will be trimmed)
     * @return New TodoItem with updated title and description
     */
    fun update(title: String, description: String): TodoItem = copy(
        title = title.trim(),
        description = description.trim()
    )
}