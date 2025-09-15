package com.example.playground.domain.repository

import com.example.playground.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * TODOアイテムのデータアクセス用Repository interface
 * Clean Architectureの依存関係逆転の原則に従い、Domain層でインターフェースを定義
 * Data層で実装される
 */
interface TodoRepository {

    /**
     * 全TODOアイテムを取得
     * @return 全TODOアイテムのリスト（Result型でエラーハンドリング）
     */
    suspend fun getAllTodos(): Result<List<TodoItem>>

    /**
     * 全TODOアイテムをFlowで監視
     * リアルタイムでデータ変更を通知
     * @return TODOアイテムリストのFlow
     */
    fun observeAllTodos(): Flow<List<TodoItem>>

    /**
     * 指定IDのTODOアイテムを取得
     * @param id TODOアイテムのID
     * @return TODOアイテム（存在しない場合はエラー）
     */
    suspend fun getTodoById(id: Long): Result<TodoItem>

    /**
     * 新しいTODOアイテムを作成
     * @param todo 作成するTODOアイテム（IDは自動生成される）
     * @return 作成されたTODOアイテム（IDが設定済み）
     */
    suspend fun createTodo(todo: TodoItem): Result<TodoItem>

    /**
     * 既存のTODOアイテムを更新
     * @param todo 更新するTODOアイテム
     * @return 更新されたTODOアイテム
     */
    suspend fun updateTodo(todo: TodoItem): Result<TodoItem>

    /**
     * TODOアイテムを削除
     * @param id 削除するTODOアイテムのID
     * @return 削除成功時はUnit、失敗時はエラー
     */
    suspend fun deleteTodo(id: Long): Result<Unit>
}