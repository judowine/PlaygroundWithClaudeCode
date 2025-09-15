package com.example.playground.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.domain.model.TodoItem
import com.example.playground.ui.state.DeleteConfirmationDialogState
import com.example.playground.ui.state.TodoCreationUiState
import com.example.playground.ui.state.TodoListUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

/**
 * ViewModel for managing the TodoList screen state and operations.
 * This is a prototype implementation using in-memory storage.
 */
class TodoListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    var creationUiState by mutableStateOf(TodoCreationUiState())
        private set

    // Currently editing todo item (null means creating new)
    private var editingTodo: TodoItem? = null

    /**
     * Whether we're currently in edit mode
     */
    val isEditMode: Boolean
        get() = editingTodo != null

    // In-memory storage for prototype
    private var todoList = mutableListOf<TodoItem>()
    private var nextId = 1L

    init {
        // Load sample data for demonstration
        loadSampleData()
    }

    /**
     * Shows the task creation bottom sheet
     */
    fun showCreateTaskDialog() {
        _uiState.value = _uiState.value.copy(isCreationBottomSheetVisible = true)
        creationUiState = TodoCreationUiState()
    }

    /**
     * Hides the task creation bottom sheet
     */
    fun dismissCreateTaskDialog() {
        _uiState.value = _uiState.value.copy(isCreationBottomSheetVisible = false)
        creationUiState = TodoCreationUiState()
        editingTodo = null
    }

    /**
     * Shows the task edit bottom sheet with pre-filled data
     */
    fun showEditTaskDialog(todo: TodoItem) {
        editingTodo = todo
        creationUiState = TodoCreationUiState(
            title = todo.title,
            description = todo.description
        )
        _uiState.value = _uiState.value.copy(isCreationBottomSheetVisible = true)
    }

    /**
     * Updates the title in the creation form
     */
    fun updateCreationTitle(title: String) {
        creationUiState = creationUiState.copy(
            title = title,
            titleError = if (title.isBlank()) "Title is required" else null
        )
    }

    /**
     * Updates the description in the creation form
     */
    fun updateCreationDescription(description: String) {
        creationUiState = creationUiState.copy(description = description)
    }

    /**
     * Saves a new task
     */
    fun saveNewTask() {
        if (!creationUiState.canSave) return

        viewModelScope.launch {
            creationUiState = creationUiState.copy(isLoading = true)

            // Simulate API call delay
            delay(500)

            val currentEditingTodo = editingTodo
            if (currentEditingTodo != null) {
                // Edit existing todo
                val updatedTodo = currentEditingTodo.copy(
                    title = creationUiState.title.trim(),
                    description = creationUiState.description.trim(),
                    updatedAt = Clock.System.now()
                )

                val index = todoList.indexOfFirst { it.id == currentEditingTodo.id }
                if (index >= 0) {
                    todoList[index] = updatedTodo
                }

                updateTodoList()
                showSuccessMessage("Task '${updatedTodo.title}' updated successfully!")
            } else {
                // Create new todo
                val newTodo = TodoItem(
                    id = nextId++,
                    title = creationUiState.title.trim(),
                    description = creationUiState.description.trim(),
                    isCompleted = false,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                )

                todoList.add(0, newTodo) // Add to beginning
                updateTodoList()
                showSuccessMessage("Task '${newTodo.title}' created successfully!")
            }

            dismissCreateTaskDialog()
        }
    }

    /**
     * Toggles the completion status of a task
     */
    fun toggleTaskCompletion(todo: TodoItem) {
        viewModelScope.launch {
            val updatedTodo = if (todo.isCompleted) {
                todo.markIncomplete()
            } else {
                todo.markCompleted()
            }

            val index = todoList.indexOfFirst { it.id == todo.id }
            if (index != -1) {
                todoList[index] = updatedTodo
                updateTodoList()

                // Show success message
                val action = if (updatedTodo.isCompleted) "completed" else "marked as incomplete"
                _uiState.value = _uiState.value.copy(
                    successMessage = "Task \"${updatedTodo.title}\" $action!"
                )
            }
        }
    }

    /**
     * Shows delete confirmation dialog for a task
     */
    fun requestDeleteTask(todo: TodoItem) {
        _uiState.value = _uiState.value.copy(
            deleteConfirmationDialog = DeleteConfirmationDialogState(todoToDelete = todo)
        )
    }

    /**
     * Confirms and deletes the task
     */
    fun confirmDeleteTask() {
        viewModelScope.launch {
            val dialogState = _uiState.value.deleteConfirmationDialog
            if (dialogState != null) {
                val todoToDelete = dialogState.todoToDelete
                todoList.removeAll { it.id == todoToDelete.id }
                updateTodoList()

                // Show success message
                _uiState.value = _uiState.value.copy(
                    successMessage = "Task \"${todoToDelete.title}\" deleted successfully!",
                    deleteConfirmationDialog = null
                )
            }
        }
    }

    /**
     * Dismisses the delete confirmation dialog
     */
    fun dismissDeleteDialog() {
        _uiState.value = _uiState.value.copy(deleteConfirmationDialog = null)
    }

    /**
     * Updates a task (placeholder for edit functionality)
     */
    fun updateTask(todo: TodoItem) {
        viewModelScope.launch {
            val index = todoList.indexOfFirst { it.id == todo.id }
            if (index != -1) {
                todoList[index] = todo
                updateTodoList()

                // Show success message
                _uiState.value = _uiState.value.copy(
                    successMessage = "Task \"${todo.title}\" updated successfully!"
                )
            }
        }
    }

    /**
     * Dismisses the current success message
     */
    fun dismissSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }

    /**
     * Shows a success message to the user
     */
    private fun showSuccessMessage(message: String) {
        _uiState.value = _uiState.value.copy(successMessage = message)
    }

    /**
     * Updates the UI state with current todo list
     */
    private fun updateTodoList() {
        _uiState.value = _uiState.value.copy(
            todos = todoList.toList(),
            isLoading = false
        )
    }

    /**
     * Loads sample data for demonstration
     */
    private fun loadSampleData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Simulate loading delay
            delay(1000)

            val sampleTodos = listOf(
                TodoItem(
                    id = nextId++,
                    title = "Complete project documentation",
                    description = "Write comprehensive documentation for the new feature implementation including API specs and user guides",
                    isCompleted = false,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                ),
                TodoItem(
                    id = nextId++,
                    title = "Review pull request #42",
                    description = "",
                    isCompleted = false,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                ),
                TodoItem(
                    id = nextId++,
                    title = "Update dependencies",
                    description = "Update all project dependencies to latest stable versions",
                    isCompleted = true,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                ),
                TodoItem(
                    id = nextId++,
                    title = "Fix login bug",
                    description = "Investigate and fix the authentication issue reported in issue #123",
                    isCompleted = true,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                )
            )

            todoList.addAll(sampleTodos)
            updateTodoList()
        }
    }
}