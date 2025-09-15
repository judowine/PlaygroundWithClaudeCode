package com.example.playground.ui.state

import com.example.playground.domain.model.TodoItem

/**
 * UI state for the TodoEdit screen
 *
 * @param originalTodo The original todo item being edited
 * @param title Current title input
 * @param description Current description input
 * @param titleError Error message for title field, null if valid
 * @param isLoading Whether save operation is in progress
 * @param hasUnsavedChanges Whether there are unsaved changes
 */
data class TodoEditUiState(
    val originalTodo: TodoItem? = null,
    val title: String = "",
    val description: String = "",
    val titleError: String? = null,
    val isLoading: Boolean = false,
    val hasUnsavedChanges: Boolean = false
) {
    /**
     * Whether the form is valid and can be saved
     */
    val isValid: Boolean
        get() = title.isNotBlank() && titleError == null

    /**
     * Whether the save button should be enabled
     */
    val canSave: Boolean
        get() = isValid && !isLoading && hasUnsavedChanges

    /**
     * Whether to show discard changes confirmation
     */
    val shouldShowDiscardDialog: Boolean
        get() = hasUnsavedChanges && originalTodo != null

    companion object {
        /**
         * Creates initial state for editing an existing todo
         */
        fun fromTodo(todo: TodoItem): TodoEditUiState = TodoEditUiState(
            originalTodo = todo,
            title = todo.title,
            description = todo.description,
            hasUnsavedChanges = false
        )
    }
}