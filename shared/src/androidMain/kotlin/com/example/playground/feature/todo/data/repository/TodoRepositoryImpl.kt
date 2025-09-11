package com.example.playground.feature.todo.data.repository

import android.database.sqlite.SQLiteException
import com.example.playground.feature.todo.data.database.dao.TodoDao
import com.example.playground.feature.todo.data.database.entity.toDomain
import com.example.playground.feature.todo.data.database.entity.toEntity
import com.example.playground.feature.todo.domain.exception.TodoException
import com.example.playground.feature.todo.domain.model.TodoItem
import com.example.playground.feature.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Android-specific implementation of TodoRepository using Room database.
 * Handles data persistence through SQLite and provides error handling
 * with proper exception mapping from database errors to domain exceptions.
 */
class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {
    
    /**
     * Retrieve all TODO items from the database
     * @return Result containing list of TodoItem or error information
     */
    override suspend fun getAllTodos(): Result<List<TodoItem>> = try {
        val entities = todoDao.getAllTodos()
        val todoItems = entities.map { it.toDomain() }
        Result.success(todoItems)
    } catch (e: SQLiteException) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "getAllTodos",
                cause = e
            )
        )
    } catch (e: Exception) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "getAllTodos",
                cause = e
            )
        )
    }
    
    /**
     * Retrieve a specific TODO item by its ID
     * @param id The unique identifier of the TODO item
     * @return Result containing TodoItem if found, null if not found, or error information
     */
    override suspend fun getTodoById(id: Long): Result<TodoItem?> = try {
        val entity = todoDao.getTodoById(id)
        val todoItem = entity?.toDomain()
        Result.success(todoItem)
    } catch (e: SQLiteException) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "getTodoById",
                cause = e
            )
        )
    } catch (e: Exception) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "getTodoById", 
                cause = e
            )
        )
    }
    
    /**
     * Create a new TODO item in the database
     * @param todo The TodoItem to create (ID will be generated)
     * @return Result containing the created TodoItem with generated ID or error information
     */
    override suspend fun createTodo(todo: TodoItem): Result<TodoItem> = try {
        val entity = todo.toEntity()
        val generatedId = todoDao.insertTodo(entity)
        val createdTodo = todo.copy(id = generatedId)
        Result.success(createdTodo)
    } catch (e: SQLiteException) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "createTodo",
                cause = e
            )
        )
    } catch (e: Exception) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "createTodo",
                cause = e
            )
        )
    }
    
    /**
     * Update an existing TODO item in the database
     * @param todo The TodoItem with updated information
     * @return Result containing the updated TodoItem or error information
     */
    override suspend fun updateTodo(todo: TodoItem): Result<TodoItem> = try {
        val entity = todo.toEntity()
        todoDao.updateTodo(entity)
        Result.success(todo)
    } catch (e: SQLiteException) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "updateTodo",
                cause = e
            )
        )
    } catch (e: Exception) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "updateTodo",
                cause = e
            )
        )
    }
    
    /**
     * Delete a TODO item from the database
     * @param id The unique identifier of the TODO item to delete
     * @return Result indicating success (Unit) or error information
     */
    override suspend fun deleteTodo(id: Long): Result<Unit> = try {
        todoDao.deleteTodoById(id)
        Result.success(Unit)
    } catch (e: SQLiteException) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "deleteTodo",
                cause = e
            )
        )
    } catch (e: Exception) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "deleteTodo",
                cause = e
            )
        )
    }
    
    /**
     * Toggle the completion status of a TODO item
     * @param id The unique identifier of the TODO item to toggle
     * @return Result containing the updated TodoItem with toggled completion status
     */
    override suspend fun toggleTodoCompletion(id: Long): Result<TodoItem> = try {
        val entity = todoDao.getTodoById(id)
            ?: return Result.failure(TodoException.NotFoundException(id))
        
        val currentTodo = entity.toDomain()
        val updatedTodo = if (currentTodo.isCompleted) {
            currentTodo.markUncompleted()
        } else {
            currentTodo.markCompleted()
        }
        
        todoDao.updateTodo(updatedTodo.toEntity())
        Result.success(updatedTodo)
    } catch (e: SQLiteException) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "toggleTodoCompletion",
                cause = e
            )
        )
    } catch (e: Exception) {
        Result.failure(
            TodoException.DatabaseException(
                operation = "toggleTodoCompletion",
                cause = e
            )
        )
    }
    
    /**
     * Observe all TODO items for real-time updates
     * @return Flow that emits the current list of TodoItem whenever data changes
     */
    override fun observeTodos(): Flow<List<TodoItem>> {
        return todoDao.observeAllTodos().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}