package com.example.playground.feature.todo.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playground.feature.todo.domain.model.TodoItem

/**
 * Room database entity for storing TODO items in SQLite.
 * This entity represents the database table structure and is separate from
 * the domain model to maintain clean architecture separation.
 */
@Entity(tableName = "todo_items")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "title")
    val title: String,
    
    @ColumnInfo(name = "description")
    val description: String,
    
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    
    @ColumnInfo(name = "completed_at")
    val completedAt: Long?
)

/**
 * Extension function to convert domain model to database entity.
 * @return TodoEntity representation of this TodoItem
 */
fun TodoItem.toEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt,
    completedAt = completedAt
)

/**
 * Extension function to convert database entity to domain model.
 * @return TodoItem representation of this TodoEntity
 */
fun TodoEntity.toDomain(): TodoItem = TodoItem(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt,
    completedAt = completedAt
)