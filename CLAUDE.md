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