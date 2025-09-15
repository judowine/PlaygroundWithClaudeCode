# UI Development Command

**Description**: PBIからCompose UIを対話的に開発するワークフローを実行します

**Usage**: `/ui-dev [PBI-ID] [追加オプション]`

**Examples**:
- `/ui-dev PBI-001A`
- `/ui-dev PBI-002B --feature=login`
- `/ui-dev PBI-003 --platform=android`

## Command Implementation

```javascript
async function execute(args) {
  // PBI ID の抽出
  const pbiId = args[0];
  const options = parseOptions(args.slice(1));

  if (!pbiId) {
    throw new Error('PBI ID is required. Usage: /ui-dev [PBI-ID]');
  }

  // ワークフローテンプレートの読み込み
  const workflowTemplate = await readFile('/Users/shota/AndroidStudioProjects/Playground/docs/workflows/ui-development-workflow.md');

  // 段階的実行
  await executeWorkflowSteps(pbiId, options, workflowTemplate);
}

async function executeWorkflowSteps(pbiId, options, template) {
  const workflow = [
    { step: 0, name: 'branch_setup', agent: 'strategic-project-manager' },
    { step: 1, name: 'pbi_analysis', agent: 'product-owner-pbi-manager' },
    { step: 2, name: 'prototype_creation', agent: 'design-system-ui-architect' },
    { step: 3, name: 'interactive_review', agent: 'ux-persona-journey-designer' },
    { step: 4, name: 'commit_push', agent: 'strategic-project-manager' },
    { step: 5, name: 'pr_creation', agent: 'strategic-project-manager' }
  ];

  for (const stage of workflow) {
    await executeStage(stage, pbiId, options);
  }
}
```

## Workflow Process

### Step 0: 作業ブランチの作成
**Agent**: `strategic-project-manager`
**目的**: UI開発専用ブランチの作成と環境準備

**実行内容**:
- 現在のブランチ状態確認
- feature/ui-[PBI-ID] ブランチの作成
- 依存関係とビルド環境の確認

### Step 1: PBIの内容確認・不明点解消
**Agent**: `product-owner-pbi-manager`
**目的**: 90%以上の確信が持てるまで要件を明確化

**確認項目**:
- **UI要件の詳細化**: 画面レイアウト、操作フロー、状態管理
- **ユーザーエクスペリエンス**: インタラクション、アニメーション、フィードバック
- **プラットフォーム要件**: 各プラットフォームでの表示・動作差異
- **技術制約**: パフォーマンス、アクセシビリティ、セキュリティ
- **受け入れ条件**: 具体的で測定可能な完了基準

**アウトプット**: 明確化されたUI要件書

### Step 2: プロトタイプ作成とPreview実装
**Agent**: `design-system-ui-architect`
**目的**: 動作確認可能なComposeプロトタイプの作成

**実装内容**:
- **UI状態設計**: UiState クラスの定義
- **コンポーネント構造**: 再利用可能なUI要素の分解
- **Compose実装**: レイアウト、スタイリング、状態管理
- **Preview実装**: @Preview アノテーションでの表示確認
- **プラットフォーム対応**: 必要に応じたプラットフォーム固有実装

**品質基準**:
- Preview で正常表示される
- 基本的なユーザーインタラクションが動作する
- アクセシビリティ対応済み

### Step 3: 対話的レビューと修正点確認
**Agent**: `ux-persona-journey-designer`
**目的**: ユーザー視点でのUI/UX評価と改善

**レビュー観点**:
- **ユーザビリティ**: 直感的な操作性、学習コスト
- **視覚デザイン**: デザインシステム準拠、一貫性
- **インタラクション**: レスポンシブ性、フィードバック
- **アクセシビリティ**: スクリーンリーダー対応、コントラスト
- **プラットフォーム一貫性**: 各プラットフォームでの体験統一

**対話プロセス**:
1. プロトタイプのデモ実行
2. ユーザーシナリオベースの操作確認
3. 改善点の特定と優先度付け
4. 修正実装とre-review

### Step 4: コミット＆プッシュ
**Agent**: `strategic-project-manager`
**目的**: 段階的コミット戦略での変更保存

**コミット戦略**:
```bash
# UI状態設計
git commit -m "[feat] UI: [PBI-ID] UiState クラスを定義"

# コンポーネント実装
git commit -m "[feat] UI: [PBI-ID] メインコンポーネントを実装"

# Preview実装
git commit -m "[feat] UI: [PBI-ID] Preview実装と表示確認"

# レビュー対応
git commit -m "[fix] UI: [PBI-ID] UXレビュー対応 - アクセシビリティ改善"
```

### Step 5: PR作成
**Agent**: `strategic-project-manager`
**目的**: 統合テスト可能なPRの作成

**PR要件**:
- **タイトル**: `[UI] [PBI-ID] [機能名] UI Implementation`
- **説明**: UI要件書リンク、Previewスクリーンショット、対話レビュー結果
- **レビュー観点**: UI/UXデザイン、Compose実装、プラットフォーム対応

## Agent Specialization

### design-system-ui-architect の役割
**UI実装の専門家として**:
- Compose Multiplatform の最適実装
- デザインシステム準拠の確保
- パフォーマンス最適化
- 再利用可能コンポーネント設計

### ux-persona-journey-designer の役割
**UX評価の専門家として**:
- ユーザーシナリオベースの評価
- アクセシビリティ監査
- プラットフォーム間UX一貫性確保
- ユーザビリティテスト実施

### product-owner-pbi-manager の役割
**要件明確化の専門家として**:
- ステークホルダー視点での要件確認
- ビジネス価値と技術実装のバランス調整
- 受け入れ条件の具体化

## Quality Gates

### Step 1完了基準（要件明確化）
```yaml
criteria:
  requirements_clarity: ">= 90% (確信度)"
  ui_specifications: "詳細仕様書完成"
  acceptance_criteria: "測定可能な基準定義済み"
  stakeholder_approval: "要件承認取得済み"
```

### Step 2完了基準（プロトタイプ）
```yaml
criteria:
  preview_functionality: "全Preview正常表示"
  basic_interaction: "主要インタラクション動作確認"
  accessibility_compliance: "基本アクセシビリティ対応済み"
  code_quality: "Compose ベストプラクティス準拠"
```

### Step 3完了基準（レビュー）
```yaml
criteria:
  ux_evaluation_score: ">= 4.0/5.0"
  usability_issues: "重要度High以上の課題解決済み"
  design_system_compliance: "100%準拠"
  cross_platform_consistency: "各プラットフォーム確認済み"
```

## Output Structure

各ステップで以下の成果物を生成：

### Step 1: UI要件書
```
docs/design/ui-requirements/[PBI-ID]-ui-requirements.md
├── UI機能仕様
├── ユーザーインタラクション詳細
├── プラットフォーム要件
├── 受け入れ条件
└── 技術制約
```

### Step 2: Compose実装
```
composeApp/src/commonMain/kotlin/com/example/playground/
├── ui/
│   ├── components/[FeatureName]/
│   │   ├── [FeatureName]Screen.kt
│   │   ├── [FeatureName]Component.kt
│   │   └── [FeatureName]Preview.kt
│   └── state/
│       └── [FeatureName]UiState.kt
```

### Step 3: UXレビューレポート
```
docs/reviews/ux-reviews/[PBI-ID]-ux-review.md
├── ユーザビリティ評価
├── アクセシビリティ監査結果
├── デザインシステム準拠確認
├── 改善実装結果
└── 最終承認記録
```

## Error Handling

### 要件不明確時の対応
- product-owner-pbi-manager による追加ヒアリング
- ステークホルダーとの直接確認セッション
- プロトタイプベースの要件確認

### 技術実装困難時の対応
- architecture-strategist による代替案検討
- プラットフォーム制約の技術的解決策提示
- 段階的実装計画の再策定

### UXレビュー不合格時の対応
- 具体的改善項目の特定と優先度付け
- 修正実装とre-review の繰り返し
- 必要に応じた要件再確認

## Configuration

### デフォルト設定
```yaml
default_platform: "android"
review_threshold: 4.0
commit_strategy: "incremental"
preview_required: true
accessibility_check: true
```

### カスタマイズオプション
```yaml
platforms: ["android", "ios", "desktop", "web"]
ui_framework: "compose_multiplatform"
design_system: "material3"
review_agents: ["ux-persona-journey-designer", "design-system-ui-architect"]
```