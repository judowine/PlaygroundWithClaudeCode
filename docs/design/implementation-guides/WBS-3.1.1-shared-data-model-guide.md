# Implementation Guide: WBS-3.1.1 共通データモデル実装

## 基本情報
- **タスクID**: WBS-3.1.1
- **タスク名**: 共通データモデル実装
- **関連Design Doc**: WBS-1.3.1-architecture-design.md
- **作成日**: 2025-09-02
- **実装者**: Architecture Strategist + Frontend Generalist Dev
- **見積もり工数**: 1.5人日（12時間）

## 実装概要
### 実装目標
Kotlin Multiplatform TODOアプリケーションの共通データレイヤーを実装し、5プラットフォーム（Android、iOS、Web、Desktop、Server）で共有するデータモデル、Repository Interface、Use Case層を構築する。SQLDelightとkotlinx-serializationを活用したタイプセーフで高性能なデータ管理基盤を提供する。

### 成果物
- [ ] shared/commonMain データモデル（TodoEntity、UserEntity、SyncMetadata）
- [ ] Repository Interface（TodoRepository、UserRepository、SyncRepository）
- [ ] Use Case実装（GetTodos、CreateTodo、UpdateTodo、DeleteTodo、SyncTodos）
- [ ] エラーハンドリングとバリデーション機能
- [ ] 包括的なテストスイート（データモデル、Repository、UseCase）

### 前提条件
- [ ] Phase 1: 設計・基盤構築完了（アーキテクチャ設計承認済み）
- [ ] Kotlin Multiplatform環境構築済み
- [ ] Compose Multiplatform 1.6.x、Ktor 2.3.x利用可能

## 実装手順

### Step 1: 環境準備
#### 1.1 依存関係の追加
```bash
# shared/build.gradle.kts に必要な依存関係を追加
```

```kotlin
// shared/build.gradle.kts - 追加する依存関係
sourceSets {
    commonMain.dependencies {
        // Coroutines & Serialization
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
        
        // Network & HTTP Client
        implementation("io.ktor:ktor-client-core:2.3.5")
        implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
        implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
        implementation("io.ktor:ktor-client-auth:2.3.5")
        implementation("io.ktor:ktor-client-logging:2.3.5")
        
        // Local Database
        implementation("app.cash.sqldelight:runtime:2.0.0")
        implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")
        
        // Dependency Injection
        implementation("io.insert-koin:koin-core:3.5.0")
        
        // Logging
        implementation("io.github.aakira:napier:2.7.1")
    }
    
    commonTest.dependencies {
        implementation(libs.kotlin.test)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
        implementation("io.insert-koin:koin-test:3.5.0")
    }
    
    androidMain.dependencies {
        implementation("io.ktor:ktor-client-android:2.3.5")
        implementation("app.cash.sqldelight:android-driver:2.0.0")
        implementation("androidx.datastore:datastore-preferences:1.0.0")
    }
    
    iosMain.dependencies {
        implementation("io.ktor:ktor-client-darwin:2.3.5")
        implementation("app.cash.sqldelight:native-driver:2.0.0")
    }
    
    jvmMain.dependencies {
        implementation("io.ktor:ktor-client-cio:2.3.5")
        implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
    }
    
    wasmJsMain.dependencies {
        implementation("io.ktor:ktor-client-js:2.3.5")
        implementation("app.cash.sqldelight:web-worker-driver:2.0.0")
    }
}
```

#### 1.2 必要なディレクトリ・ファイルの作成
```bash
# データモデル用ディレクトリ構造の作成
mkdir -p shared/src/commonMain/kotlin/com/example/playground/data/model
mkdir -p shared/src/commonMain/kotlin/com/example/playground/data/repository
mkdir -p shared/src/commonMain/kotlin/com/example/playground/data/local
mkdir -p shared/src/commonMain/kotlin/com/example/playground/data/remote
mkdir -p shared/src/commonMain/kotlin/com/example/playground/domain/repository
mkdir -p shared/src/commonMain/kotlin/com/example/playground/domain/usecase
mkdir -p shared/src/commonMain/kotlin/com/example/playground/domain/error
mkdir -p shared/src/commonTest/kotlin/com/example/playground/data
mkdir -p shared/src/commonTest/kotlin/com/example/playground/domain
```

#### 1.3 設定ファイルの更新
```kotlin
// shared/build.gradle.kts - プラグインの追加
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    id("app.cash.sqldelight") version "2.0.0"
}

// SQLDelight設定
sqldelight {
    databases {
        create("TodoDatabase") {
            packageName.set("com.example.playground.data.local")
            srcDirs.setFrom("src/commonMain/sqldelight")
        }
    }
}
```

### Step 2: データ層実装
#### 2.1 データモデルの作成
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/data/model/TodoEntity.kt`

```kotlin
package com.example.playground.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class TodoEntity(
    val id: String = generateId(),
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: TodoPriority = TodoPriority.MEDIUM,
    val dueDate: LocalDateTime? = null,
    val createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val updatedAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val syncStatus: SyncStatus = SyncStatus.SYNCED,
    val version: Long = 1L
) {
    init {
        require(title.trim().isNotEmpty()) { "Title cannot be empty" }
        require(title.length <= 200) { "Title must be 200 characters or less" }
        require(description.length <= 2000) { "Description must be 2000 characters or less" }
        require(version > 0) { "Version must be positive" }
    }
}

@Serializable
enum class TodoPriority(val value: String) {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low")
}

@Serializable
enum class SyncStatus(val value: String) {
    SYNCED("synced"),
    PENDING_SYNC("pending_sync"),
    CONFLICT("conflict")
}

@OptIn(ExperimentalUuidApi::class)
private fun generateId(): String = Uuid.random().toString()
```

**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/data/model/UserEntity.kt`

```kotlin
package com.example.playground.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Serializable
data class UserEntity(
    val id: String,
    val email: String,
    val displayName: String,
    val createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val lastSyncedAt: LocalDateTime? = null
) {
    init {
        require(email.isValidEmail()) { "Invalid email format" }
        require(displayName.trim().isNotEmpty()) { "Display name cannot be empty" }
        require(displayName.length <= 100) { "Display name must be 100 characters or less" }
    }
}

private fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$".toRegex()
    return emailRegex.matches(this)
}
```

**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/data/model/SyncMetadata.kt`

```kotlin
package com.example.playground.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Serializable
data class SyncMetadata(
    val entityId: String,
    val entityType: String,
    val lastModified: LocalDateTime,
    val version: Long,
    val deviceId: String,
    val changeType: ChangeType = ChangeType.UPDATE
) {
    init {
        require(entityId.isNotBlank()) { "Entity ID cannot be blank" }
        require(entityType.isNotBlank()) { "Entity type cannot be blank" }
        require(deviceId.isNotBlank()) { "Device ID cannot be blank" }
        require(version > 0) { "Version must be positive" }
    }
}

@Serializable
enum class ChangeType(val value: String) {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete")
}

@Serializable
data class SyncResult(
    val syncedCount: Int,
    val conflictsResolved: Int,
    val lastSyncTime: LocalDateTime,
    val errors: List<String> = emptyList()
)
```

#### 2.2 Repository Interfaceの作成
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/domain/repository/TodoRepository.kt`

```kotlin
package com.example.playground.domain.repository

import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.SyncResult
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    // Basic CRUD operations
    suspend fun getAllTodos(): Result<List<TodoEntity>>
    suspend fun getTodoById(id: String): Result<TodoEntity?>
    suspend fun insertTodo(todo: TodoEntity): Result<TodoEntity>
    suspend fun updateTodo(todo: TodoEntity): Result<TodoEntity>
    suspend fun deleteTodo(id: String): Result<Unit>
    
    // Query operations
    suspend fun getTodosByStatus(isCompleted: Boolean): Result<List<TodoEntity>>
    suspend fun getTodosByPriority(priority: String): Result<List<TodoEntity>>
    suspend fun searchTodos(query: String): Result<List<TodoEntity>>
    
    // Sync operations
    suspend fun syncWithRemote(): Result<SyncResult>
    suspend fun getPendingChanges(): Result<List<TodoEntity>>
    suspend fun markAsSynced(todoIds: List<String>): Result<Unit>
    
    // Reactive streams
    fun observeTodos(): Flow<List<TodoEntity>>
    fun observeTodoById(id: String): Flow<TodoEntity?>
}
```

**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/domain/repository/UserRepository.kt`

```kotlin
package com.example.playground.domain.repository

import com.example.playground.data.model.UserEntity

interface UserRepository {
    suspend fun getCurrentUser(): Result<UserEntity?>
    suspend fun saveUser(user: UserEntity): Result<UserEntity>
    suspend fun clearUser(): Result<Unit>
    suspend fun updateLastSyncTime(): Result<Unit>
}
```

#### 2.3 Repository実装の作成
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/data/repository/TodoRepositoryImpl.kt`

```kotlin
package com.example.playground.data.repository

import com.example.playground.data.local.TodoLocalDataSource
import com.example.playground.data.remote.TodoRemoteDataSource
import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.SyncResult
import com.example.playground.data.model.SyncStatus
import com.example.playground.domain.repository.TodoRepository
import com.example.playground.domain.error.TodoError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TodoRepositoryImpl(
    private val localDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource,
    private val syncManager: SyncManager
) : TodoRepository {
    
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override suspend fun getAllTodos(): Result<List<TodoEntity>> = withContext(Dispatchers.IO) {
        try {
            val localTodos = localDataSource.getAllTodos()
            // バックグラウンド同期をトリガー
            repositoryScope.launch { syncManager.scheduleSyncIfNeeded() }
            Result.success(localTodos)
        } catch (e: Exception) {
            Result.failure(TodoError.DatabaseError(e.message ?: "Unknown database error"))
        }
    }
    
    override suspend fun getTodoById(id: String): Result<TodoEntity?> = withContext(Dispatchers.IO) {
        try {
            val todo = localDataSource.getTodoById(id)
            Result.success(todo)
        } catch (e: Exception) {
            Result.failure(TodoError.DatabaseError(e.message ?: "Failed to get todo"))
        }
    }
    
    override suspend fun insertTodo(todo: TodoEntity): Result<TodoEntity> = withContext(Dispatchers.IO) {
        try {
            // ローカル保存（同期待ち状態）
            val todoWithSyncStatus = todo.copy(syncStatus = SyncStatus.PENDING_SYNC)
            val savedTodo = localDataSource.insertTodo(todoWithSyncStatus)
            
            // バックグラウンド同期
            repositoryScope.launch { syncManager.syncSingle(savedTodo) }
            
            Result.success(savedTodo)
        } catch (e: Exception) {
            Result.failure(TodoError.DatabaseError(e.message ?: "Failed to insert todo"))
        }
    }
    
    override suspend fun updateTodo(todo: TodoEntity): Result<TodoEntity> = withContext(Dispatchers.IO) {
        try {
            val updatedTodo = todo.copy(
                syncStatus = SyncStatus.PENDING_SYNC,
                version = todo.version + 1
            )
            val savedTodo = localDataSource.updateTodo(updatedTodo)
            
            // バックグラウンド同期
            repositoryScope.launch { syncManager.syncSingle(savedTodo) }
            
            Result.success(savedTodo)
        } catch (e: Exception) {
            Result.failure(TodoError.DatabaseError(e.message ?: "Failed to update todo"))
        }
    }
    
    override suspend fun deleteTodo(id: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            localDataSource.deleteTodo(id)
            // バックグラウンド同期（削除の同期）
            repositoryScope.launch { syncManager.syncDeletion(id) }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TodoError.DatabaseError(e.message ?: "Failed to delete todo"))
        }
    }
    
    override suspend fun getTodosByStatus(isCompleted: Boolean): Result<List<TodoEntity>> = 
        withContext(Dispatchers.IO) {
            try {
                val todos = localDataSource.getTodosByStatus(isCompleted)
                Result.success(todos)
            } catch (e: Exception) {
                Result.failure(TodoError.DatabaseError(e.message ?: "Failed to get todos by status"))
            }
        }
    
    override suspend fun getTodosByPriority(priority: String): Result<List<TodoEntity>> = 
        withContext(Dispatchers.IO) {
            try {
                val todos = localDataSource.getTodosByPriority(priority)
                Result.success(todos)
            } catch (e: Exception) {
                Result.failure(TodoError.DatabaseError(e.message ?: "Failed to get todos by priority"))
            }
        }
    
    override suspend fun searchTodos(query: String): Result<List<TodoEntity>> = 
        withContext(Dispatchers.IO) {
            try {
                val todos = localDataSource.searchTodos(query)
                Result.success(todos)
            } catch (e: Exception) {
                Result.failure(TodoError.DatabaseError(e.message ?: "Failed to search todos"))
            }
        }
    
    override suspend fun syncWithRemote(): Result<SyncResult> = withContext(Dispatchers.IO) {
        try {
            syncManager.syncAll()
        } catch (e: Exception) {
            Result.failure(TodoError.SyncError(e.message ?: "Sync failed"))
        }
    }
    
    override suspend fun getPendingChanges(): Result<List<TodoEntity>> = withContext(Dispatchers.IO) {
        try {
            val pendingTodos = localDataSource.getPendingChanges()
            Result.success(pendingTodos)
        } catch (e: Exception) {
            Result.failure(TodoError.DatabaseError(e.message ?: "Failed to get pending changes"))
        }
    }
    
    override suspend fun markAsSynced(todoIds: List<String>): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            localDataSource.markAsSynced(todoIds)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TodoError.DatabaseError(e.message ?: "Failed to mark as synced"))
        }
    }
    
    override fun observeTodos(): Flow<List<TodoEntity>> = localDataSource.observeTodos()
    
    override fun observeTodoById(id: String): Flow<TodoEntity?> = localDataSource.observeTodoById(id)
}
```

### Step 3: ビジネスロジック層実装
#### 3.1 Use Caseの作成
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/domain/usecase/GetTodosUseCase.kt`

```kotlin
package com.example.playground.domain.usecase

import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.TodoPriority
import com.example.playground.domain.repository.TodoRepository

class GetTodosUseCase(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(filter: TodoFilter = TodoFilter.ALL): Result<List<TodoEntity>> {
        return when (filter) {
            TodoFilter.ALL -> todoRepository.getAllTodos()
            TodoFilter.COMPLETED -> todoRepository.getTodosByStatus(isCompleted = true)
            TodoFilter.PENDING -> todoRepository.getTodosByStatus(isCompleted = false)
            TodoFilter.HIGH_PRIORITY -> todoRepository.getTodosByPriority(TodoPriority.HIGH.value)
            is TodoFilter.SEARCH -> todoRepository.searchTodos(filter.query)
        }.map { todos ->
            // 追加のビジネスロジック適用（ソート等）
            todos.sortedWith(compareByDescending<TodoEntity> { it.priority == TodoPriority.HIGH }
                .thenByDescending { it.priority == TodoPriority.MEDIUM }
                .thenBy { it.isCompleted }
                .thenByDescending { it.createdAt })
        }
    }
}

sealed class TodoFilter {
    object ALL : TodoFilter()
    object COMPLETED : TodoFilter()
    object PENDING : TodoFilter()
    object HIGH_PRIORITY : TodoFilter()
    data class SEARCH(val query: String) : TodoFilter()
}
```

**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/domain/usecase/CreateTodoUseCase.kt`

```kotlin
package com.example.playground.domain.usecase

import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.TodoPriority
import com.example.playground.domain.repository.TodoRepository
import com.example.playground.domain.error.TodoError
import kotlinx.datetime.LocalDateTime

class CreateTodoUseCase(
    private val todoRepository: TodoRepository,
    private val validator: TodoValidator
) {
    suspend operator fun invoke(request: CreateTodoRequest): Result<TodoEntity> {
        return validator.validate(request)
            .mapCatching { validRequest ->
                val todo = TodoEntity(
                    title = validRequest.title.trim(),
                    description = validRequest.description.trim(),
                    priority = validRequest.priority,
                    dueDate = validRequest.dueDate
                )
                todoRepository.insertTodo(todo).getOrThrow()
            }
    }
}

data class CreateTodoRequest(
    val title: String,
    val description: String = "",
    val priority: TodoPriority = TodoPriority.MEDIUM,
    val dueDate: LocalDateTime? = null
)

class TodoValidator {
    fun validate(request: CreateTodoRequest): Result<CreateTodoRequest> {
        return when {
            request.title.isBlank() -> 
                Result.failure(TodoError.ValidationError("title", "Title cannot be empty"))
            request.title.length > 200 -> 
                Result.failure(TodoError.ValidationError("title", "Title must be 200 characters or less"))
            request.description.length > 2000 -> 
                Result.failure(TodoError.ValidationError("description", "Description must be 2000 characters or less"))
            else -> Result.success(request)
        }
    }
    
    fun validate(request: UpdateTodoRequest): Result<UpdateTodoRequest> {
        return when {
            request.title != null && request.title.isBlank() -> 
                Result.failure(TodoError.ValidationError("title", "Title cannot be empty"))
            request.title != null && request.title.length > 200 -> 
                Result.failure(TodoError.ValidationError("title", "Title must be 200 characters or less"))
            request.description != null && request.description.length > 2000 -> 
                Result.failure(TodoError.ValidationError("description", "Description must be 2000 characters or less"))
            else -> Result.success(request)
        }
    }
}
```

**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/domain/usecase/UpdateTodoUseCase.kt`

```kotlin
package com.example.playground.domain.usecase

import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.TodoPriority
import com.example.playground.domain.repository.TodoRepository
import com.example.playground.domain.error.TodoError
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class UpdateTodoUseCase(
    private val todoRepository: TodoRepository,
    private val validator: TodoValidator
) {
    suspend operator fun invoke(todoId: String, request: UpdateTodoRequest): Result<TodoEntity> {
        return validator.validate(request)
            .mapCatching { validRequest ->
                // 既存のTODOを取得
                val existingTodo = todoRepository.getTodoById(todoId).getOrThrow()
                    ?: throw TodoError.TodoNotFound
                
                // 更新項目のみ適用
                val updatedTodo = existingTodo.copy(
                    title = validRequest.title ?: existingTodo.title,
                    description = validRequest.description ?: existingTodo.description,
                    isCompleted = validRequest.isCompleted ?: existingTodo.isCompleted,
                    priority = validRequest.priority ?: existingTodo.priority,
                    dueDate = validRequest.dueDate ?: existingTodo.dueDate,
                    updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
                
                todoRepository.updateTodo(updatedTodo).getOrThrow()
            }
    }
}

data class UpdateTodoRequest(
    val title: String? = null,
    val description: String? = null,
    val isCompleted: Boolean? = null,
    val priority: TodoPriority? = null,
    val dueDate: LocalDateTime? = null
)
```

**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/domain/usecase/DeleteTodoUseCase.kt`

```kotlin
package com.example.playground.domain.usecase

import com.example.playground.domain.repository.TodoRepository
import com.example.playground.domain.error.TodoError

class DeleteTodoUseCase(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoId: String): Result<Unit> {
        return try {
            // TODOの存在確認
            val existingTodo = todoRepository.getTodoById(todoId).getOrThrow()
            if (existingTodo == null) {
                Result.failure(TodoError.TodoNotFound)
            } else {
                todoRepository.deleteTodo(todoId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/domain/usecase/ToggleTodoUseCase.kt`

```kotlin
package com.example.playground.domain.usecase

import com.example.playground.domain.repository.TodoRepository
import com.example.playground.domain.error.TodoError
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ToggleTodoUseCase(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoId: String): Result<Unit> {
        return try {
            val existingTodo = todoRepository.getTodoById(todoId).getOrThrow()
                ?: return Result.failure(TodoError.TodoNotFound)
            
            val updatedTodo = existingTodo.copy(
                isCompleted = !existingTodo.isCompleted,
                updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
            
            todoRepository.updateTodo(updatedTodo).map { Unit }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

#### 3.2 エラーハンドリング実装
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/domain/error/TodoError.kt`

```kotlin
package com.example.playground.domain.error

sealed class TodoError : Exception() {
    // ネットワーク関連エラー
    object NetworkUnavailable : TodoError() {
        override val message: String = "Network is unavailable"
    }
    
    object TimeoutError : TodoError() {
        override val message: String = "Request timed out"
    }
    
    object ServerError : TodoError() {
        override val message: String = "Server error occurred"
    }
    
    // データ関連エラー
    object TodoNotFound : TodoError() {
        override val message: String = "Todo not found"
    }
    
    data class ValidationError(val field: String, val reason: String) : TodoError() {
        override val message: String = "Validation error in $field: $reason"
    }
    
    data class DatabaseError(val reason: String) : TodoError() {
        override val message: String = "Database error: $reason"
    }
    
    // 認証関連エラー
    object UnauthorizedError : TodoError() {
        override val message: String = "Unauthorized access"
    }
    
    object ForbiddenError : TodoError() {
        override val message: String = "Access forbidden"
    }
    
    // 同期関連エラー
    object SyncConflict : TodoError() {
        override val message: String = "Sync conflict occurred"
    }
    
    data class SyncError(val reason: String) : TodoError() {
        override val message: String = "Sync error: $reason"
    }
    
    // 一般的エラー
    data class UnknownError(val cause: Throwable) : TodoError() {
        override val message: String = "Unknown error: ${cause.message}"
    }
}

// エラーハンドリングヘルパー関数
suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
    Result.success(apiCall())
} catch (e: Exception) {
    when (e) {
        is TodoError -> Result.failure(e)
        else -> Result.failure(TodoError.UnknownError(e))
    }
}
```

### Step 4: プラットフォーム固有実装の基盤
#### 4.1 データソース抽象化
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/data/local/TodoLocalDataSource.kt`

```kotlin
package com.example.playground.data.local

import com.example.playground.data.model.TodoEntity
import kotlinx.coroutines.flow.Flow

expect class TodoLocalDataSource {
    suspend fun getAllTodos(): List<TodoEntity>
    suspend fun getTodoById(id: String): TodoEntity?
    suspend fun insertTodo(todo: TodoEntity): TodoEntity
    suspend fun updateTodo(todo: TodoEntity): TodoEntity
    suspend fun deleteTodo(id: String)
    suspend fun getTodosByStatus(isCompleted: Boolean): List<TodoEntity>
    suspend fun getTodosByPriority(priority: String): List<TodoEntity>
    suspend fun searchTodos(query: String): List<TodoEntity>
    suspend fun getPendingChanges(): List<TodoEntity>
    suspend fun markAsSynced(todoIds: List<String>)
    fun observeTodos(): Flow<List<TodoEntity>>
    fun observeTodoById(id: String): Flow<TodoEntity?>
}
```

**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/data/remote/TodoRemoteDataSource.kt`

```kotlin
package com.example.playground.data.remote

import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.SyncMetadata

expect class TodoRemoteDataSource {
    suspend fun getAllTodos(): List<TodoEntity>
    suspend fun getTodoById(id: String): TodoEntity?
    suspend fun createTodo(todo: TodoEntity): TodoEntity
    suspend fun updateTodo(todo: TodoEntity): TodoEntity
    suspend fun deleteTodo(id: String)
    suspend fun sync(localChanges: List<SyncMetadata>): SyncResponse
}

data class SyncResponse(
    val serverChanges: List<TodoEntity>,
    val conflicts: List<ConflictInfo>
)

data class ConflictInfo(
    val entityId: String,
    val clientVersion: Long,
    val serverVersion: Long,
    val clientData: TodoEntity,
    val serverData: TodoEntity
)
```

#### 4.2 同期管理システム
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/data/repository/SyncManager.kt`

```kotlin
package com.example.playground.data.repository

import com.example.playground.data.local.TodoLocalDataSource
import com.example.playground.data.remote.TodoRemoteDataSource
import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.SyncResult
import com.example.playground.data.model.SyncStatus
import com.example.playground.domain.error.TodoError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class SyncManager(
    private val localDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource,
    private val conflictResolver: ConflictResolver
) {
    suspend fun syncAll(): Result<SyncResult> = withContext(Dispatchers.IO) {
        try {
            val pendingChanges = localDataSource.getPendingChanges()
            if (pendingChanges.isEmpty()) {
                return@withContext Result.success(SyncResult(
                    syncedCount = 0,
                    conflictsResolved = 0,
                    lastSyncTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                ))
            }
            
            // リモートと同期
            val syncMetadata = pendingChanges.map { todo ->
                // TodoEntityをSyncMetadataに変換する処理
                createSyncMetadata(todo)
            }
            
            val syncResponse = remoteDataSource.sync(syncMetadata)
            
            // 競合解決
            val resolvedConflicts = conflictResolver.resolve(syncResponse.conflicts)
            
            // ローカルに反映
            syncResponse.serverChanges.forEach { serverTodo ->
                val localTodo = localDataSource.getTodoById(serverTodo.id)
                if (localTodo == null || localTodo.version < serverTodo.version) {
                    localDataSource.updateTodo(serverTodo.copy(syncStatus = SyncStatus.SYNCED))
                }
            }
            
            // 同期完了済みとしてマーク
            localDataSource.markAsSynced(pendingChanges.map { it.id })
            
            Result.success(SyncResult(
                syncedCount = pendingChanges.size,
                conflictsResolved = resolvedConflicts.size,
                lastSyncTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ))
            
        } catch (e: Exception) {
            Result.failure(TodoError.SyncError(e.message ?: "Sync failed"))
        }
    }
    
    suspend fun syncSingle(todo: TodoEntity): Result<Unit> {
        // 単一TODO同期の実装
        return try {
            val updatedTodo = remoteDataSource.updateTodo(todo)
            localDataSource.updateTodo(updatedTodo.copy(syncStatus = SyncStatus.SYNCED))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TodoError.SyncError(e.message ?: "Single sync failed"))
        }
    }
    
    suspend fun syncDeletion(todoId: String): Result<Unit> {
        // 削除同期の実装
        return try {
            remoteDataSource.deleteTodo(todoId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TodoError.SyncError(e.message ?: "Deletion sync failed"))
        }
    }
    
    suspend fun scheduleSyncIfNeeded() {
        // バックグラウンド同期のスケジューリングロジック
        try {
            val pendingCount = localDataSource.getPendingChanges().size
            if (pendingCount > 0) {
                syncAll()
            }
        } catch (e: Exception) {
            // ログ出力のみ（サイレント失敗）
            println("Background sync failed: ${e.message}")
        }
    }
    
    private fun createSyncMetadata(todo: TodoEntity): com.example.playground.data.model.SyncMetadata {
        return com.example.playground.data.model.SyncMetadata(
            entityId = todo.id,
            entityType = "todo",
            lastModified = todo.updatedAt,
            version = todo.version,
            deviceId = getDeviceId() // プラットフォーム固有実装
        )
    }
    
    private fun getDeviceId(): String {
        // プラットフォーム固有のデバイスID取得
        return "unknown-device"
    }
}

class ConflictResolver {
    suspend fun resolve(conflicts: List<ConflictInfo>): List<TodoEntity> {
        // シンプルな競合解決戦略: サーバー側を優先
        return conflicts.map { conflict ->
            conflict.serverData
        }
    }
}
```

### Step 5: テスト実装
#### 5.1 単体テスト作成
**ファイル**: `shared/src/commonTest/kotlin/com/example/playground/data/model/TodoEntityTest.kt`

```kotlin
package com.example.playground.data.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TodoEntityTest {
    
    @Test
    fun `should create TodoEntity with valid data`() {
        // Given
        val title = "Test Todo"
        val description = "Test description"
        
        // When
        val todo = TodoEntity(
            title = title,
            description = description,
            priority = TodoPriority.HIGH
        )
        
        // Then
        assertEquals(title, todo.title)
        assertEquals(description, todo.description)
        assertEquals(TodoPriority.HIGH, todo.priority)
        assertEquals(false, todo.isCompleted)
        assertEquals(SyncStatus.SYNCED, todo.syncStatus)
        assertTrue(todo.id.isNotEmpty())
    }
    
    @Test
    fun `should throw exception for empty title`() {
        assertFailsWith<IllegalArgumentException> {
            TodoEntity(title = "")
        }
    }
    
    @Test
    fun `should throw exception for blank title`() {
        assertFailsWith<IllegalArgumentException> {
            TodoEntity(title = "   ")
        }
    }
    
    @Test
    fun `should throw exception for title exceeding 200 characters`() {
        val longTitle = "a".repeat(201)
        assertFailsWith<IllegalArgumentException> {
            TodoEntity(title = longTitle)
        }
    }
    
    @Test
    fun `should throw exception for description exceeding 2000 characters`() {
        val longDescription = "a".repeat(2001)
        assertFailsWith<IllegalArgumentException> {
            TodoEntity(title = "Valid title", description = longDescription)
        }
    }
    
    @Test
    fun `should throw exception for non-positive version`() {
        assertFailsWith<IllegalArgumentException> {
            TodoEntity(title = "Valid title", version = 0)
        }
    }
}
```

**ファイル**: `shared/src/commonTest/kotlin/com/example/playground/domain/usecase/GetTodosUseCaseTest.kt`

```kotlin
package com.example.playground.domain.usecase

import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.TodoPriority
import com.example.playground.domain.repository.TodoRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// MockTodoRepository実装
class MockTodoRepository : TodoRepository {
    private val todos = mutableListOf<TodoEntity>()
    
    fun addTodos(vararg todoList: TodoEntity) {
        todos.addAll(todoList)
    }
    
    override suspend fun getAllTodos(): Result<List<TodoEntity>> {
        return Result.success(todos.toList())
    }
    
    override suspend fun getTodosByStatus(isCompleted: Boolean): Result<List<TodoEntity>> {
        return Result.success(todos.filter { it.isCompleted == isCompleted })
    }
    
    override suspend fun getTodosByPriority(priority: String): Result<List<TodoEntity>> {
        return Result.success(todos.filter { it.priority.value == priority })
    }
    
    override suspend fun searchTodos(query: String): Result<List<TodoEntity>> {
        return Result.success(todos.filter { 
            it.title.contains(query, ignoreCase = true) || 
            it.description.contains(query, ignoreCase = true) 
        })
    }
    
    // 他のメソッドは未実装（テストで使用しない）
    override suspend fun getTodoById(id: String): Result<TodoEntity?> = TODO()
    override suspend fun insertTodo(todo: TodoEntity): Result<TodoEntity> = TODO()
    override suspend fun updateTodo(todo: TodoEntity): Result<TodoEntity> = TODO()
    override suspend fun deleteTodo(id: String): Result<Unit> = TODO()
    override suspend fun syncWithRemote(): Result<com.example.playground.data.model.SyncResult> = TODO()
    override suspend fun getPendingChanges(): Result<List<TodoEntity>> = TODO()
    override suspend fun markAsSynced(todoIds: List<String>): Result<Unit> = TODO()
    override fun observeTodos(): kotlinx.coroutines.flow.Flow<List<TodoEntity>> = TODO()
    override fun observeTodoById(id: String): kotlinx.coroutines.flow.Flow<TodoEntity?> = TODO()
}

class GetTodosUseCaseTest {
    
    @Test
    fun `should return all todos when filter is ALL`() = runTest {
        // Given
        val mockRepository = MockTodoRepository()
        val todos = listOf(
            TodoEntity(id = "1", title = "Task 1", isCompleted = true, priority = TodoPriority.HIGH),
            TodoEntity(id = "2", title = "Task 2", isCompleted = false, priority = TodoPriority.MEDIUM),
            TodoEntity(id = "3", title = "Task 3", isCompleted = true, priority = TodoPriority.LOW)
        )
        mockRepository.addTodos(*todos.toTypedArray())
        val useCase = GetTodosUseCase(mockRepository)
        
        // When
        val result = useCase(TodoFilter.ALL)
        
        // Then
        assertTrue(result.isSuccess)
        val returnedTodos = result.getOrNull()!!
        assertEquals(3, returnedTodos.size)
        // 高優先度が最初に来ることを確認
        assertEquals(TodoPriority.HIGH, returnedTodos[0].priority)
    }
    
    @Test
    fun `should return only completed todos when filter is COMPLETED`() = runTest {
        // Given
        val mockRepository = MockTodoRepository()
        val todos = listOf(
            TodoEntity(id = "1", title = "Task 1", isCompleted = true),
            TodoEntity(id = "2", title = "Task 2", isCompleted = false),
            TodoEntity(id = "3", title = "Task 3", isCompleted = true)
        )
        mockRepository.addTodos(*todos.toTypedArray())
        val useCase = GetTodosUseCase(mockRepository)
        
        // When
        val result = useCase(TodoFilter.COMPLETED)
        
        // Then
        assertTrue(result.isSuccess)
        val completedTodos = result.getOrNull()!!
        assertEquals(2, completedTodos.size)
        assertTrue(completedTodos.all { it.isCompleted })
    }
    
    @Test
    fun `should return high priority todos when filter is HIGH_PRIORITY`() = runTest {
        // Given
        val mockRepository = MockTodoRepository()
        val todos = listOf(
            TodoEntity(id = "1", title = "Task 1", priority = TodoPriority.HIGH),
            TodoEntity(id = "2", title = "Task 2", priority = TodoPriority.MEDIUM),
            TodoEntity(id = "3", title = "Task 3", priority = TodoPriority.HIGH)
        )
        mockRepository.addTodos(*todos.toTypedArray())
        val useCase = GetTodosUseCase(mockRepository)
        
        // When
        val result = useCase(TodoFilter.HIGH_PRIORITY)
        
        // Then
        assertTrue(result.isSuccess)
        val highPriorityTodos = result.getOrNull()!!
        assertEquals(2, highPriorityTodos.size)
        assertTrue(highPriorityTodos.all { it.priority == TodoPriority.HIGH })
    }
    
    @Test
    fun `should return search results when filter is SEARCH`() = runTest {
        // Given
        val mockRepository = MockTodoRepository()
        val todos = listOf(
            TodoEntity(id = "1", title = "Buy groceries", description = "Milk and bread"),
            TodoEntity(id = "2", title = "Clean house", description = "Living room and kitchen"),
            TodoEntity(id = "3", title = "Study Kotlin", description = "Multiplatform development")
        )
        mockRepository.addTodos(*todos.toTypedArray())
        val useCase = GetTodosUseCase(mockRepository)
        
        // When
        val result = useCase(TodoFilter.SEARCH("kotlin"))
        
        // Then
        assertTrue(result.isSuccess)
        val searchResults = result.getOrNull()!!
        assertEquals(1, searchResults.size)
        assertEquals("Study Kotlin", searchResults[0].title)
    }
}
```

**ファイル**: `shared/src/commonTest/kotlin/com/example/playground/domain/usecase/CreateTodoUseCaseTest.kt`

```kotlin
package com.example.playground.domain.usecase

import com.example.playground.data.model.TodoEntity
import com.example.playground.data.model.TodoPriority
import com.example.playground.domain.error.TodoError
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CreateTodoUseCaseTest {
    
    @Test
    fun `should create todo successfully with valid request`() = runTest {
        // Given
        val mockRepository = MockTodoRepository()
        val validator = TodoValidator()
        val useCase = CreateTodoUseCase(mockRepository, validator)
        val request = CreateTodoRequest(
            title = "Test Todo",
            description = "Test description",
            priority = TodoPriority.HIGH
        )
        
        // When
        val result = useCase(request)
        
        // Then
        assertTrue(result.isSuccess)
        val createdTodo = result.getOrNull()!!
        assertEquals("Test Todo", createdTodo.title)
        assertEquals("Test description", createdTodo.description)
        assertEquals(TodoPriority.HIGH, createdTodo.priority)
    }
    
    @Test
    fun `should fail validation for empty title`() = runTest {
        // Given
        val mockRepository = MockTodoRepository()
        val validator = TodoValidator()
        val useCase = CreateTodoUseCase(mockRepository, validator)
        val request = CreateTodoRequest(title = "")
        
        // When
        val result = useCase(request)
        
        // Then
        assertTrue(result.isFailure)
        val error = result.exceptionOrNull() as? TodoError.ValidationError
        assertEquals("title", error?.field)
    }
    
    @Test
    fun `should fail validation for title exceeding 200 characters`() = runTest {
        // Given
        val mockRepository = MockTodoRepository()
        val validator = TodoValidator()
        val useCase = CreateTodoUseCase(mockRepository, validator)
        val request = CreateTodoRequest(title = "a".repeat(201))
        
        // When
        val result = useCase(request)
        
        // Then
        assertTrue(result.isFailure)
        val error = result.exceptionOrNull() as? TodoError.ValidationError
        assertEquals("title", error?.field)
    }
}

// MockTodoRepository を CreateTodoUseCase に対応させるための拡張
class MockTodoRepository : com.example.playground.domain.repository.TodoRepository {
    private val todos = mutableListOf<TodoEntity>()
    
    override suspend fun insertTodo(todo: TodoEntity): Result<TodoEntity> {
        todos.add(todo)
        return Result.success(todo)
    }
    
    // 他のメソッドの実装は省略（テストで必要に応じて追加）
    override suspend fun getAllTodos(): Result<List<TodoEntity>> = Result.success(todos.toList())
    override suspend fun getTodoById(id: String): Result<TodoEntity?> = TODO()
    override suspend fun updateTodo(todo: TodoEntity): Result<TodoEntity> = TODO()
    override suspend fun deleteTodo(id: String): Result<Unit> = TODO()
    override suspend fun getTodosByStatus(isCompleted: Boolean): Result<List<TodoEntity>> = TODO()
    override suspend fun getTodosByPriority(priority: String): Result<List<TodoEntity>> = TODO()
    override suspend fun searchTodos(query: String): Result<List<TodoEntity>> = TODO()
    override suspend fun syncWithRemote(): Result<com.example.playground.data.model.SyncResult> = TODO()
    override suspend fun getPendingChanges(): Result<List<TodoEntity>> = TODO()
    override suspend fun markAsSynced(todoIds: List<String>): Result<Unit> = TODO()
    override fun observeTodos(): kotlinx.coroutines.flow.Flow<List<TodoEntity>> = TODO()
    override fun observeTodoById(id: String): kotlinx.coroutines.flow.Flow<TodoEntity?> = TODO()
}
```

### Step 6: 統合・動作確認
#### 6.1 ビルド確認
```bash
# 共通モジュールのビルド確認
./gradlew :shared:build

# 全プラットフォームのビルド確認
./gradlew build
```

#### 6.2 テスト実行
```bash
# 共通テスト実行
./gradlew :shared:commonTest

# 全プラットフォームテスト実行
./gradlew :shared:allTests

# 特定プラットフォームテスト
./gradlew :shared:jvmTest
./gradlew :shared:androidUnitTest
```

#### 6.3 コード品質確認
```bash
# Kotlin lintチェック
./gradlew :shared:kotlinter

# コードフォーマット
./gradlew :shared:kotlinterFormat
```

## チェックリスト

### 実装チェック
- [ ] データモデル作成完了（TodoEntity、UserEntity、SyncMetadata）
- [ ] Repository Interface作成完了（TodoRepository、UserRepository）
- [ ] Use Case実装完了（GetTodos、CreateTodo、UpdateTodo、DeleteTodo、ToggleTodo）
- [ ] エラーハンドリング実装完了（TodoError定義、safeApiCall関数）
- [ ] バリデーション機能実装完了（TodoValidator）
- [ ] 同期管理システム基盤実装完了（SyncManager、ConflictResolver）
- [ ] expect/actual抽象化実装完了（LocalDataSource、RemoteDataSource）

### 品質チェック
- [ ] 単体テスト実装完了（データモデル、UseCase）
- [ ] テストカバレッジ確認（主要クラス80%以上）
- [ ] コード規約準拠確認（Kotlin Coding Conventions）
- [ ] エラーハンドリング実装確認（全メソッドで適切な例外処理）
- [ ] バリデーション動作確認（入力値検証・境界値テスト）
- [ ] ログ出力実装確認（デバッグ・エラートラッキング）

### 動作チェック
- [ ] ローカルビルド成功（:shared:build）
- [ ] 全テスト成功（:shared:allTests）
- [ ] Android対応確認（androidMain dependencies）
- [ ] iOS対応確認（iosMain dependencies）
- [ ] Web対応確認（wasmJsMain dependencies）
- [ ] Desktop対応確認（jvmMain dependencies）

### ドキュメント更新
- [ ] 実装内容のドキュメント更新
- [ ] Repository Interface仕様ドキュメント
- [ ] Use Case仕様ドキュメント
- [ ] エラーハンドリング仕様書

## トラブルシューティング

### よくある問題と解決策
#### ビルドエラー
**問題**: kotlinx-serialization plugin not found
**解決策**: build.gradle.ktsにkotlinSerialization pluginを追加し、Gradle syncを実行

#### ビルドエラー
**問題**: SQLDelight database generation failed
**解決策**: sqldelight設定を確認し、src/commonMain/sqldelightディレクトリが存在することを確認

#### 実行時エラー
**問題**: expect/actual declaration mismatch
**解決策**: 各プラットフォームのactual実装が対応するexpect宣言と完全に一致することを確認

#### テスト失敗
**問題**: Coroutine test timeout
**解決策**: runTestブロックを使用し、MockRepositoryの実装が適切に suspend 関数を処理することを確認

### 依存関係の問題
**問題**: Version conflicts between dependencies
**解決策**: libs.versions.tomlで依存関係のバージョンを統一管理し、./gradlew dependenciesでconflictを確認

## 完了確認
### 成果物チェック
- [ ] 実装ファイル一式（データモデル、Repository、UseCase）
- [ ] テストファイル一式（単体テスト、統合テスト基盤）
- [ ] プラットフォーム抽象化（expect/actual declarations）
- [ ] ビルド設定更新（dependencies、plugins）
- [ ] 動作確認完了（ビルド・テスト成功）

### レビュー準備
- [ ] コミット作成（meaningful commit messages）
- [ ] プルリクエスト作成準備
- [ ] レビュー依頼準備（設計文書との整合性確認）

## 次のステップ
1. **プラットフォーム固有実装**: AndroidMain、iOSMain、jvmMain、wasmJsMainでのactual実装
2. **API統合**: サーバーサイド実装とのAPI連携
3. **UI統合**: composeAppモジュールでのViewModel・UI実装
4. **統合テスト**: 全プラットフォーム横断テストの実装
5. **性能最適化**: メモリ使用量・応答時間の最適化

## 参考資料
- **Design Doc**: WBS-1.3.1-architecture-design.md
- **Context File**: PBI-001-context.md
- **WBS**: PBI-001-wbs.md
- **Kotlin Multiplatform公式ドキュメント**: https://kotlinlang.org/docs/multiplatform.html
- **SQLDelight Documentation**: https://cashapp.github.io/sqldelight/
- **kotlinx-serialization**: https://github.com/Kotlin/kotlinx.serialization
- **kotlinx-coroutines**: https://github.com/Kotlin/kotlinx.coroutines

## 実装ログ
| 日時 | 実装者 | 実装内容 | 備考 |
|------|--------|----------|------|
| 2025-09-02 14:00 | Architecture Strategist | 実装手順書作成・データモデル設計 | Phase 1 設計完了に基づく詳細実装計画 |