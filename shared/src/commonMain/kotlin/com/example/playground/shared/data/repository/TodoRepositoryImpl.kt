package com.example.playground.shared.data.repository

import com.example.playground.shared.data.mapper.TodoMapper
import com.example.playground.shared.data.mapper.toDomainModel
import com.example.playground.shared.database.TodoDatabase
import com.example.playground.shared.domain.model.TodoItem
import com.example.playground.shared.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList

/**
 * TodoRepositoryの実装
 * Design Docの仕様に基づくSQLDelightを使用したデータアクセス実装
 */
class TodoRepositoryImpl(
    private val database: TodoDatabase
) : TodoRepository {

    /**
     * すべてのTODOアイテムを取得（リアルタイム更新）
     */
    override fun getAllTodos(): Flow<List<TodoItem>> {
        return database.todoQueries.selectAllTodos()
            .asFlow()
            .mapToList()
            .map { entityList ->
                entityList.map { it.toDomainModel() }
            }
    }

    /**
     * 完了状態でフィルタリングしたTODOアイテムを取得
     */
    override fun getTodosByCompletion(isCompleted: Boolean): Flow<List<TodoItem>> {
        val completionValue = if (isCompleted) 1L else 0L
        return database.todoQueries.selectTodosByCompletion(completionValue)
            .asFlow()
            .mapToList()
            .map { entityList ->
                entityList.map { it.toDomainModel() }
            }
    }

    /**
     * 特定のTODOアイテムを取得
     */
    override suspend fun getTodoById(id: Long): Result<TodoItem> = runCatching {
        val entity = database.todoQueries.selectTodoById(id).executeAsOneOrNull()
            ?: throw NoSuchElementException("Todo with id $id not found")
        entity.toDomainModel()
    }

    /**
     * TODOアイテムを作成
     */
    override suspend fun createTodo(todo: TodoItem): Result<TodoItem> = runCatching {
        val (title, description, createdAt) = TodoMapper.fromDomainToInsertParams(todo)
        database.todoQueries.insertTodo(title, description, createdAt)
        
        // 作成されたアイテムのIDを取得してモデルを返す
        // TODO: 実際の実装では last_insert_rowid() などを使用
        val insertedId = database.todoQueries.selectAllTodos().executeAsList().maxOfOrNull { it.id } ?: 0L
        todo.copy(id = insertedId)
    }

    /**
     * TODOアイテムを更新
     */
    override suspend fun updateTodo(todo: TodoItem): Result<TodoItem> = runCatching {
        val params = TodoMapper.fromDomainToUpdateParams(todo)
        database.todoQueries.updateTodo(
            title = params[0] as String,
            description = params[1] as String,
            is_completed = params[2] as Long,
            completed_at = params[3] as Long?,
            id = params[4] as Long
        )
        todo
    }

    /**
     * TODOアイテムを削除
     */
    override suspend fun deleteTodo(id: Long): Result<Unit> = runCatching {
        database.todoQueries.deleteTodo(id)
    }

    /**
     * TODOアイテムの完了状態を切り替え
     */
    override suspend fun toggleTodoCompletion(id: Long): Result<TodoItem> = runCatching {
        val currentTodo = getTodoById(id).getOrThrow()
        val updatedTodo = currentTodo.copy(
            isCompleted = !currentTodo.isCompleted,
            completedAt = if (!currentTodo.isCompleted) 
                Clock.System.now().epochSeconds 
            else null
        )
        updateTodo(updatedTodo).getOrThrow()
        updatedTodo
    }
}