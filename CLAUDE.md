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

プロジェクトには10の専門agentが設定されており、開発プロセス全体をサポートします：

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
- 新機能のPBI作成: product-owner-pbi-managerでヒアリング実行
- UI設計: design-system-ui-architectでデザインシステム構築
- アーキテクチャ検討: architecture-strategistで設計レビュー
- テスト計画: qa-test-strategistで包括的テスト設計
```