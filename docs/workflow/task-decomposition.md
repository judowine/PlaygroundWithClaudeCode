# タスク分解フレームワーク

## 概要
Kotlin Multiplatformプロジェクトの複雑な開発要求を効率的に分解し、実装可能な単位まで細分化するための体系的フレームワークです。`multiplatform-dev-orchestrator`が使用する標準的な分解手法と評価基準を定義します。

## 分解階層構造

### 1. PBI (Product Backlog Item) レベル
```
PBI: ビジネス価値を提供する機能単位
例: "マルチプラットフォーム商品管理システムの実装"
```

#### 分解基準
- **ビジネス価値**: 独立したユーザー価値を提供
- **実装規模**: 2-4スプリント程度で完了可能
- **リリース単位**: 単独でリリース・検証可能

### 2. Epic レベル
```
Epic: PBIを技術的・機能的観点で大分類
例: 
- Epic 1: 基盤アーキテクチャ構築
- Epic 2: UI/UXデザインシステム
- Epic 3: プラットフォーム別実装
- Epic 4: 統合・品質保証
```

#### 分解基準
- **技術領域**: アーキテクチャ、デザイン、実装、品質保証
- **プラットフォーム分割**: Android/iOS/Web/Desktop/Server
- **機能グループ**: 関連する機能の集合

### 3. Feature レベル
```
Feature: Epicを実装可能な機能単位に分割
例: Epic "基盤アーキテクチャ構築"
- Feature 1.1: データモデル定義
- Feature 1.2: API設計・実装
- Feature 1.3: プラットフォーム抽象化実装
- Feature 1.4: 認証・セキュリティ実装
```

#### 分解基準
- **単一責任**: 一つの明確な機能を担当
- **独立性**: 他Featureとの依存関係を最小化
- **テスト可能性**: 独立してテスト可能

### 4. Task レベル
```
Task: Featureの実装に必要な具体的作業単位
例: Feature "データモデル定義"
- Task 1.1.1: Productデータクラス実装
- Task 1.1.2: バリデーションロジック実装
- Task 1.1.3: シリアライゼーション設定
- Task 1.1.4: 単体テスト作成
```

#### 分解基準
- **作業単位**: 1-2日で完了可能
- **単一担当**: 一人のDeveloperが担当可能
- **明確な完了条件**: Definition of Doneが明確

## プラットフォーム影響分析

### プラットフォーム分類マトリックス

#### 共通実装 (shared/commonMain)
```yaml
category: "shared_implementation"
scope:
  - business_logic: "ビジネスロジック・計算処理"
  - data_models: "データクラス・エンティティ"
  - utilities: "ユーティリティ・ヘルパー関数"
  - constants: "定数・設定値"
  - validation: "バリデーション・ビジネスルール"

implementation_strategy:
  approach: "共通実装優先"
  coverage: "80-90%の機能を共通化"
  benefits: ["コード重複排除", "一貫性確保", "保守性向上"]
```

#### プラットフォーム抽象化 (expect/actual)
```yaml
category: "platform_abstraction"
scope:
  - platform_services: "プラットフォーム固有サービス"
  - file_io: "ファイル操作・永続化"
  - network: "ネットワークアクセス・HTTP"
  - security: "暗号化・認証・セキュリティ"
  - ui_integration: "プラットフォーム特有のUI統合"

implementation_strategy:
  approach: "expect/actualパターン使用"
  design_principle: "共通インターフェース、個別実装"
  testing: "共通テスト + プラットフォーム固有テスト"
```

#### プラットフォーム固有実装
```yaml
android:
  category: "android_specific"
  scope:
    - ui_components: "Compose for Android"
    - platform_integration: "Android SDK統合"
    - lifecycle: "Activity・Fragment lifecycle"
    - permissions: "Android権限システム"
    - native_features: "カメラ・GPS・センサー等"

ios:
  category: "ios_specific"
  scope:
    - ui_components: "Compose Multiplatform for iOS"
    - platform_integration: "iOS Framework統合"
    - lifecycle: "UIViewController lifecycle"
    - permissions: "iOS権限システム"
    - native_features: "Core Location・AVFoundation等"

web:
  category: "web_specific"
  scope:
    - ui_components: "Compose for Web (WASM)"
    - dom_integration: "DOM操作・Browser API"
    - responsive_design: "レスポンシブレイアウト"
    - web_standards: "Web標準・アクセシビリティ"
    - pwa_features: "Service Worker・PWA機能"

desktop:
  category: "desktop_specific"
  scope:
    - ui_components: "Compose for Desktop"
    - os_integration: "OS固有機能統合"
    - window_management: "ウィンドウ管理"
    - native_menus: "ネイティブメニュー・ショートカット"
    - file_system: "ファイルシステム統合"

server:
  category: "server_specific"
  scope:
    - api_endpoints: "REST API・GraphQL"
    - database: "データベース操作・ORM"
    - authentication: "認証・認可・セッション管理"
    - middleware: "ミドルウェア・フィルター"
    - deployment: "デプロイメント・監視"
```

## 分解手法・パターン

### 1. ユーザーフロー分解
```yaml
method: "user_flow_decomposition"
description: "ユーザーの操作フローに基づいてタスクを分解"

example:
  user_story: "ユーザーが商品を検索・購入する"
  
  flow_steps:
    1: "商品検索画面表示"
    2: "検索条件入力・実行"
    3: "検索結果表示・フィルタリング"
    4: "商品詳細表示"
    5: "カート追加・購入手続き"
    6: "決済・注文確定"

  task_decomposition:
    ui_tasks:
      - "商品検索UI実装 (各プラットフォーム)"
      - "検索結果リストUI実装"
      - "商品詳細画面UI実装"
      - "購入フローUI実装"
    
    logic_tasks:
      - "検索ロジック実装 (shared)"
      - "カート管理ロジック実装"
      - "決済処理ロジック実装"
    
    integration_tasks:
      - "API統合・エラーハンドリング"
      - "状態管理・データフロー"
      - "プラットフォーム間データ同期"
```

### 2. レイヤー別分解
```yaml
method: "layer_based_decomposition"
description: "アーキテクチャレイヤーに基づいてタスクを分解"

layers:
  presentation:
    scope: "UI・UX・ユーザーインタラクション"
    tasks:
      - "画面設計・レイアウト実装"
      - "ユーザーインタラクション実装"
      - "状態管理・データバインディング"
      
  business:
    scope: "ビジネスロジック・ドメインモデル"
    tasks:
      - "ドメインモデル定義"
      - "ビジネスルール実装"
      - "ワークフロー・処理フロー"
      
  data:
    scope: "データアクセス・永続化・外部連携"
    tasks:
      - "データモデル・Repository実装"
      - "API統合・外部サービス連携"
      - "ローカルストレージ・キャッシュ"
      
  infrastructure:
    scope: "プラットフォーム固有・技術基盤"
    tasks:
      - "プラットフォーム抽象化実装"
      - "セキュリティ・認証実装"
      - "監視・ログ・エラー処理"
```

### 3. プラットフォーム優先度分解
```yaml
method: "platform_priority_decomposition"
description: "プラットフォーム展開戦略に基づいてタスクを優先順位付け"

strategies:
  core_first:
    description: "共通コア機能を最優先実装"
    sequence:
      1: "shared/commonMain基盤実装"
      2: "expect/actualインターフェース定義"
      3: "プラットフォーム固有実装"
      4: "統合・テスト"
    
  flagship_platform:
    description: "主要プラットフォームから開始"
    sequence:
      1: "主要プラットフォーム完全実装"
      2: "共通ロジック抽出・shared化"
      3: "他プラットフォーム展開"
      4: "最適化・統合"
    
  parallel_development:
    description: "全プラットフォーム並行開発"
    sequence:
      1: "共通基盤並行実装"
      2: "プラットフォーム固有実装並行"
      3: "統合・調整"
      4: "最適化・品質保証"
```

## 見積もり・複雑度評価

### ストーリーポイント算定

#### 基準ポイント定義
```yaml
story_points:
  1_point:
    description: "単純な設定変更・小修正"
    examples: ["定数変更", "単純なUI調整", "ログ出力追加"]
    effort: "2-4時間"
    
  2_points:
    description: "単一ファイル内の小機能追加"
    examples: ["バリデーション追加", "単純な計算ロジック", "基本的なUI部品"]
    effort: "0.5-1日"
    
  3_points:
    description: "複数ファイルにまたがる小機能"
    examples: ["新しいデータ項目追加", "基本的な検索機能", "単純なAPI追加"]
    effort: "1-1.5日"
    
  5_points:
    description: "中規模機能・新しいコンポーネント"
    examples: ["新しい画面追加", "認証機能追加", "データベーステーブル追加"]
    effort: "2-3日"
    
  8_points:
    description: "大規模機能・複雑な統合"
    examples: ["新しいモジュール追加", "外部システム統合", "複雑なワークフロー"]
    effort: "1週間"
    
  13_points:
    description: "非常に複雑・高リスク機能"
    examples: ["アーキテクチャ変更", "セキュリティ機能", "パフォーマンス最適化"]
    effort: "2週間以上"
```

#### プラットフォーム係数
```yaml
platform_multipliers:
  single_platform:
    android: 1.0
    ios: 1.0
    web: 1.0
    desktop: 1.0
    server: 1.0
    
  multi_platform:
    two_platforms: 1.3
    three_platforms: 1.5
    four_platforms: 1.8
    all_platforms: 2.0
    
  complexity_factors:
    shared_logic_heavy: 0.8  # 共通ロジック中心の場合は効率化
    ui_heavy: 1.2           # UI実装中心の場合は複雑度増加
    platform_integration: 1.5  # プラットフォーム統合が必要な場合
    new_technology: 1.8     # 新技術・学習が必要な場合
```

### リスク評価マトリックス

#### 技術リスク
```yaml
technical_risks:
  low_risk:
    description: "既知の技術・実績のあるパターン"
    examples: ["CRUD操作", "基本的なUI", "標準的なAPI"]
    multiplier: 1.0
    
  medium_risk:
    description: "部分的に未経験・複雑な統合"
    examples: ["新しいライブラリ使用", "複雑なデータフロー", "パフォーマンス要件"]
    multiplier: 1.2
    
  high_risk:
    description: "未経験技術・複雑なアーキテクチャ"
    examples: ["新しいフレームワーク", "セキュリティ要件", "リアルタイム処理"]
    multiplier: 1.5
    
  very_high_risk:
    description: "実験的・研究開発要素を含む"
    examples: ["最新技術の実験", "複雑なアルゴリズム", "パフォーマンス限界への挑戦"]
    multiplier: 2.0
```

#### 依存関係リスク
```yaml
dependency_risks:
  internal_dependencies:
    low: "同チーム内・明確な依存関係"
    medium: "他チーム・調整が必要な依存関係"
    high: "外部チーム・不確定な依存関係"
    
  external_dependencies:
    low: "安定したライブラリ・API"
    medium: "メンテナンスされているサードパーティ"
    high: "実験的・不安定な外部依存"
    
  platform_dependencies:
    low: "全プラットフォームで利用可能"
    medium: "一部プラットフォームで制限"
    high: "プラットフォーム固有・代替策が必要"
```

## 分解品質チェック

### SMART原則適用
```yaml
smart_criteria:
  specific:
    check: "タスクが具体的で明確か"
    good_example: "商品検索APIのGETエンドポイント実装"
    bad_example: "検索機能の実装"
    
  measurable:
    check: "完了条件が測定可能か"
    good_example: "APIが正常レスポンスを返し、単体テストが通過"
    bad_example: "検索が動作する"
    
  achievable:
    check: "現実的な工数・スキルで実現可能か"
    evaluation_factors: ["チームスキル", "利用可能時間", "技術制約"]
    
  relevant:
    check: "上位目標・PBIに貢献するか"
    traceability: "PBI → Epic → Feature → Taskの関連性確認"
    
  time_bound:
    check: "明確な期限が設定されているか"
    guideline: "Task: 1-2日, Feature: 1週間, Epic: 1-2スプリント"
```

### 独立性チェック
```yaml
independence_check:
  task_independence:
    criteria: "他のタスクに依存せず実行可能"
    exceptions: "明確に定義された依存関係のみ許可"
    
  platform_independence:
    criteria: "プラットフォーム固有タスクが独立実行可能"
    shared_dependencies: "共通基盤への依存のみ許可"
    
  team_independence:
    criteria: "単一担当者・チームで完了可能"
    collaboration: "必要な協力関係を明確化"
```

### 完全性チェック
```yaml
completeness_check:
  coverage:
    functional: "全機能要件がタスク化されている"
    non_functional: "品質要件・制約が考慮されている"
    testing: "テストタスクが含まれている"
    documentation: "ドキュメント更新が含まれている"
    
  definition_of_done:
    criteria: "各レベルでDoD基準が明確"
    verification: "完了判定が客観的に可能"
    
  traceability:
    backward: "上位要件からの漏れがない"
    forward: "下位タスクが上位目標に貢献"
```

## 分解テンプレート

### PBI分解テンプレート
```markdown
# PBI分解: {PBI名称}

## PBI概要
- **ID**: {PBI-ID}
- **優先度**: {High/Medium/Low}
- **見積もり**: {Story Points}
- **期限**: {Target Date}

## Epic分解
### Epic 1: {Epic名称}
- **概要**: {Epic概要}
- **担当Agent**: {primary-agent}
- **連携Agent**: {support-agents}
- **見積もり**: {Story Points}
- **依存関係**: {Dependencies}

## Platform Impact Analysis
### 影響範囲
- [ ] Android - {影響内容}
- [ ] iOS - {影響内容}
- [ ] Web - {影響内容}
- [ ] Desktop - {影響内容}
- [ ] Server - {影響内容}

### 共通実装 vs 個別実装
- **共通実装**: {shared実装範囲}
- **expect/actual**: {抽象化が必要な部分}
- **プラットフォーム固有**: {個別実装が必要な部分}

## リスク・制約
- **技術リスク**: {技術的な課題・リスク}
- **依存関係**: {内部・外部依存関係}
- **制約条件**: {時間・リソース・技術的制約}

## 成功基準
- **機能要件**: {functional requirements}
- **非機能要件**: {non-functional requirements}
- **受入条件**: {acceptance criteria}
```

### Feature分解テンプレート
```markdown
# Feature分解: {Feature名称}

## Feature詳細
- **Epic**: {所属Epic}
- **担当Agent**: {responsible-agent}
- **見積もり**: {Story Points}
- **期限**: {Target Date}

## Task分解
### Task 1: {Task名称}
- **概要**: {Task詳細}
- **担当**: {Developer/Agent}
- **工数**: {Hours/Days}
- **依存**: {Dependencies}
- **完了条件**: {Definition of Done}

## 技術仕様
- **実装方針**: {Implementation approach}
- **使用技術**: {Technologies/Libraries}
- **アーキテクチャ**: {Architecture considerations}

## テスト戦略
- **単体テスト**: {Unit test requirements}
- **統合テスト**: {Integration test requirements}
- **E2Eテスト**: {End-to-end test requirements}
```

---

## まとめ

このタスク分解フレームワークにより、Kotlin Multiplatformプロジェクトの複雑な要求を体系的・効率的に分解し、適切なagentに割り当てることが可能になります。

継続的な改善により、分解精度・見積もり精度を向上させ、プロジェクト全体の予測性・制御性を高めます。