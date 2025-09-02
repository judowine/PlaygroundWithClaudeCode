# Implementation Guide Template

## 基本情報
- **タスクID**: [WBS-X.X.X]
- **タスク名**: [タスク名]
- **関連Design Doc**: [design-doc-file-name.md]
- **作成日**: [YYYY-MM-DD]
- **実装者**: [実装者名]
- **見積もり工数**: [X時間]

## 実装概要
### 実装目標
[Design Docから転記]

### 成果物
- [ ] [成果物1: 具体的なファイル名]
- [ ] [成果物2: 具体的なファイル名]
- [ ] [成果物3: 具体的なファイル名]

### 前提条件
- [ ] [前提条件1]
- [ ] [前提条件2]
- [ ] [依存タスクの完了確認]

## 実装手順

### Step 1: 環境準備
#### 1.1 依存関係の追加
```bash
# 必要な依存関係をbuild.gradle.ktsに追加
# 具体的なコマンドまたは設定内容
```

```kotlin
// build.gradle.kts (Module: shared)
dependencies {
    implementation("io.ktor:ktor-client-core:2.3.x")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.x")
    // その他必要な依存関係
}
```

#### 1.2 必要なディレクトリ・ファイルの作成
```bash
# ディレクトリ構造の作成
mkdir -p shared/src/commonMain/kotlin/com/example/playground/feature/[feature-name]
mkdir -p shared/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/data
mkdir -p shared/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/domain
mkdir -p shared/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/presentation
```

#### 1.3 設定ファイルの更新
```kotlin
// 必要に応じて設定ファイルを更新
```

### Step 2: データ層実装
#### 2.1 データモデルの作成
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/data/model/[Model].kt`

```kotlin
package com.example.playground.feature.[feature-name].data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class [Model](
    val id: String,
    val name: String,
    val createdAt: LocalDateTime
)
```

#### 2.2 Repository Interfaceの作成
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/domain/repository/[Feature]Repository.kt`

```kotlin
package com.example.playground.feature.[feature-name].domain.repository

interface [Feature]Repository {
    suspend fun get[Items](): Result<List<[Model]>>
    suspend fun get[Item](id: String): Result<[Model]>
    suspend fun create[Item](item: [Model]): Result<[Model]>
    suspend fun update[Item](item: [Model]): Result<[Model]>
    suspend fun delete[Item](id: String): Result<Unit>
}
```

#### 2.3 Repository実装の作成
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/data/repository/[Feature]RepositoryImpl.kt`

```kotlin
package com.example.playground.feature.[feature-name].data.repository

import com.example.playground.feature.[feature-name].domain.repository.[Feature]Repository

class [Feature]RepositoryImpl(
    private val apiService: [Feature]ApiService,
    private val localDataSource: [Feature]LocalDataSource
) : [Feature]Repository {
    
    override suspend fun get[Items](): Result<List<[Model]>> {
        return try {
            // 実装内容
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // その他のメソッド実装
}
```

### Step 3: ビジネスロジック層実装
#### 3.1 UseCaseの作成
**ファイル**: `shared/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/domain/usecase/Get[Items]UseCase.kt`

```kotlin
package com.example.playground.feature.[feature-name].domain.usecase

class Get[Items]UseCase(
    private val repository: [Feature]Repository
) {
    suspend operator fun invoke(): Result<List<[Model]>> {
        return repository.get[Items]()
    }
}
```

#### 3.2 その他UseCase実装
**ファイル群**: 
- `Create[Item]UseCase.kt`
- `Update[Item]UseCase.kt`  
- `Delete[Item]UseCase.kt`

```kotlin
// 各UseCaseの実装テンプレート
```

### Step 4: プレゼンテーション層実装
#### 4.1 ViewModelの作成（composeAppモジュール）
**ファイル**: `composeApp/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/presentation/[Feature]ViewModel.kt`

```kotlin
package com.example.playground.feature.[feature-name].presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class [Feature]ViewModel(
    private val get[Items]UseCase: Get[Items]UseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow([Feature]UiState())
    val uiState: StateFlow<[Feature]UiState> = _uiState
    
    fun load[Items]() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            get[Items]UseCase()
                .onSuccess { items ->
                    _uiState.value = _uiState.value.copy(
                        items = items,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
        }
    }
}

data class [Feature]UiState(
    val items: List<[Model]> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

#### 4.2 Compose UI実装
**ファイル**: `composeApp/src/commonMain/kotlin/com/example/playground/feature/[feature-name]/presentation/[Feature]Screen.kt`

```kotlin
package com.example.playground.feature.[feature-name].presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun [Feature]Screen(
    viewModel: [Feature]ViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.load[Items]()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // UI実装内容
        
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                LazyColumn {
                    items(uiState.items) { item ->
                        [Feature]Item(
                            item = item,
                            onClick = { /* 処理 */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun [Feature]Item(
    item: [Model],
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.id,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
```

### Step 5: サーバー実装（該当する場合）
#### 5.1 Ktorルート実装
**ファイル**: `server/src/main/kotlin/com/example/playground/plugins/[Feature]Routes.kt`

```kotlin
package com.example.playground.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configure[Feature]Routes() {
    routing {
        route("/api/v1/[feature-name]") {
            get {
                // GET実装
                call.respond(listOf<[Model]>())
            }
            
            post {
                // POST実装
            }
            
            put("/{id}") {
                // PUT実装
            }
            
            delete("/{id}") {
                // DELETE実装
            }
        }
    }
}
```

#### 5.2 アプリケーション設定への追加
**ファイル**: `server/src/main/kotlin/com/example/playground/Application.kt`

```kotlin
// configure[Feature]Routes()の追加
```

### Step 6: プラットフォーム固有実装
#### 6.1 Android固有実装
**ファイル**: `shared/src/androidMain/kotlin/com/example/playground/feature/[feature-name]/Platform[Feature].android.kt`

```kotlin
package com.example.playground.feature.[feature-name]

actual class Platform[Feature] {
    actual fun perform[Operation](): String {
        // Android固有の実装
        return "Android implementation"
    }
}
```

#### 6.2 iOS固有実装
**ファイル**: `shared/src/iosMain/kotlin/com/example/playground/feature/[feature-name]/Platform[Feature].ios.kt`

```kotlin
package com.example.playground.feature.[feature-name]

actual class Platform[Feature] {
    actual fun perform[Operation](): String {
        // iOS固有の実装
        return "iOS implementation"
    }
}
```

### Step 7: テスト実装
#### 7.1 単体テスト作成
**ファイル**: `shared/src/commonTest/kotlin/com/example/playground/feature/[feature-name]/domain/usecase/Get[Items]UseCaseTest.kt`

```kotlin
package com.example.playground.feature.[feature-name].domain.usecase

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Get[Items]UseCaseTest {
    
    @Test
    fun `should return items when repository succeeds`() = runTest {
        // Given
        val mockRepository = Mock[Feature]Repository()
        val useCase = Get[Items]UseCase(mockRepository)
        
        // When
        val result = useCase()
        
        // Then
        assertTrue(result.isSuccess)
    }
}
```

#### 7.2 UIテスト作成
**ファイル**: `composeApp/src/commonTest/kotlin/com/example/playground/feature/[feature-name]/presentation/[Feature]ScreenTest.kt`

```kotlin
package com.example.playground.feature.[feature-name].presentation

import androidx.compose.ui.test.*
import kotlin.test.Test

class [Feature]ScreenTest {
    
    @Test
    fun `should display items when loaded successfully`() {
        // UI test implementation
    }
}
```

### Step 8: 統合・動作確認
#### 8.1 ビルド確認
```bash
# 全プラットフォームのビルド確認
./gradlew build
```

#### 8.2 テスト実行
```bash
# 全テスト実行
./gradlew check
./gradlew allTests
```

#### 8.3 動作確認
```bash
# 各プラットフォームでの動作確認
./gradlew :composeApp:wasmJsBrowserDevelopmentRun    # Web
./gradlew :composeApp:runDebugExecutableLinuxX64     # Desktop
# Android/iOS は IDE から実行
```

## チェックリスト

### 実装チェック
- [ ] データモデル作成完了
- [ ] Repository Interface作成完了
- [ ] Repository実装完了
- [ ] UseCase実装完了
- [ ] ViewModel実装完了
- [ ] UI実装完了
- [ ] サーバー実装完了（該当する場合）
- [ ] プラットフォーム固有実装完了

### 品質チェック
- [ ] 単体テスト実装完了
- [ ] テストカバレッジ確認
- [ ] コード規約準拠確認
- [ ] エラーハンドリング実装確認
- [ ] ログ出力実装確認

### 動作チェック
- [ ] ローカルビルド成功
- [ ] 全テスト成功
- [ ] Android動作確認完了
- [ ] iOS動作確認完了
- [ ] Web動作確認完了
- [ ] Desktop動作確認完了
- [ ] サーバー動作確認完了

### ドキュメント更新
- [ ] 実装内容のドキュメント更新
- [ ] APIドキュメント更新（該当する場合）
- [ ] README更新（必要に応じて）

## トラブルシューティング

### よくある問題と解決策
#### ビルドエラー
**問題**: [具体的なエラー内容]
**解決策**: [解決手順]

#### 実行時エラー
**問題**: [具体的なエラー内容]
**解決策**: [解決手順]

#### テスト失敗
**問題**: [具体的なエラー内容]
**解決策**: [解決手順]

## 完了確認
### 成果物チェック
- [ ] 実装ファイル一式
- [ ] テストファイル一式
- [ ] ドキュメント更新
- [ ] 動作確認完了

### レビュー準備
- [ ] コミット作成
- [ ] プルリクエスト作成準備
- [ ] レビュー依頼準備

## 次のステップ
1. コードレビュー依頼
2. 修正対応（必要に応じて）
3. 統合テスト実行
4. プロダクション環境デプロイ準備

## 参考資料
- [Design Doc]: [design-doc-file-name.md]
- [関連実装]: [他の実装ファイル]
- [技術ドキュメント]: [参考URL]

## 実装ログ
| 日時 | 実装者 | 実装内容 | 備考 |
|------|--------|----------|------|
| [YYYY-MM-DD HH:MM] | [実装者] | [実装内容] | [備考] |