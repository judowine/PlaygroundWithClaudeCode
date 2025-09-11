# プラットフォーム影響分析手法

## 概要
Kotlin Multiplatformプロジェクトにおいて、新機能・変更要求が各プラットフォーム（Android/iOS/Web/Desktop/Server）に与える影響を体系的に分析する手法です。`multiplatform-dev-orchestrator`が実装計画策定時に使用します。

## 分析フレームワーク

### 1. プラットフォーム実装レイヤー分析

#### Layer 1: Common Core (shared/commonMain)
```yaml
category: "shared_common_implementation"
description: "全プラットフォーム共通のビジネスロジック・データモデル"

typical_contents:
  business_logic:
    - "計算ロジック・アルゴリズム"
    - "データ変換・フォーマッティング"
    - "バリデーション・ビジネスルール"
    - "ワークフロー・状態管理"
  
  data_models:
    - "エンティティ・データクラス"
    - "DTOクラス・レスポンスモデル"
    - "列挙型・定数定義"
    - "シリアライゼーション設定"
  
  utilities:
    - "ユーティリティ関数・拡張関数"
    - "ヘルパークラス・コンパニオンオブジェクト"
    - "文字列処理・日時処理"
    - "コレクション操作・フィルタリング"

impact_assessment:
  effort_multiplier: 1.0  # 基準工数（1回実装で全プラットフォーム対応）
  maintenance_cost: "低"   # 一箇所修正で全体に反映
  testing_scope: "共通テスト + プラットフォーム固有検証"
```

#### Layer 2: Platform Abstraction (expect/actual)
```yaml
category: "platform_abstraction"
description: "プラットフォーム間の差異を吸収する抽象化レイヤー"

typical_abstractions:
  platform_services:
    - "ファイルシステムアクセス"
    - "ネットワーク通信・HTTP"
    - "データベースアクセス・永続化"
    - "暗号化・セキュリティ機能"
  
  system_integration:
    - "ログ出力・デバッグ機能"
    - "設定・環境変数アクセス"
    - "プラットフォーム情報取得"
    - "リソース・アセット管理"
  
  ui_abstraction:
    - "ナビゲーション・ルーティング"
    - "ダイアログ・通知"
    - "ファイルピッカー・カメラ"
    - "位置情報・センサー"

impact_assessment:
  effort_multiplier: 1.5  # expect定義 + 各プラットフォームactual実装
  maintenance_cost: "中"   # インターフェース変更時は全プラットフォーム対応必要
  testing_scope: "共通インターフェーステスト + 各プラットフォーム実装テスト"
```

#### Layer 3: Platform Specific Implementation
```yaml
category: "platform_specific"
description: "各プラットフォーム固有の実装・最適化"

android_specifics:
  ui_framework: "Compose for Android"
  platform_apis:
    - "Android SDK・Framework API"
    - "Google Play Services"
    - "AndroidX Library"
    - "Jetpack Components"
  integration_points:
    - "Activity・Fragment lifecycle"
    - "Intent・BroadcastReceiver"
    - "Service・WorkManager"
    - "Permissions・Runtime permissions"

ios_specifics:
  ui_framework: "Compose Multiplatform for iOS"
  platform_apis:
    - "iOS SDK・Foundation"
    - "UIKit・SwiftUI interop"
    - "Core Data・CloudKit"
    - "Core Location・AVFoundation"
  integration_points:
    - "UIViewController lifecycle"
    - "App delegate・Scene delegate"
    - "Background tasks・Push notifications"
    - "iOS permissions・Privacy settings"

web_specifics:
  ui_framework: "Compose for Web (WASM)"
  platform_apis:
    - "Web APIs・Browser APIs"
    - "DOM manipulation"
    - "Local Storage・IndexedDB"
    - "Service Worker・PWA"
  integration_points:
    - "Browser history・Routing"
    - "Web Workers・Async operations"
    - "Responsive design・Media queries"
    - "Accessibility・ARIA"

desktop_specifics:
  ui_framework: "Compose for Desktop"
  platform_apis:
    - "JVM・Swing interop"
    - "OS native APIs"
    - "File system・Registry"
    - "System tray・Native menus"
  integration_points:
    - "Window management"
    - "Keyboard shortcuts・Menu bars"
    - "File associations・Protocol handlers"
    - "OS notifications・System integration"

server_specifics:
  framework: "Ktor Server"
  platform_apis:
    - "JVM・Coroutines"
    - "Database drivers・JDBC"
    - "Authentication libraries"
    - "Monitoring・Logging frameworks"
  integration_points:
    - "HTTP endpoints・REST API"
    - "Database connections・ORM"
    - "Authentication・Authorization"
    - "Deployment・Container integration"

impact_assessment:
  effort_multiplier: 2.0-3.0  # プラットフォーム数に比例
  maintenance_cost: "高"       # 各プラットフォーム個別対応必要
  testing_scope: "各プラットフォーム専用テスト + 統合テスト"
```

## 影響度分析マトリックス

### 機能タイプ別影響度

#### データ処理・ビジネスロジック中心機能
```yaml
function_type: "data_processing_business_logic"
description: "計算・データ変換・ビジネスルール中心の機能"

platform_impact:
  android: "Low" # 共通ロジック使用、UI層のみ個別実装
  ios: "Low"
  web: "Low" 
  desktop: "Low"
  server: "Medium" # API・データベース連携が必要

implementation_distribution:
  shared_common: 80%      # ビジネスロジックは共通実装
  expect_actual: 10%      # データアクセス抽象化
  platform_specific: 10% # UI・プラットフォーム統合

effort_estimation:
  base_effort: "Medium"
  platform_multiplier: 1.2
  total_effort: "Medium+"
```

#### UI・UX中心機能
```yaml
function_type: "ui_ux_heavy"
description: "ユーザーインターフェース・インタラクション中心の機能"

platform_impact:
  android: "High" # Android Material Design適用
  ios: "High"     # iOS Human Interface Guidelines適用
  web: "High"     # レスポンシブ・Web標準対応
  desktop: "High" # デスクトップ UI paradigm適用
  server: "Low"   # UIに関連しない

implementation_distribution:
  shared_common: 30%      # 状態管理・ビジネスロジック
  expect_actual: 20%      # UI抽象化・ナビゲーション
  platform_specific: 50% # プラットフォーム固有UI実装

effort_estimation:
  base_effort: "High"
  platform_multiplier: 2.0
  total_effort: "Very High"
```

#### プラットフォーム統合機能
```yaml
function_type: "platform_integration"
description: "OS・プラットフォーム固有機能との統合"

examples:
  - "カメラ・ファイルアクセス"
  - "位置情報・センサー"
  - "プッシュ通知"
  - "認証・バイオメトリクス"

platform_impact:
  android: "Very High" # Android固有API・権限システム
  ios: "Very High"     # iOS固有Framework・権限システム
  web: "Medium"        # Web API制限・セキュリティ制約
  desktop: "Medium"    # OS統合・ファイルシステム
  server: "Low"        # 直接関連なし

implementation_distribution:
  shared_common: 20%      # 共通インターフェース・データモデル
  expect_actual: 30%      # プラットフォーム抽象化
  platform_specific: 50% # プラットフォーム固有実装

effort_estimation:
  base_effort: "High"
  platform_multiplier: 2.5
  total_effort: "Very High"
```

#### 性能・最適化機能
```yaml
function_type: "performance_optimization"
description: "パフォーマンス・メモリ・電力最適化機能"

platform_impact:
  android: "High"      # Androidメモリ・電力管理
  ios: "High"         # iOSメモリ・電力管理
  web: "Medium"       # ブラウザ最適化・Bundle size
  desktop: "Medium"   # JVM最適化・メモリ管理
  server: "High"      # スケーラビリティ・パフォーマンス

implementation_distribution:
  shared_common: 40%      # 共通アルゴリズム最適化
  expect_actual: 20%      # プラットフォーム固有最適化抽象化
  platform_specific: 40% # 各プラットフォーム最適化

effort_estimation:
  base_effort: "High"
  platform_multiplier: 1.8
  total_effort: "Very High"
```

## 影響度評価基準

### 実装工数影響度
```yaml
impact_levels:
  none:
    description: "影響なし・変更不要"
    effort_multiplier: 0.0
    examples: ["他プラットフォームのみの変更"]
  
  minimal:
    description: "最小限の変更・設定調整"
    effort_multiplier: 0.1
    examples: ["設定値変更", "定数更新"]
  
  low:
    description: "小規模な修正・既存機能拡張"
    effort_multiplier: 0.3
    examples: ["バリデーション追加", "UI微調整"]
  
  medium:
    description: "中規模の機能追加・変更"
    effort_multiplier: 0.6
    examples: ["新API追加", "画面レイアウト変更"]
  
  high:
    description: "大規模な機能実装・大幅変更"
    effort_multiplier: 1.0
    examples: ["新機能実装", "アーキテクチャ変更"]
  
  very_high:
    description: "全面的な実装・根本的変更"
    effort_multiplier: 1.5
    examples: ["新プラットフォーム対応", "技術スタック変更"]
```

### テスト影響度
```yaml
testing_impact:
  unit_tests:
    shared_common: "共通テストで対応可能"
    expect_actual: "インターフェーステスト + 実装テスト"
    platform_specific: "プラットフォーム固有テスト必要"
  
  integration_tests:
    cross_platform: "プラットフォーム間統合テスト"
    platform_integration: "プラットフォーム内統合テスト"
    external_integration: "外部システム統合テスト"
  
  ui_tests:
    shared_behavior: "共通UI動作テスト"
    platform_specific: "プラットフォーム固有UIテスト"
    visual_regression: "ビジュアルリグレッションテスト"
  
  performance_tests:
    benchmark: "パフォーマンスベンチマーク"
    load_testing: "負荷テスト（主にServer）"
    memory_profiling: "メモリ・リソース使用量テスト"
```

## 分析プロセス

### Step 1: 要件分析
```yaml
requirement_analysis:
  functional_requirements:
    - "何を実現する機能か"
    - "ユーザーが期待する動作"
    - "ビジネス価値・目的"
  
  technical_requirements:
    - "技術的制約・要件"
    - "パフォーマンス要件"
    - "セキュリティ・コンプライアンス要件"
  
  platform_requirements:
    - "プラットフォーム固有要件"
    - "対象ユーザー・デバイス"
    - "リリース戦略・優先順位"
```

### Step 2: 実装レイヤー分類
```yaml
layer_classification:
  classification_questions:
    - "共通ロジックとして実装可能か？"
    - "プラットフォーム抽象化が必要か？"
    - "プラットフォーム固有実装が必要か？"
  
  decision_matrix:
    shared_common:
      criteria: "プラットフォーム非依存・純粋なロジック"
      examples: ["計算処理", "データ変換", "バリデーション"]
    
    expect_actual:
      criteria: "プラットフォーム依存だが抽象化可能"
      examples: ["ファイルIO", "ネットワーク", "永続化"]
    
    platform_specific:
      criteria: "プラットフォーム固有・抽象化困難"
      examples: ["UI実装", "OS統合", "プラットフォーム固有機能"]
```

### Step 3: 影響度マッピング
```yaml
impact_mapping:
  per_platform_analysis:
    android:
      impact_factors: ["UI変更", "権限追加", "ライブラリ依存", "API変更"]
      risk_factors: ["フラグメンテーション", "権限モデル", "メモリ制約"]
    
    ios:
      impact_factors: ["UI変更", "Framework使用", "App Store要件", "権限設定"]
      risk_factors: ["App Store審査", "iOS版互換性", "メモリ制約"]
    
    web:
      impact_factors: ["ブラウザ互換", "レスポンシブ対応", "Bundle size", "PWA機能"]
      risk_factors: ["ブラウザ差異", "セキュリティ制約", "パフォーマンス"]
    
    desktop:
      impact_factors: ["OS統合", "ウィンドウ管理", "ファイルシステム", "ショートカット"]
      risk_factors: ["OS差異", "配布方法", "アップデート機構"]
    
    server:
      impact_factors: ["API変更", "データベース変更", "認証変更", "デプロイ影響"]
      risk_factors: ["スケーラビリティ", "セキュリティ", "運用影響"]
```

### Step 4: 総合評価・実装戦略
```yaml
comprehensive_assessment:
  effort_estimation:
    total_story_points: "各プラットフォーム影響度の合計"
    critical_path: "実装順序・依存関係の特定"
    resource_allocation: "担当agent・リソース配分"
  
  risk_assessment:
    technical_risks: "実装・統合リスク"
    schedule_risks: "期限・依存関係リスク"
    quality_risks: "品質・テストカバレッジリスク"
  
  implementation_strategy:
    phasing: "段階的実装戦略"
    prioritization: "プラットフォーム優先順位"
    parallel_execution: "並行実装可能性"
```

## 分析テンプレート

### プラットフォーム影響分析シート
```markdown
# プラットフォーム影響分析: {機能名称}

## 要件概要
- **機能概要**: {機能の説明}
- **ビジネス価値**: {提供する価値}
- **技術要件**: {技術的な要件・制約}

## レイヤー分類
### Shared Common (shared/commonMain)
- **実装範囲**: {共通実装する範囲}
- **見積もり**: {Story Points}
- **リスク**: {技術的リスク}

### Platform Abstraction (expect/actual)
- **抽象化対象**: {抽象化が必要な部分}
- **見積もり**: {Story Points}
- **リスク**: {実装・統合リスク}

### Platform Specific
#### Android
- **影響度**: {None/Minimal/Low/Medium/High/Very High}
- **実装内容**: {Android固有実装の詳細}
- **見積もり**: {Story Points}
- **特記事項**: {権限・ライブラリ・制約等}

#### iOS
- **影響度**: {None/Minimal/Low/Medium/High/Very High}
- **実装内容**: {iOS固有実装の詳細}
- **見積もり**: {Story Points}
- **特記事項**: {Framework・App Store・制約等}

#### Web
- **影響度**: {None/Minimal/Low/Medium/High/Very High}
- **実装内容**: {Web固有実装の詳細}
- **見積もり**: {Story Points}
- **特記事項**: {ブラウザ互換・PWA・制約等}

#### Desktop
- **影響度**: {None/Minimal/Low/Medium/High/Very High}
- **実装内容**: {Desktop固有実装の詳細}
- **見積もり**: {Story Points}
- **特記事項**: {OS統合・配布・制約等}

#### Server
- **影響度**: {None/Minimal/Low/Medium/High/Very High}
- **実装内容**: {Server実装の詳細}
- **見積もり**: {Story Points}
- **特記事項**: {API・DB・デプロイ等}

## 総合評価
### 工数見積もり
- **総Story Points**: {合計ポイント}
- **実装期間**: {推定期間}
- **リソース要件**: {必要スキル・人数}

### 実装戦略
- **実装順序**: {推奨実装順序}
- **クリティカルパス**: {重要な依存関係}
- **並行実装**: {並行可能な範囲}

### リスク・制約
- **技術リスク**: {技術的な課題・リスク}
- **スケジュールリスク**: {期限・依存関係リスク}
- **リソースリスク**: {人的・技術リソースリスク}

## 品質保証計画
### テスト戦略
- **単体テスト**: {プラットフォーム別テスト計画}
- **統合テスト**: {プラットフォーム間統合テスト}
- **E2Eテスト**: {エンドツーエンドテスト}

### 品質指標
- **カバレッジ目標**: {テストカバレッジ目標}
- **パフォーマンス目標**: {性能要件}
- **品質ゲート**: {品質確保の基準}
```

---

## まとめ

このプラットフォーム影響分析手法により、Kotlin Multiplatformプロジェクトにおける変更・新機能の影響を体系的に評価し、効率的な実装計画を策定できます。

継続的な分析精度向上により、見積もり精度の向上とプロジェクトリスクの最小化を実現します。