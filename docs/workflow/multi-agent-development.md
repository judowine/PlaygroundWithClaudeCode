# Multi-Agent開発プロセス

## 概要
Kotlin Multiplatformプロジェクトにおいて、`multiplatform-dev-orchestrator`が統括する11の専門agentを活用した効率的な開発プロセスを定義します。エンドツーエンドの開発フローから日常的な運用まで、包括的なワークフローを提供します。

## 開発プロセス全体像

### 開発フェーズ構造
```
Phase 0: プロジェクト初期化・計画
    ↓
Phase 1: 要件定義・分析
    ↓
Phase 2: UX・デザイン設計
    ↓
Phase 3: アーキテクチャ・技術設計
    ↓
Phase 4: 実装・開発
    ↓
Phase 5: 統合・品質保証
    ↓
Phase 6: リリース・デプロイ
    ↓
Phase 7: 運用・保守
```

## Phase 0: プロジェクト初期化・計画

### 目的
プロジェクト全体の方向性を確立し、Multi-Agent開発体制を構築する

### 参加Agent
- **Primary**: strategic-project-manager
- **Secondary**: multiplatform-dev-orchestrator

### 主要活動

#### 0.1 プロジェクト憲章作成
```yaml
activity: project_charter_creation
duration: 2-3日
owner: strategic-project-manager

deliverables:
  - project_charter.md
  - stakeholder_analysis.md
  - initial_scope_definition.md
  
key_decisions:
  - project_objectives: "明確なビジネス目標設定"
  - success_metrics: "測定可能な成功指標定義"
  - target_platforms: "対象プラットフォーム確定"
  - timeline_constraints: "リリース期限・マイルストーン設定"
  
quality_gates:
  - stakeholder_alignment: "主要ステークホルダーの合意"
  - feasibility_confirmation: "初期実現可能性確認"
  - budget_approval: "予算・リソース承認"
```

#### 0.2 Multi-Agent体制構築
```yaml
activity: agent_team_setup
duration: 1-2日
owner: multiplatform-dev-orchestrator

deliverables:
  - agent_roles_responsibilities.md
  - collaboration_protocols.md
  - communication_channels.md
  
key_decisions:
  - agent_specialization_mapping: "専門領域とagent割り当て"
  - collaboration_patterns: "agent間連携方法定義"
  - escalation_procedures: "課題解決・意思決定プロセス"
  - quality_standards: "開発品質基準設定"
  
quality_gates:
  - agent_readiness: "全agentの準備完了"
  - process_understanding: "プロセス理解・合意"
  - tool_setup: "必要ツール・環境準備"
```

### 成果物
- プロジェクト憲章・スコープ定義
- Agent体制・責任分担表
- コミュニケーション・エスカレーションプロトコル
- 初期スケジュール・マイルストーン計画

## Phase 1: 要件定義・分析

### 目的
ステークホルダーニーズを詳細に分析し、実装可能なPBIとして整理する

### 参加Agent
- **Primary**: product-owner-pbi-manager, pbi-refinement-facilitator
- **Secondary**: market-analyst, multiplatform-dev-orchestrator
- **Support**: strategic-project-manager

### 主要活動

#### 1.1 ステークホルダーヒアリング
```yaml
activity: stakeholder_interviews
duration: 1-2週間
owner: product-owner-pbi-manager
support: market-analyst

approach:
  preparation:
    - stakeholder_identification: "キーステークホルダー特定"
    - interview_planning: "構造化インタビュー計画"
    - context_research: "業界・競合調査"
    
  execution:
    - structured_interviews: "体系的ヒアリング実施"
    - requirement_capture: "要求事項詳細記録"
    - pain_point_analysis: "課題・ペインポイント分析"
    
  analysis:
    - requirement_categorization: "機能・非機能要件分類"
    - priority_assessment: "ビジネス価値・優先度評価"
    - feasibility_evaluation: "初期技術実現性評価"

deliverables:
  - stakeholder_interview_reports/
  - business_requirements_specification.md
  - competitive_analysis_report.md
  - market_opportunity_assessment.md
```

#### 1.2 要件分析・整理
```yaml
activity: requirements_analysis
duration: 1週間
owner: pbi-refinement-facilitator
support: product-owner-pbi-manager

approach:
  requirement_structuring:
    - functional_requirements: "機能要件詳細化"
    - non_functional_requirements: "品質・制約要件定義"
    - platform_requirements: "プラットフォーム固有要件"
    
  user_story_creation:
    - persona_based_stories: "ユーザーペルソナベースストーリー"
    - epic_level_grouping: "Epic レベルでの機能グループ化"
    - acceptance_criteria: "受け入れ条件詳細定義"
    
  dependency_analysis:
    - internal_dependencies: "機能間依存関係"
    - external_dependencies: "外部システム・サービス依存"
    - platform_dependencies: "プラットフォーム制約・依存"

deliverables:
  - requirements_analysis_report.md
  - user_stories_draft/
  - epic_breakdown_structure.md
  - dependency_matrix.md
```

#### 1.3 技術実現性評価
```yaml
activity: technical_feasibility_assessment
duration: 3-5日
owner: multiplatform-dev-orchestrator
support: architecture-strategist (consultation)

approach:
  platform_analysis:
    - kotlin_multiplatform_suitability: "KMP適用可能性評価"
    - platform_specific_constraints: "プラットフォーム制約分析"
    - shared_vs_specific_ratio: "共通実装 vs 個別実装比率"
    
  technology_evaluation:
    - library_ecosystem: "利用可能ライブラリ・SDK評価"
    - performance_requirements: "性能要件実現可能性"
    - integration_complexity: "外部統合複雑度評価"
    
  risk_assessment:
    - technical_risks: "技術的リスク特定"
    - schedule_impact: "スケジュール影響評価"
    - mitigation_strategies: "リスク緩和戦略"

deliverables:
  - technical_feasibility_report.md
  - platform_compatibility_matrix.md
  - technology_stack_recommendations.md
  - risk_assessment_report.md
```

#### 1.4 PBI作成・優先度付け
```yaml
activity: pbi_creation_prioritization
duration: 1週間
owner: pbi-refinement-facilitator
collaboration: product-owner-pbi-manager, multiplatform-dev-orchestrator

approach:
  pbi_creation:
    - story_breakdown: "Epic から Feature レベルへの分解"
    - acceptance_criteria_detail: "詳細受け入れ条件定義"
    - estimation_preparation: "見積もり準備（相対サイジング）"
    
  prioritization:
    - value_assessment: "ビジネス価値評価"
    - effort_estimation: "実装工数見積もり"
    - risk_consideration: "技術・ビジネスリスク考慮"
    - dependency_ordering: "依存関係に基づく順序決定"
    
  definition_of_ready:
    - completeness_check: "DoR基準適合確認"
    - stakeholder_validation: "ステークホルダー検証"
    - team_understanding: "開発チーム理解度確認"

deliverables:
  - docs/pbi/ready/ (Definition of Ready完了PBI)
  - prioritized_backlog.md
  - epic_roadmap.md
  - estimation_baseline.md
```

### Phase 1 品質ゲート
```yaml
quality_gate_criteria:
  requirements_completeness:
    - "全主要要件がPBI化されている"
    - "受け入れ条件が明確で測定可能"
    - "優先順位がビジネス価値に基づいている"
    
  technical_readiness:
    - "技術実現性が確認されている"
    - "主要リスクが特定・評価されている"
    - "技術スタック方針が固まっている"
    
  stakeholder_alignment:
    - "ステークホルダー承認が得られている"
    - "スコープ境界が明確に定義されている"
    - "期待値が適切に設定されている"

gate_reviewers:
  - strategic-project-manager
  - multiplatform-dev-orchestrator
  - key_stakeholders

approval_criteria: "全criteria満足 かつ レビューアー承認"
```

## Phase 2: UX・デザイン設計

### 目的
ユーザー中心設計に基づく一貫性のあるMultiplatform UI/UXを設計する

### 参加Agent
- **Primary**: ux-persona-journey-designer, design-system-ui-architect, interaction-prototyping-agent
- **Secondary**: multiplatform-dev-orchestrator
- **Support**: market-analyst, product-owner-pbi-manager

### 主要活動

#### 2.1 ユーザーリサーチ・ペルソナ設計
```yaml
activity: user_research_persona_design
duration: 1-2週間
owner: ux-persona-journey-designer
support: market-analyst

approach:
  user_research:
    - target_user_identification: "ターゲットユーザー特定"
    - user_interview_planning: "ユーザーインタビュー計画"
    - behavioral_analysis: "ユーザー行動パターン分析"
    
  persona_development:
    - primary_persona_creation: "プライマリペルソナ作成"
    - secondary_persona_definition: "セカンダリペルソナ定義"
    - persona_validation: "ペルソナ検証・精緻化"
    
  journey_mapping:
    - current_journey_analysis: "現在のユーザージャーニー分析"
    - future_journey_design: "理想的ユーザージャーニー設計"
    - touchpoint_identification: "タッチポイント・課題特定"

deliverables:
  - docs/ux/personas/
  - docs/ux/journey_maps/
  - user_research_insights.md
  - ux_requirements_specification.md
```

#### 2.2 デザインシステム構築
```yaml
activity: design_system_development
duration: 2-3週間
owner: design-system-ui-architect
collaboration: ux-persona-journey-designer

approach:
  foundation_design:
    - brand_identity_application: "ブランドアイデンティティ適用"
    - color_palette_creation: "カラーパレット・テーマ設計"
    - typography_system: "タイポグラフィシステム構築"
    - spacing_grid_system: "スペーシング・グリッドシステム"
    
  component_library:
    - atomic_components: "原子レベルコンポーネント設計"
    - molecular_components: "分子レベルコンポーネント設計"
    - organism_components: "組織レベルコンポーネント設計"
    - template_layouts: "テンプレート・レイアウト設計"
    
  platform_adaptation:
    - material_design_integration: "Android Material Design適用"
    - human_interface_guidelines: "iOS Human Interface Guidelines適用"
    - web_design_standards: "Web アクセシビリティ・レスポンシブ対応"
    - desktop_ui_patterns: "Desktop UI パターン適用"

deliverables:
  - docs/design/design_system/
  - design_tokens.json
  - component_specifications/
  - platform_adaptation_guidelines.md
```

#### 2.3 インタラクション・プロトタイプ設計
```yaml
activity: interaction_prototyping
duration: 2週間
owner: interaction-prototyping-agent
input_from: design-system-ui-architect

approach:
  interaction_design:
    - user_flow_optimization: "ユーザーフロー最適化"
    - micro_interaction_design: "マイクロインタラクション設計"
    - animation_specifications: "アニメーション仕様策定"
    
  prototype_creation:
    - low_fidelity_prototypes: "ワイヤーフレーム・ローファイプロトタイプ"
    - high_fidelity_prototypes: "高解像度インタラクティブプロトタイプ"
    - platform_specific_adaptations: "プラットフォーム固有適応"
    
  user_testing:
    - usability_testing_planning: "ユーザビリティテスト計画"
    - prototype_testing_execution: "プロトタイプテスト実施"
    - feedback_integration: "フィードバック統合・改善"

deliverables:
  - interactive_prototypes/ (platform-specific)
  - interaction_specifications.md
  - usability_test_results.md
  - design_iteration_log.md
```

#### 2.4 デザイン統合・検証
```yaml
activity: design_integration_validation
duration: 1週間
owner: multiplatform-dev-orchestrator
collaboration: design-system-ui-architect, interaction-prototyping-agent

approach:
  cross_platform_consistency:
    - visual_consistency_audit: "視覚的一貫性監査"
    - interaction_pattern_alignment: "インタラクション パターン整合"
    - accessibility_compliance_check: "アクセシビリティ準拠確認"
    
  implementation_feasibility:
    - compose_multiplatform_compatibility: "Compose Multiplatform実装可能性"
    - performance_impact_assessment: "パフォーマンス影響評価"
    - development_effort_estimation: "開発工数再見積もり"
    
  stakeholder_validation:
    - design_review_sessions: "デザインレビューセッション"
    - stakeholder_approval_process: "ステークホルダー承認プロセス"
    - user_acceptance_validation: "ユーザー受容性検証"

deliverables:
  - design_consistency_audit_report.md
  - implementation_feasibility_assessment.md
  - stakeholder_approval_documentation.md
  - final_design_specifications/
```

### Phase 2 品質ゲート
```yaml
quality_gate_criteria:
  user_experience_quality:
    - "ユーザーニーズが適切に反映されている"
    - "ユーザージャーニーが最適化されている"
    - "ユーザビリティテストで良好な結果"
    
  design_system_quality:
    - "一貫性のあるデザインシステム構築"
    - "プラットフォーム適応が適切"
    - "アクセシビリティ基準準拠"
    
  implementation_readiness:
    - "技術実装可能性確認済み"
    - "開発工数見積もり妥当性確認"
    - "ステークホルダー承認取得"

gate_reviewers:
  - ux-persona-journey-designer
  - design-system-ui-architect
  - multiplatform-dev-orchestrator

approval_criteria: "全criteria満足 かつ ユーザーテスト合格"
```

## Phase 3: アーキテクチャ・技術設計

### 目的
Kotlin Multiplatformの特性を活かしたスケーラブルなシステムアーキテクチャを設計する

### 参加Agent
- **Primary**: architecture-strategist, backend-security-architect
- **Secondary**: multiplatform-dev-orchestrator
- **Support**: qa-test-strategist, frontend-generalist-dev (consultation)

### 主要活動

#### 3.1 システムアーキテクチャ設計
```yaml
activity: system_architecture_design
duration: 2週間
owner: architecture-strategist
collaboration: backend-security-architect, multiplatform-dev-orchestrator

approach:
  high_level_design:
    - architecture_pattern_selection: "アーキテクチャパターン選択"
    - layer_organization: "レイヤー構成・責任分担"
    - component_interaction: "コンポーネント間相互作用設計"
    
  multiplatform_strategy:
    - shared_common_scope: "shared/commonMain実装範囲"
    - expect_actual_interfaces: "expect/actual抽象化設計"
    - platform_specific_scope: "プラットフォーム固有実装範囲"
    
  scalability_design:
    - modular_architecture: "モジュラーアーキテクチャ設計"
    - dependency_management: "依存関係管理戦略"
    - extensibility_planning: "拡張性・将来対応計画"

deliverables:
  - docs/architecture/system_design.md
  - architecture_decision_records/ (ADRs)
  - component_diagram.md
  - multiplatform_strategy.md
```

#### 3.2 データアーキテクチャ・API設計
```yaml
activity: data_api_architecture
duration: 1-2週間
owner: backend-security-architect
collaboration: architecture-strategist

approach:
  data_modeling:
    - domain_model_design: "ドメインモデル設計"
    - data_flow_architecture: "データフローアーキテクチャ"
    - persistence_strategy: "永続化戦略・データベース設計"
    
  api_architecture:
    - rest_api_design: "REST API設計・仕様"
    - authentication_architecture: "認証・認可アーキテクチャ"
    - error_handling_strategy: "エラーハンドリング戦略"
    
  security_architecture:
    - security_by_design: "セキュリティバイデザイン原則"
    - data_protection_strategy: "データ保護・プライバシー戦略"
    - compliance_architecture: "コンプライアンス対応設計"

deliverables:
  - docs/architecture/data_architecture.md
  - api_specifications/
  - security_architecture.md
  - database_schema_design.md
```

#### 3.3 テストアーキテクチャ設計
```yaml
activity: test_architecture_design
duration: 1週間
owner: qa-test-strategist
collaboration: architecture-strategist

approach:
  test_strategy:
    - test_pyramid_design: "テストピラミッド戦略"
    - multiplatform_test_approach: "マルチプラットフォームテスト手法"
    - automation_strategy: "テスト自動化戦略"
    
  quality_assurance:
    - quality_gates_definition: "品質ゲート定義"
    - performance_test_strategy: "パフォーマンステスト戦略"
    - security_test_planning: "セキュリティテスト計画"
    
  ci_cd_integration:
    - continuous_integration: "継続的統合戦略"
    - deployment_pipeline: "デプロイメントパイプライン設計"
    - monitoring_strategy: "監視・アラート戦略"

deliverables:
  - docs/testing/test_architecture.md
  - quality_assurance_strategy.md
  - ci_cd_pipeline_design.md
  - performance_benchmarks.md
```

#### 3.4 アーキテクチャ統合・検証
```yaml
activity: architecture_integration_validation
duration: 1週間
owner: multiplatform-dev-orchestrator
collaboration: all_architecture_agents

approach:
  integration_verification:
    - component_integration_check: "コンポーネント統合整合性確認"
    - cross_platform_compatibility: "クロスプラットフォーム互換性確認"
    - performance_architecture_validation: "パフォーマンス アーキテクチャ検証"
    
  implementation_planning:
    - development_roadmap: "開発ロードマップ策定"
    - technology_stack_finalization: "技術スタック最終決定"
    - risk_mitigation_planning: "リスク緩和計画策定"
    
  architecture_review:
    - peer_review_process: "ピアレビュープロセス"
    - external_review_coordination: "外部レビュー調整"
    - approval_documentation: "承認プロセス・文書化"

deliverables:
  - integrated_architecture_specification.md
  - implementation_roadmap.md
  - architecture_review_results.md
  - technology_stack_final.md
```

### Phase 3 品質ゲート
```yaml
quality_gate_criteria:
  architecture_quality:
    - "スケーラブル・保守可能なアーキテクチャ"
    - "Kotlin Multiplatform特性活用"
    - "セキュリティ・コンプライアンス対応"
    
  technical_coherence:
    - "技術スタック整合性確認"
    - "パフォーマンス要件満足"
    - "テストability・品質保証設計"
    
  implementation_readiness:
    - "実装可能性・工数妥当性確認"
    - "リスク緩和策準備"
    - "チーム理解・技術準備完了"

gate_reviewers:
  - architecture-strategist
  - backend-security-architect
  - multiplatform-dev-orchestrator
  - external_architect (optional)

approval_criteria: "アーキテクチャレビュー合格 かつ 実装準備完了"
```

## Phase 4: 実装・開発

### 目的
設計されたアーキテクチャに基づき、高品質なMultiplatformアプリケーションを実装する

### 参加Agent
- **Primary**: frontend-generalist-dev, backend-security-architect
- **Secondary**: multiplatform-dev-orchestrator
- **Support**: design-system-ui-architect, qa-test-strategist

### 主要活動

#### 4.1 基盤実装
```yaml
activity: foundation_implementation
duration: 2-3週間
parallel_execution: true

backend_foundation:
  owner: backend-security-architect
  scope:
    - server_infrastructure: "Ktor サーバー基盤構築"
    - database_implementation: "データベーススキーマ・Repository実装"
    - authentication_system: "認証・認可システム実装"
    - api_endpoints_core: "コアAPI エンドポイント実装"
  deliverables:
    - server/src/main/kotlin/
    - api_documentation/
    - database_migrations/
    - authentication_tests/

shared_foundation:
  owner: architecture-strategist (handoff to frontend-generalist-dev)
  scope:
    - shared_data_models: "共通データモデル実装"
    - business_logic_core: "コアビジネスロジック実装"
    - expect_actual_interfaces: "プラットフォーム抽象化実装"
    - validation_rules: "バリデーション・ビジネスルール実装"
  deliverables:
    - shared/commonMain/kotlin/
    - shared/commonTest/kotlin/
    - platform_interfaces/
    - business_logic_tests/

design_system_implementation:
  owner: frontend-generalist-dev
  collaboration: design-system-ui-architect
  scope:
    - design_tokens_integration: "デザイントークン統合"
    - base_components: "基本UIコンポーネント実装"
    - theming_system: "テーマシステム実装"
    - responsive_layouts: "レスポンシブレイアウト基盤"
  deliverables:
    - composeApp/src/commonMain/kotlin/ui/
    - design_system_components/
    - theme_definitions/
    - ui_component_tests/

coordination:
  owner: multiplatform-dev-orchestrator
  activities:
    - daily_standup: "日次進捗・ブロッカー確認"
    - integration_monitoring: "基盤統合性確認"
    - quality_checkpoint: "品質基準準拠確認"
```

#### 4.2 プラットフォーム実装
```yaml
activity: platform_implementation
duration: 3-4週間
parallel_execution: true

android_implementation:
  owner: frontend-generalist-dev
  scope:
    - android_specific_ui: "Android固有UI実装"
    - material_design_integration: "Material Design 3統合"
    - android_platform_features: "Android固有機能実装"
    - permissions_handling: "権限管理実装"
  deliverables:
    - composeApp/src/androidMain/kotlin/
    - android_resources/
    - android_manifest_configuration/
    - android_specific_tests/

ios_implementation:
  owner: frontend-generalist-dev
  scope:
    - ios_specific_ui: "iOS固有UI実装"
    - ios_platform_features: "iOS固有機能実装"
    - ios_permissions_handling: "iOS権限管理実装"
    - app_store_compliance: "App Store準拠対応"
  deliverables:
    - composeApp/src/iosMain/kotlin/
    - iosApp/iosApp/ (Swift integration)
    - ios_specific_configuration/
    - ios_specific_tests/

web_implementation:
  owner: frontend-generalist-dev
  scope:
    - web_responsive_ui: "Webレスポンシブ UI実装"
    - pwa_features: "PWA機能実装"
    - web_accessibility: "Webアクセシビリティ実装"
    - browser_compatibility: "ブラウザ互換性対応"
  deliverables:
    - composeApp/src/wasmJsMain/kotlin/
    - web_resources/
    - pwa_configuration/
    - web_specific_tests/

desktop_implementation:
  owner: frontend-generalist-dev
  scope:
    - desktop_ui: "Desktop UI実装"
    - os_integration: "OS統合機能実装"
    - desktop_specific_features: "Desktop固有機能実装"
    - packaging_preparation: "パッケージング準備"
  deliverables:
    - composeApp/src/jvmMain/kotlin/
    - desktop_resources/
    - packaging_configuration/
    - desktop_specific_tests/

quality_monitoring:
  owner: qa-test-strategist
  activities:
    - platform_testing: "プラットフォーム別テスト実行"
    - integration_validation: "統合動作確認"
    - performance_monitoring: "パフォーマンス監視"
    - security_validation: "セキュリティ検証"
```

#### 4.3 機能統合・最適化
```yaml
activity: feature_integration_optimization
duration: 2週間
owner: multiplatform-dev-orchestrator
collaboration: frontend-generalist-dev, backend-security-architect

approach:
  cross_platform_integration:
    - api_integration_verification: "API統合動作確認"
    - data_flow_validation: "データフロー検証"
    - error_handling_integration: "エラーハンドリング統合"
    
  performance_optimization:
    - shared_code_optimization: "共通コード最適化"
    - platform_specific_tuning: "プラットフォーム固有チューニング"
    - memory_usage_optimization: "メモリ使用量最適化"
    
  user_experience_refinement:
    - interaction_polish: "インタラクション磨き上げ"
    - loading_state_optimization: "ローディング状態最適化"
    - accessibility_enhancement: "アクセシビリティ向上"

deliverables:
  - integration_test_results/
  - performance_optimization_report.md
  - user_experience_validation.md
  - optimization_recommendations.md
```

#### 4.4 実装品質確保
```yaml
activity: implementation_quality_assurance
duration: 1-2週間 (並行実行)
owner: qa-test-strategist
collaboration: all_implementation_agents

approach:
  code_quality_assurance:
    - code_review_process: "コードレビュープロセス実行"
    - static_analysis: "静的解析・品質チェック"
    - test_coverage_validation: "テストカバレッジ検証"
    
  functional_quality_testing:
    - feature_testing: "機能テスト実行"
    - user_acceptance_testing: "ユーザー受入テスト"
    - cross_platform_consistency: "クロスプラットフォーム一貫性確認"
    
  non_functional_quality_testing:
    - performance_testing: "パフォーマンステスト"
    - security_testing: "セキュリティテスト"
    - accessibility_testing: "アクセシビリティテスト"

deliverables:
  - code_quality_report.md
  - functional_test_results/
  - performance_test_results/
  - security_assessment_report.md
```

### Phase 4 品質ゲート
```yaml
quality_gate_criteria:
  implementation_completeness:
    - "全PBI機能実装完了"
    - "プラットフォーム間一貫性確認"
    - "デザインシステム準拠実装"
    
  quality_standards:
    - "コード品質基準満足"
    - "テストカバレッジ目標達成 (>80%)"
    - "パフォーマンス要件満足"
    
  integration_success:
    - "API統合動作確認"
    - "クロスプラットフォーム動作確認"
    - "エラーハンドリング適切実装"

gate_reviewers:
  - frontend-generalist-dev
  - backend-security-architect
  - qa-test-strategist
  - multiplatform-dev-orchestrator

approval_criteria: "全実装完了 かつ 品質基準満足 かつ 統合テスト合格"
```

## Phase 5: 統合・品質保証

### 目的
全プラットフォーム・機能の統合を完了し、リリース品質を確保する

### 参加Agent
- **Primary**: qa-test-strategist, multiplatform-dev-orchestrator
- **Secondary**: All implementation agents
- **Support**: strategic-project-manager

### 主要活動

#### 5.1 システム統合テスト
```yaml
activity: system_integration_testing
duration: 1-2週間
owner: qa-test-strategist
support: multiplatform-dev-orchestrator

test_categories:
  end_to_end_testing:
    scope: "ユーザージャーニー完全テスト"
    platforms: ["Android", "iOS", "Web", "Desktop"]
    scenarios: ["primary user flows", "edge cases", "error scenarios"]
    
  cross_platform_integration:
    scope: "プラットフォーム間データ同期・整合性"
    test_types: ["data consistency", "state synchronization", "offline/online sync"]
    
  api_integration_testing:
    scope: "フロントエンド・バックエンド統合"
    test_types: ["api contract validation", "error handling", "performance"]
    
  security_integration_testing:
    scope: "セキュリティ統合動作確認"
    test_types: ["authentication flow", "authorization", "data protection"]

deliverables:
  - integration_test_plan.md
  - test_execution_reports/
  - defect_tracking_reports/
  - platform_compatibility_matrix.md
```

#### 5.2 パフォーマンス・セキュリティ検証
```yaml
activity: performance_security_validation
duration: 1週間
owner: qa-test-strategist
collaboration: backend-security-architect

performance_testing:
  load_testing:
    metrics: ["response time", "throughput", "resource utilization"]
    targets: ["95th percentile < 200ms", ">1000 concurrent users"]
    platforms: ["Server API", "Client applications"]
    
  stress_testing:
    approach: "システム限界・回復力テスト"
    scenarios: ["peak load", "resource exhaustion", "network interruption"]
    
  mobile_performance:
    metrics: ["battery usage", "memory consumption", "startup time"]
    targets: ["<3s cold start", "<100MB memory", "minimal battery impact"]

security_testing:
  vulnerability_assessment:
    scope: "OWASP Top 10 検証"
    tools: ["automated scanning", "manual penetration testing"]
    
  data_protection_validation:
    scope: "個人情報・機密データ保護確認"
    compliance: ["GDPR", "CCPA", "relevant regulations"]
    
  authentication_security:
    scope: "認証・認可セキュリティ検証"
    test_types: ["session management", "token security", "access control"]

deliverables:
  - performance_test_report.md
  - security_assessment_report.md
  - vulnerability_report.md
  - compliance_validation_report.md
```

#### 5.3 ユーザー受入テスト調整
```yaml
activity: user_acceptance_testing_coordination
duration: 1-2週間
owner: qa-test-strategist
collaboration: product-owner-pbi-manager, ux-persona-journey-designer

uat_planning:
  test_scenario_development:
    basis: "user journey maps", "personas", "acceptance criteria"
    coverage: "primary workflows", "edge cases", "error recovery"
    
  user_recruitment:
    criteria: "representative user profiles"
    size: "5-10 users per primary persona"
    compensation: "appropriate user incentives"
    
  test_environment_preparation:
    setup: "production-like environment"
    data: "realistic test data sets"
    monitoring: "user behavior tracking"

uat_execution:
  moderated_testing:
    approach: "guided task completion"
    observation: "user behavior, pain points, confusion"
    feedback_collection: "structured questionnaire, interview"
    
  unmoderated_testing:
    approach: "independent user exploration"
    tracking: "analytics, heat maps, error logs"
    feedback_mechanism: "in-app feedback, surveys"

deliverables:
  - uat_test_plan.md
  - user_testing_results/
  - usability_report.md
  - user_feedback_analysis.md
```

#### 5.4 リリース準備・最終検証
```yaml
activity: release_preparation_final_validation
duration: 1週間
owner: multiplatform-dev-orchestrator
collaboration: strategic-project-manager, qa-test-strategist

release_readiness_check:
  deployment_validation:
    environments: ["staging", "pre-production", "production"]
    verification: ["build processes", "deployment automation", "rollback procedures"]
    
  operational_readiness:
    monitoring: ["application monitoring", "error tracking", "performance dashboards"]
    support: ["documentation", "troubleshooting guides", "escalation procedures"]
    
  compliance_final_check:
    legal: ["license compliance", "terms of service", "privacy policy"]
    security: ["final security scan", "certificate validation"]
    platform: ["app store requirements", "web standards compliance"]

go_no_go_decision:
  criteria_evaluation:
    quality: "all quality gates passed"
    performance: "performance targets met"
    security: "security requirements satisfied"
    user_acceptance: "uat feedback positive"
    
  risk_assessment:
    technical_risks: "identified and mitigated"
    business_risks: "acceptable risk level"
    operational_risks: "support readiness confirmed"
    
  stakeholder_approval:
    approvers: ["product owner", "technical lead", "qa lead", "business stakeholder"]
    criteria: "unanimous approval for release"

deliverables:
  - release_readiness_report.md
  - go_no_go_decision_log.md
  - deployment_checklist.md
  - support_handover_documentation/
```

### Phase 5 品質ゲート
```yaml
quality_gate_criteria:
  integration_quality:
    - "全システム統合テスト合格"
    - "クロスプラットフォーム一貫性確認"
    - "パフォーマンス・セキュリティ要件満足"
    
  user_acceptance:
    - "ユーザー受入テスト合格"
    - "ユーザビリティ要件満足"
    - "アクセシビリティ基準準拠"
    
  operational_readiness:
    - "本番デプロイメント準備完了"
    - "監視・サポート体制構築"
    - "ロールバック手順検証済み"

gate_reviewers:
  - qa-test-strategist
  - multiplatform-dev-orchestrator
  - strategic-project-manager
  - key_stakeholders

approval_criteria: "全品質ゲート合格 かつ Go/No-Go承認"
```

## Phase 6: リリース・デプロイ

### 目的
安全で確実なプロダクションリリースを実行し、ユーザーに価値を提供する

### 参加Agent
- **Primary**: strategic-project-manager, multiplatform-dev-orchestrator
- **Secondary**: backend-security-architect, frontend-generalist-dev
- **Support**: qa-test-strategist

### 主要活動

#### 6.1 段階的リリース実行
```yaml
activity: phased_release_execution
duration: 1-2週間
owner: strategic-project-manager
coordination: multiplatform-dev-orchestrator

release_strategy:
  phased_rollout:
    phase_1_limited:
      scope: "内部ユーザー・ベータテスター"
      percentage: "5% of target audience"
      duration: "2-3日"
      monitoring: "intensive monitoring and feedback collection"
      
    phase_2_gradual:
      scope: "拡張ユーザーグループ"
      percentage: "25% of target audience"
      duration: "3-5日"
      monitoring: "performance metrics, user feedback"
      
    phase_3_full:
      scope: "全ターゲットユーザー"
      percentage: "100% of target audience"
      duration: "ongoing"
      monitoring: "standard operational monitoring"

platform_release_coordination:
  server_deployment:
    owner: backend-security-architect
    approach: "Blue-Green deployment"
    validation: "health checks, smoke tests"
    rollback: "automated rollback triggers"
    
  mobile_app_release:
    android: "Google Play Console gradual rollout"
    ios: "App Store Connect phased release"
    testing: "production testing on both platforms"
    
  web_deployment:
    approach: "CDN-based progressive deployment"
    validation: "A/B testing framework"
    fallback: "feature flag rollback"
    
  desktop_distribution:
    channels: "official website, package managers"
    validation: "cross-OS compatibility testing"
    updates: "auto-update mechanism testing"

deliverables:
  - release_execution_plan.md
  - deployment_logs/
  - release_monitoring_reports/
  - user_onboarding_metrics.md
```

#### 6.2 リリース監視・サポート
```yaml
activity: release_monitoring_support
duration: 2-4週間 (ongoing)
owner: multiplatform-dev-orchestrator
support: strategic-project-manager

monitoring_activities:
  technical_monitoring:
    metrics: ["error rates", "response times", "resource usage", "crash rates"]
    alerts: ["critical error thresholds", "performance degradation", "system outages"]
    dashboards: ["real-time monitoring", "historical trends", "platform-specific metrics"]
    
  user_experience_monitoring:
    analytics: ["user engagement", "feature adoption", "user flows"]
    feedback: ["app store reviews", "in-app feedback", "support tickets"]
    usability: ["task completion rates", "user journey analysis"]
    
  business_metrics_monitoring:
    kpis: ["user acquisition", "retention rates", "conversion metrics"]
    success_metrics: "predefined success criteria tracking"
    roi_tracking: "return on investment measurement"

support_activities:
  user_support:
    channels: ["in-app help", "documentation", "customer support"]
    escalation: "technical issue escalation procedures"
    knowledge_base: "FAQ and troubleshooting guides"
    
  technical_support:
    incident_response: "24/7 critical incident response"
    bug_triage: "issue prioritization and assignment"
    hotfix_deployment: "rapid fix deployment procedures"
    
  stakeholder_communication:
    reporting: "regular status reports to stakeholders"
    success_celebration: "achievement recognition and communication"
    lessons_learned: "post-launch retrospective planning"

deliverables:
  - monitoring_dashboard_setup/
  - incident_response_logs/
  - user_support_metrics.md
  - post_launch_status_reports/
```

#### 6.3 ユーザーオンボーディング支援
```yaml
activity: user_onboarding_support
duration: 4-6週間
owner: product-owner-pbi-manager
collaboration: ux-persona-journey-designer

onboarding_execution:
  user_communication:
    announcement: "product launch communication strategy"
    channels: ["email", "social media", "website", "press release"]
    messaging: "value proposition, key features, getting started"
    
  guided_onboarding:
    in_app_tutorials: "interactive feature introduction"
    quick_start_guides: "platform-specific getting started guides"
    video_tutorials: "key feature demonstration videos"
    
  community_building:
    user_forums: "community support and discussion forums"
    feedback_channels: "structured feedback collection"
    beta_community: "ongoing beta user engagement"

success_measurement:
  adoption_metrics:
    acquisition: "new user registration/download rates"
    activation: "feature adoption and engagement rates"
    retention: "user retention over time"
    
  satisfaction_metrics:
    nps_surveys: "Net Promoter Score tracking"
    user_reviews: "app store and web review analysis"
    support_satisfaction: "support interaction satisfaction"

deliverables:
  - user_communication_campaign/
  - onboarding_materials/
  - community_engagement_strategy.md
  - user_adoption_analysis.md
```

## Phase 7: 運用・保守

### 目的
継続的な価値提供とシステム改善を通じて、長期的な成功を実現する

### 参加Agent
- **Primary**: strategic-project-manager, multiplatform-dev-orchestrator
- **Secondary**: All agents (as needed)
- **Support**: qa-test-strategist, backend-security-architect

### 継続的活動

#### 7.1 継続的監視・改善
```yaml
activity: continuous_monitoring_improvement
frequency: ongoing
owner: multiplatform-dev-orchestrator

monitoring_processes:
  system_health_monitoring:
    technical_metrics: "performance, reliability, security"
    business_metrics: "user engagement, conversion, satisfaction"
    operational_metrics: "support load, incident frequency"
    
  trend_analysis:
    performance_trends: "system performance over time"
    usage_patterns: "user behavior and feature usage"
    issue_patterns: "recurring problems and root causes"
    
  proactive_optimization:
    performance_tuning: "ongoing system optimization"
    user_experience_enhancement: "UX improvement based on data"
    cost_optimization: "infrastructure and operational cost management"

improvement_cycles:
  monthly_reviews:
    metrics_review: "key performance indicator analysis"
    user_feedback_analysis: "support tickets, reviews, surveys"
    technical_debt_assessment: "code quality and maintenance needs"
    
  quarterly_planning:
    feature_roadmap_updates: "next quarter development priorities"
    technology_updates: "platform updates, security patches"
    capacity_planning: "resource and infrastructure scaling"
    
  annual_strategic_review:
    product_strategy_alignment: "business strategy alignment check"
    technology_strategy_update: "technology stack evolution planning"
    team_capability_development: "skill development and training"
```

#### 7.2 継続的デプロイメント・リリース
```yaml
activity: continuous_deployment_releases
frequency: bi-weekly to monthly
coordination: multiplatform-dev-orchestrator
execution: relevant agents based on changes

release_types:
  hotfixes:
    trigger: "critical bugs, security vulnerabilities"
    timeline: "within 24-48 hours"
    process: "expedited testing and deployment"
    coordination: "incident response team activation"
    
  minor_releases:
    frequency: "bi-weekly"
    scope: "bug fixes, small enhancements"
    process: "standard testing and gradual rollout"
    coordination: "regular development team process"
    
  major_releases:
    frequency: "quarterly"
    scope: "new features, significant improvements"
    process: "full development cycle with user testing"
    coordination: "multi-agent collaboration as per main process"

deployment_automation:
  ci_cd_pipeline_maintenance:
    monitoring: "pipeline reliability and performance"
    updates: "tooling updates and security patches"
    optimization: "build time and deployment speed improvements"
    
  quality_gates_refinement:
    criteria_updates: "quality criteria based on lessons learned"
    automation_enhancement: "increased automation coverage"
    feedback_integration: "user and stakeholder feedback integration"
```

#### 7.3 エボリューション計画・実行
```yaml
activity: product_evolution_planning
frequency: quarterly planning, ongoing execution
owner: strategic-project-manager
collaboration: multiplatform-dev-orchestrator, product-owner-pbi-manager

evolution_areas:
  feature_evolution:
    user_feedback_integration: "feature requests and improvements"
    market_opportunity_analysis: "new market needs and opportunities"
    competitive_response: "competitive landscape changes"
    
  technology_evolution:
    platform_updates: "Kotlin Multiplatform updates adoption"
    performance_improvements: "system optimization opportunities"
    security_enhancements: "security posture improvements"
    
  scale_evolution:
    user_base_growth: "scaling for increased user base"
    geographic_expansion: "new market/region support"
    integration_opportunities: "new system integrations"

planning_process:
  quarterly_roadmap_planning:
    stakeholder_input: "business and user requirement gathering"
    technical_assessment: "technology evolution assessment"
    resource_planning: "team capacity and skill planning"
    
  agile_iteration_planning:
    sprint_planning: "2-week iteration planning"
    backlog_refinement: "ongoing backlog maintenance"
    retrospective_improvements: "process improvement integration"
```

## 日常運用プロセス

### デイリー・ウィークリー活動

#### Daily Standup (開発フェーズ中)
```yaml
frequency: daily (during active development)
duration: 15-30 minutes
facilitator: multiplatform-dev-orchestrator

format:
  agent_updates:
    - completed_yesterday: "前日完了タスク"
    - planned_today: "本日予定タスク"
    - blockers_issues: "ブロッカー・課題"
    
  coordination_items:
    - handoff_status: "agent間ハンドオフ状況"
    - integration_points: "統合ポイント調整"
    - quality_concerns: "品質・リスク事項"
    
  decision_items:
    - immediate_decisions: "即座に必要な意思決定"
    - escalation_needs: "上位エスカレーション必要事項"
```

#### Weekly Progress Review
```yaml
frequency: weekly
duration: 60-90 minutes
facilitator: strategic-project-manager
participants: all_active_agents + key_stakeholders

agenda:
  progress_assessment:
    - milestone_progress: "マイルストーン進捗確認"
    - quality_metrics: "品質指標レビュー"
    - risk_status: "リスク状況・緩和策"
    
  coordination_review:
    - agent_performance: "agent パフォーマンス・効率性"
    - process_improvements: "プロセス改善提案"
    - resource_adjustments: "リソース調整必要性"
    
  planning_updates:
    - next_week_priorities: "来週優先事項"
    - dependency_resolution: "依存関係解決計画"
    - stakeholder_communication: "ステークホルダー報告事項"
```

### 課題・エスカレーション管理

#### 課題分類・対応プロセス
```yaml
issue_categories:
  technical_issues:
    examples: ["implementation blockers", "integration failures", "performance problems"]
    first_response: "relevant technical agent"
    escalation_trigger: "24-48 hours without resolution"
    escalation_to: "multiplatform-dev-orchestrator"
    
  process_issues:
    examples: ["agent coordination problems", "quality gate failures", "timeline conflicts"]
    first_response: "multiplatform-dev-orchestrator"
    escalation_trigger: "immediate if critical, 24 hours if major"
    escalation_to: "strategic-project-manager"
    
  business_issues:
    examples: ["requirement changes", "stakeholder conflicts", "scope adjustments"]
    first_response: "product-owner-pbi-manager"
    escalation_trigger: "immediate for scope changes"
    escalation_to: "strategic-project-manager"

escalation_procedures:
  level_1_agent_resolution:
    timeframe: "immediate to 24 hours"
    approach: "agent specialization and expertise"
    documentation: "issue tracking and resolution notes"
    
  level_2_orchestrator_coordination:
    timeframe: "24-48 hours"
    approach: "multi-agent coordination and resource allocation"
    documentation: "coordination actions and decisions"
    
  level_3_strategic_management:
    timeframe: "48-72 hours or immediate for critical"
    approach: "strategic decision making and external resource allocation"
    documentation: "strategic decisions and stakeholder communication"
```

### 品質保証・継続改善

#### 継続的品質監視
```yaml
quality_monitoring:
  automated_quality_checks:
    frequency: "continuous (CI/CD pipeline)"
    metrics: ["code quality", "test coverage", "build success rates"]
    alerts: "quality threshold violations"
    
  manual_quality_reviews:
    frequency: "weekly during development, monthly during maintenance"
    scope: ["code review quality", "design compliance", "user experience"]
    reviewers: "peer agents and qa-test-strategist"
    
  user_feedback_integration:
    frequency: "ongoing"
    sources: ["user support", "app store reviews", "usage analytics"]
    analysis: "trend identification and improvement opportunities"

improvement_processes:
  retrospective_sessions:
    frequency: "end of each phase, monthly during maintenance"
    participants: "all involved agents + multiplatform-dev-orchestrator"
    focus: ["process efficiency", "collaboration effectiveness", "quality outcomes"]
    
  process_optimization:
    trigger: "retrospective insights, performance metrics, external feedback"
    approach: "data-driven process improvements"
    implementation: "gradual rollout with measurement"
    
  knowledge_sharing:
    documentation: "lessons learned, best practices, anti-patterns"
    training: "skill development and cross-agent knowledge sharing"
    community: "external community engagement and contribution"
```

---

## まとめ

このMulti-Agent開発プロセスにより、Kotlin Multiplatformプロジェクトにおける複雑な開発を体系的・効率的に管理し、高品質な成果物を継続的に提供することが可能になります。

各フェーズの明確な定義、agent間の効果的な連携、継続的な品質保証により、プロジェクトの成功確率を最大化し、チーム全体の生産性を向上させます。