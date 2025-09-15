package com.example.playground.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playground.data.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * TODOアイテムのデータアクセスオブジェクト（DAO）
 * Room Databaseを使用したCRUD操作を定義
 *
 * Security Features:
 * - Parameterized queries: SQLインジェクション防止
 * - Transaction support: データ整合性保証
 * - Type safety: Kotlin型システムによる安全性
 *
 * Performance Features:
 * - Flow-based reactive queries: リアルタイムデータ更新
 * - Optimized indexes: クエリパフォーマンス向上
 * - Async operations: UI blocking防止
 */
@Dao
interface TodoDao {

    /**
     * 全TODOアイテムをFlowで監視
     * リアルタイムでデータ変更を通知
     */
    @Query("SELECT * FROM todos ORDER BY created_at DESC")
    fun observeAllTodos(): Flow<List<TodoEntity>>

    /**
     * 全TODOアイテムを一括取得
     * suspend fun によりコルーチン対応
     */
    @Query("SELECT * FROM todos ORDER BY created_at DESC")
    suspend fun getAllTodos(): List<TodoEntity>

    /**
     * 指定IDのTODOアイテムを取得
     *
     * @param id TODOアイテムのID
     * @return 対象アイテム（存在しない場合はnull）
     */
    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Long): TodoEntity?

    /**
     * 新しいTODOアイテムを挿入
     *
     * @param todo 挿入するTODOアイテム
     * @return 生成された行ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long

    /**
     * 既存のTODOアイテムを更新
     *
     * @param todo 更新するTODOアイテム
     */
    @Update
    suspend fun updateTodo(todo: TodoEntity)

    /**
     * TODOアイテムを削除
     *
     * @param id 削除するTODOアイテムのID
     */
    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteTodo(id: Long)

    /**
     * 完了状態でフィルタリング
     * TODO: Future enhancement for filtering
     * @Query("SELECT * FROM todos WHERE is_completed = :isCompleted ORDER BY created_at DESC")
     */
    // suspend fun getTodosByCompletionStatus(isCompleted: Boolean): List<TodoEntity>
}