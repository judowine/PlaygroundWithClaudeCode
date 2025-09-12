package com.example.playground.shared.domain.repository

import com.example.playground.shared.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * TODOデータアクセスのリポジトリインターフェース
 * Design Docの仕様に基づくAPI定義
 */
interface TodoRepository {
    /**
     * すべてのTODOアイテムを取得（リアルタイム更新）
     * @return Flow<List<TodoItem>> - TODOアイテムのリアクティブストリーム
     */
    fun getAllTodos(): Flow<List<TodoItem>>
    
    /**
     * 完了状態でフィルタリングしたTODOアイテムを取得
     * @param isCompleted true: 完了済み, false: 未完了
     * @return Flow<List<TodoItem>>
     */
    fun getTodosByCompletion(isCompleted: Boolean): Flow<List<TodoItem>>
    
    /**
     * 特定のTODOアイテムを取得
     * @param id TODOアイテムID
     * @return Result<TodoItem>
     */
    suspend fun getTodoById(id: Long): Result<TodoItem>
    
    /**
     * TODOアイテムを作成
     * @param todo 作成するTODOアイテム（IDは自動生成）
     * @return Result<TodoItem> - 作成されたTODOアイテム（ID含む）
     */
    suspend fun createTodo(todo: TodoItem): Result<TodoItem>
    
    /**
     * TODOアイテムを更新
     * @param todo 更新するTODOアイテム
     * @return Result<TodoItem>
     */
    suspend fun updateTodo(todo: TodoItem): Result<TodoItem>
    
    /**
     * TODOアイテムを削除
     * @param id 削除するTODOアイテムのID
     * @return Result<Unit>
     */
    suspend fun deleteTodo(id: Long): Result<Unit>
    
    /**
     * TODOアイテムの完了状態を切り替え
     * @param id TODOアイテムID
     * @return Result<TodoItem> - 更新されたTODOアイテム
     */
    suspend fun toggleTodoCompletion(id: Long): Result<TodoItem>
}