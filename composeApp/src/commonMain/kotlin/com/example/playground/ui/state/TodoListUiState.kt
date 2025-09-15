package com.example.playground.ui.state

import com.example.playground.domain.model.TodoItem

/**
 * UI state for the TodoList screen
 *
 * @param todos List of all todo items to display
 * @param isLoading Whether the screen is in a loading state
 * @param errorMessage Error message to display, null if no error
 * @param isCreationBottomSheetVisible Whether the task creation bottom sheet is shown
 * @param deleteConfirmationDialog Delete confirmation dialog state, null if not shown
 * @param successMessage Success message to display, null if no message
 */
data class TodoListUiState(
    val todos: List<TodoItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isCreationBottomSheetVisible: Boolean = false,
    val deleteConfirmationDialog: DeleteConfirmationDialogState? = null,
    val successMessage: String? = null
) {
    /**
     * Returns true if there are no todos and not loading
     */
    val isEmpty: Boolean
        get() = todos.isEmpty() && !isLoading

    /**
     * Returns completed and incomplete todos separately
     */
    val completedTodos: List<TodoItem>
        get() = todos.filter { it.isCompleted }

    val incompleteTodos: List<TodoItem>
        get() = todos.filter { !it.isCompleted }
}

/**
 * UI state for the task creation bottom sheet
 *
 * @param title Current title input
 * @param description Current description input
 * @param titleError Error message for title field, null if valid
 * @param isLoading Whether save operation is in progress
 */
data class TodoCreationUiState(
    val title: String = "",
    val description: String = "",
    val titleError: String? = null,
    val isLoading: Boolean = false
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
        get() = isValid && !isLoading
}

/**
 * UI state for delete confirmation dialog
 *
 * @param todoToDelete The todo item to be deleted
 * @param isVisible Whether the dialog is currently visible
 */
data class DeleteConfirmationDialogState(
    val todoToDelete: TodoItem,
    val isVisible: Boolean = true
)