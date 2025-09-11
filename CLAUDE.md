# CLAUDE.md

このファイルは、このリポジトリでコードを扱う際のClaude Code (claude.ai/code) への指針を提供します。

## プロジェクト概要

これはAndroid、iOS、Web（WASM）、Desktop（JVM）、Serverプラットフォームを対象としたKotlin Multiplatformプロジェクトです。UIにはCompose Multiplatform、サーバーコンポーネントにはKtorを使用しています。

## プロジェクト構造

- **`/composeApp`** - Compose Multiplatformを使用したメインのマルチプラットフォームUIアプリケーション
  - `commonMain/kotlin` - 全プラットフォーム共通のUIコード
  - `androidMain/kotlin` - Android固有の実装
  - `iosMain/kotlin` - iOS固有の実装
  - `jvmMain/kotlin` - Desktop JVM固有の実装
  - `wasmJsMain/kotlin` - Web WASM固有の実装
- **`/server`** - Ktorサーバーアプリケーション（JVMのみ）
- **`/shared`** - 全ターゲット間で共有されるビジネスロジックとユーティリティ
  - `commonMain/kotlin` - プラットフォーム非依存の共有コード
  - プラットフォーム固有の実装用フォルダー
- **`/iosApp`** - iOSアプリケーションのエントリーポイント（Swift/Objective-C）

## 必須コマンド

### ビルド
```bash
./gradlew build                    # 全モジュールをビルド
./gradlew :composeApp:build        # composeアプリのみをビルド
./gradlew :server:build            # サーバーのみをビルド
./gradlew :shared:build            # 共有モジュールのみをビルド
```

### アプリケーション実行
```bash
./gradlew run                                              # サーバーアプリケーションを実行
./gradlew :composeApp:wasmJsBrowserDevelopmentRun         # ブラウザでWebアプリを実行
./gradlew :composeApp:runDebugExecutableLinuxX64          # デスクトップアプリを実行（Linux）
```

### テスト
```bash
./gradlew check                    # 全テストとチェックを実行
./gradlew allTests                 # 全ターゲットのテストを実行
./gradlew :composeApp:jvmTest      # composeアプリのJVMテストを実行
./gradlew :server:test             # サーバーテストを実行
./gradlew :shared:allTests         # 共有モジュールの全テストを実行
```

### プラットフォーム固有のテスト
```bash
./gradlew :composeApp:testDebugUnitTest     # Androidユニットテスト
./gradlew :composeApp:iosX64Test            # iOSシミュレーターテスト
./gradlew :composeApp:wasmJsBrowserTest     # Webブラウザーテスト
```

### コード品質
```bash
./gradlew lint                     # Android lintを実行
./gradlew lintFix                  # lintを実行し安全な修正を適用
```

## 開発アーキテクチャ

- **共有ビジネスロジック**: `shared/commonMain`の共通コードと各プラットフォームディレクトリのプラットフォーム固有実装
- **UIフレームワーク**: プラットフォーム間で一貫したUIのためのCompose Multiplatform
- **サーバー**: NettyエンジンベースのKtor RESTサーバー
- **プラットフォーム抽象化**: プラットフォーム固有の実装はexpect/actualパターンに従う
- **ビルドシステム**: Kotlin DSLとバージョンカタログを使用するGradle
- **パッケージ構造**: 全コードで`com.example.playground`ベースパッケージを使用

## 主要設定ファイル

- **`build.gradle.kts`** - ルートビルド設定
- **`settings.gradle.kts`** - プロジェクト構造とモジュール定義
- **`gradle.properties`** - Gradleとビルド最適化設定
- **モジュール固有の`build.gradle.kts`** ファイル - 依存関係とプラットフォーム設定を定義

## ホットリロード開発

プロジェクトはCompose Hot Reloadサポートを含みます：
```bash
./gradlew hotRunJvm                # ホットリロード有効で実行
./gradlew reload                   # 実行中のアプリでホットリロードをトリガー
```

## PBI（Product Backlog Item）管理システム

このプロジェクトは包括的なPBI管理システムを採用しています。

### PBI状態管理フロー
```
requirements → ready → active → completed → archived
  要件定義中   着手可能  進行中     完了    アーカイブ
     ↓          ↓
   blocked    blocked
 （ブロック中）（ブロック中）
```

### PBI管理コマンド
```bash
/create-pbi [テーマ]               # インタラクティブPBI作成
/create-pbi ユーザー認証機能       # テーマ指定でPBI作成
```

### PBIディレクトリ構造
- **`docs/pbi/requirements/`** - 要件定義中のPBI
- **`docs/pbi/blocked/`** - ブロック中のPBI（依存関係未解決）
- **`docs/pbi/ready/`** - 着手可能なPBI（Definition of Ready完了）
- **`docs/pbi/active/`** - 進行中のPBI
- **`docs/pbi/completed/`** - 完了したPBI
- **`docs/pbi/archived/`** - アーカイブされたPBI
- **`docs/pbi/templates/`** - PBIテンプレート
- **`docs/pbi/workflow.md`** - PBI管理ワークフロー

### Definition of Ready (DoR)
PBIが`ready`状態になるためには、以下6カテゴリのチェックリストをクリアする必要があります：
1. **要件定義完了度** - ユーザーストーリーと受け入れ条件の明確化
2. **技術仕様明確性** - アーキテクチャ設計とAPI仕様の確定
3. **Kotlin Multiplatform対応** - プラットフォーム別実装方針とexpect/actual戦略
4. **依存関係解決状況** - 外部依存とPBI間依存の整理
5. **見積もり精度** - ストーリーポイントと工数の妥当性確認
6. **承認プロセス完了** - ステークホルダーとチームの最終承認

## 専門Agent活用

プロジェクトには11の専門agentが設定されており、開発プロセス全体をサポートします：

### プロジェクト管理
- **strategic-project-manager** - プロジェクト全体統制、リソース管理、リスクマネジメント

### プロダクト・要件管理
- **product-owner-pbi-manager** - PBI作成・管理、ステークホルダーヒアリング
- **pbi-refinement-facilitator** - PBI詳細化、バックロググルーミング
- **market-analyst** - 市場分析、競合調査

### UX・デザイン
- **ux-persona-journey-designer** - ユーザーペルソナ、ユーザージャーニー設計  
- **design-system-ui-architect** - UIデザインシステム、画面レイアウト
- **interaction-prototyping-agent** - インタラクション設計、プロトタイピング

### 開発・アーキテクチャ
- **architecture-strategist** - システムアーキテクチャ、技術戦略
- **frontend-generalist-dev** - フロントエンド総合開発
- **backend-security-architect** - バックエンド開発、セキュリティ設計

### 品質保証
- **qa-test-strategist** - テスト戦略、品質保証計画

### Agent活用例
```bash
# Agentを使用したタスクの例
- プロジェクト計画: strategic-project-managerで全体統制・リソース配分
- 新機能のPBI作成: product-owner-pbi-managerでヒアリング実行
- UI設計: design-system-ui-architectでデザインシステム構築
- アーキテクチャ検討: architecture-strategistで設計レビュー
- テスト計画: qa-test-strategistで包括的テスト設計
```

### Strategic Project Manager詳細仕様

**Agent名**: `strategic-project-manager`

**専門領域**:
- プロジェクト全体統制とガバナンス
- マルチプラットフォーム開発のリソース管理
- PBI優先度調整と開発スケジューリング
- クロスファンクショナルチーム連携
- リスク管理と品質コントロール
- ステークホルダー管理と進捗報告

**主要機能**:

1. **プロジェクト計画管理**
   - WBS統合とマイルストーン設定
   - Kotlin Multiplatform特有のタスク依存関係管理
   - プラットフォーム別開発優先度調整
   - 開発チーム間の作業分散とロードバランシング

2. **リソース管理**
   - 専門Agent活用戦略の策定
   - 開発者スキルマッピングとアサイン最適化
   - 並行開発タスクの効率化
   - ボトルネック特定と解消策提案

3. **品質・リスク管理**
   - Definition of Done準拠チェック
   - 技術的負債の監視と改善計画
   - プラットフォーム間の品質一貫性確保
   - セキュリティ・パフォーマンスリスク評価

4. **ステークホルダー管理**
   - 進捗レポートとダッシュボード生成
   - 課題エスカレーションと解決調整
   - 要件変更の影響分析と対応判断
   - チーム間コミュニケーション促進

**活用シーン**:
```bash
# プロジェクト開始時
strategic-project-manager: 全体計画策定、リソース配分、リスク分析

# 開発中の統制
strategic-project-manager: 進捗監視、ボトルネック解消、優先度調整

# リリース前の品質確保
strategic-project-manager: 品質ゲート、リリース判定、展開計画

# 課題発生時の対応
strategic-project-manager: 影響分析、対応策立案、リソース再配分
```

**他Agentとの連携パターン**:

- **product-owner-pbi-manager**: PBI優先度調整、要件変更影響分析
- **architecture-strategist**: 技術的意思決定、アーキテクチャリスク評価  
- **qa-test-strategist**: 品質基準設定、テスト戦略調整
- **frontend-generalist-dev** / **backend-security-architect**: 開発進捗管理、技術課題解決
- **design-system-ui-architect**: デザインシステム統制、UI一貫性管理

## 実装開発フロー（Step 1-2 Standard Process）

このプロジェクトでは、PBIの`ready`状態から機能実装完了（統合テスト成功）まで、以下の標準化された2段階プロセスに従います：

### Step 1: Skeleton実装
PBIの`ready`状態から開始し、実装の骨格を構築します。

#### 1.1 Design Doc作成
**目的**: 技術仕様と受け入れ条件の明確化

**使用Agent**: `architecture-strategist` + `design-system-ui-architect`

**成果物**: `docs/design/design-docs/[PBI-ID]-design.md`

**内容**:
- **使用技術**: Kotlin Multiplatform、Compose Multiplatform、Ktor等の具体的技術選択
- **受け入れ条件**: PBIから抽出した動作要件を技術的に詳述
- **アーキテクチャ概要**: Layered Architectureでの配置と依存関係
- **プラットフォーム対応**: 各プラットフォームでの実装方針

**品質基準**:
- 受け入れ条件が測定可能で具体的
- 技術選択に明確な根拠
- プラットフォーム間の一貫性確保

#### 1.2 Skeleton実装
**目的**: 全体構造の可視化と早期統合の実現

**使用Agent**: `frontend-generalist-dev` + `backend-security-architect`

**実装範囲**:
```kotlin
// 例: 主要なクラス・メソッド・プロパティを空実装で定義
class UserRepository {
    // TODO: データベースアクセス実装
    suspend fun findUserById(id: String): User? = null
    
    // TODO: ユーザー作成処理実装  
    suspend fun createUser(user: User): Result<User> = TODO()
}

data class User(
    val id: String,
    val name: String,
    // TODO: その他必要プロパティ追加
)
```

**実装基準**:
- 全てのクラス、メソッド、プロパティを定義
- モック実装またはTODOコメントで実装方針を明記
- ビルド・実行可能な状態を保持
- Layered Architectureの各層を適切に配置

#### 1.3 Skeleton PR提出
**PR要件**:
- **タイトル**: `[Skeleton] [PBI-ID] [機能名]`
- **説明**: Design Docへのリンクと実装方針の要約
- **レビュー観点**: アーキテクチャ構造、命名規則、TODO内容の妥当性

**品質チェック**:
```bash
./gradlew build                    # ビルド成功確認
./gradlew allTests                 # 基本テスト実行
./gradlew lint                     # コード品質チェック
```

### Step 2: レイヤー別実装とテスト
Skeleton実装を基に、Layered Architectureに従って段階的に実装します。

#### 2.1 タスク分割
**分割基準**: Layered Architectureの各層
```
1. Data Layer (Repository, DataSource, Entity)
2. Domain Layer (UseCase, Domain Model, Repository Interface)  
3. Presentation Layer (ViewModel, UI Components)
4. Platform Layer (Platform-specific implementations)
```

**使用Agent**: `architecture-strategist`による分割支援

#### 2.2 各レイヤー実装
**実装順序**: Data → Domain → Presentation → Platform

**各レイヤーPR要件**:
- **タイトル**: `[Layer] [PBI-ID] [レイヤー名] Implementation`
- **実装完了基準**: 
  - 該当レイヤーの全TODOを実装
  - 単体テスト実装・成功
  - 上位レイヤーとの統合確認

**品質基準**:
- テストカバレッジ > 80%
- 循環的複雑度 < 10
- レイヤー間依存関係の適切性

#### 2.3 統合テスト実装
**目的**: Design Docの受け入れ条件を満たす動作保証

**実装場所**: ViewModelレベルでの結合テスト

**使用Agent**: `qa-test-strategist`

**テスト実装基準**:
```kotlin
@Test
fun `ユーザー作成が正常に完了する`() = runTest {
    // Given: 有効なユーザー情報
    val userInput = CreateUserRequest(name = "テストユーザー")
    
    // When: ユーザー作成を実行
    val result = viewModel.createUser(userInput)
    
    // Then: 受け入れ条件を満たす
    assertTrue(result.isSuccess)
    assertEquals("テストユーザー", result.getOrNull()?.name)
    // TODO: Design Docの全受け入れ条件をテスト
}
```

**統合テストPR要件**:
- **タイトル**: `[Integration] [PBI-ID] [機能名] Integration Tests`
- **内容**: Design Docの受け入れ条件の完全実装とテスト
- **成果物**: 動作する機能とテストコード

### 開発完了の定義

統合テスト成功をもって**開発完了**とします。この時点でPBIは`active`から`completed`状態に移行します。

**完了条件**:
- 統合実装品質ゲート合格
- Design Docの全受け入れ条件を満たす
- 全プラットフォームでビルド・テスト成功
- コードレビュー完了・マージ済み

**次段階**: リリース・運用保守フローへ移行（別ワークフロー）

### 品質ゲート

#### Skeleton実装品質ゲート
```yaml
criteria:
  design_doc_quality: ">= 4.0"
  skeleton_completeness: "100% (全構造定義済み)"
  build_success: "全プラットフォームでビルド成功"
  architecture_compliance: "Layered Architecture準拠"
```

#### 統合実装品質ゲート  
```yaml
criteria:
  feature_completeness: "100% (受け入れ条件満足)"
  test_coverage: ">= 80%"
  integration_success: "全統合テスト成功"
  performance_requirements: "応答時間・リソース使用量基準満足"
```

### 開発効率化のコツ

#### Agent活用パターン
```bash
# Step 1: Skeleton開発
architecture-strategist: Design Doc作成・アーキテクチャ設計
frontend-generalist-dev: UI Skeleton実装
backend-security-architect: API・データ層Skeleton実装

# Step 2: レイヤー別実装
# Data Layer実装時
backend-security-architect: Repository、DataSource実装

# Domain Layer実装時  
architecture-strategist: UseCase、ビジネスロジック実装

# Presentation Layer実装時
frontend-generalist-dev: ViewModel、UI Components実装

# テスト実装時
qa-test-strategist: 統合テスト戦略・実装
```

#### トラブルシューティング
- **Skeleton段階での設計変更**: Design Docを更新してからSkeleton修正
- **レイヤー間統合エラー**: 該当レイヤーの境界設計をarchitecture-strategistで見直し
- **テスト失敗**: qa-test-strategistでテスト戦略を再検討

### テンプレート・参考資料
- **Design Docテンプレート**: `docs/design/templates/design-doc-template.md`
- **PBI管理ワークフロー**: `docs/pbi/workflow.md`
- **品質ゲート詳細**: `docs/workflow/quality-gates.md`
- **Multi-Agent開発プロセス**: `docs/workflow/multi-agent-development.md`
- **リリース・運用保守フロー**: `docs/workflow/release-operations-workflow.md`