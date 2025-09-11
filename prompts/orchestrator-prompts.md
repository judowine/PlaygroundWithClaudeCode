# Orchestrator Agent Prompts

## 概要
`multiplatform-dev-orchestrator`が使用する標準プロンプト集です。各種分析・判断・指示において一貫性のある高品質な結果を得るための構造化プロンプトを定義します。

## 基本プロンプト構造

### テンプレート形式
```
# Context
[実行する作業の背景・目的]

# Input
[分析対象・入力データの詳細]

# Task
[具体的に実行すべきタスク]

# Output Format
[期待する出力形式・構造]

# Constraints
[制約条件・注意事項]

# Success Criteria
[成功基準・品質要件]
```

## 1. 要求分析プロンプト

### PBI分析プロンプト
```
# Context
Kotlin Multiplatformプロジェクト（Android/iOS/Web/Desktop/Server対応）において、新しいPBI（Product Backlog Item）の技術実現性と実装戦略を分析します。

# Input
PBI仕様書:
- PBI ID: {pbi_id}
- タイトル: {title}
- ユーザーストーリー: {user_story}
- 受け入れ条件: {acceptance_criteria}
- ビジネス価値: {business_value}
- 制約条件: {constraints}

既存システム情報:
- 現在のアーキテクチャ: {current_architecture}
- 技術スタック: {tech_stack}
- 既存機能: {existing_features}

# Task
以下の観点でPBIを分析し、実装戦略を策定してください:

1. **ビジネス価値分析**
   - ユーザーへの価値提供内容
   - ROI・投資対効果
   - 戦略的重要度評価

2. **技術実現性評価**
   - 現在の技術スタックでの実現可能性
   - 新技術・ライブラリの必要性
   - 技術的リスク・課題

3. **プラットフォーム影響度分析**
   - 各プラットフォーム（Android/iOS/Web/Desktop/Server）への影響
   - 共通実装 vs プラットフォーム固有実装の比率
   - expect/actual抽象化が必要な部分

4. **実装複雑度評価**
   - 全体的な実装複雑度（Low/Medium/High/Very High）
   - プラットフォーム別複雑度
   - 主な複雑度要因

# Output Format
```yaml
pbi_analysis:
  id: "{pbi_id}"
  title: "{title}"
  
  business_value:
    user_impact: "High/Medium/Low"
    roi_potential: "High/Medium/Low"
    strategic_importance: "High/Medium/Low"
    priority_recommendation: "High/Medium/Low"
  
  technical_feasibility:
    overall_feasibility: "High/Medium/Low"
    required_technologies: ["tech1", "tech2"]
    technical_risks: ["risk1", "risk2"]
    risk_level: "High/Medium/Low"
  
  platform_impact:
    android:
      impact_level: "None/Minimal/Low/Medium/High/Very High"
      specific_requirements: "description"
    ios:
      impact_level: "None/Minimal/Low/Medium/High/Very High"
      specific_requirements: "description"
    web:
      impact_level: "None/Minimal/Low/Medium/High/Very High"
      specific_requirements: "description"
    desktop:
      impact_level: "None/Minimal/Low/Medium/High/Very High"
      specific_requirements: "description"
    server:
      impact_level: "None/Minimal/Low/Medium/High/Very High"
      specific_requirements: "description"
  
  implementation_strategy:
    overall_complexity: "Low/Medium/High/Very High"
    estimated_story_points: number
    estimated_duration_weeks: number
    
    layer_distribution:
      shared_common_percent: number
      expect_actual_percent: number
      platform_specific_percent: number
    
    recommended_approach:
      implementation_order: ["phase1", "phase2", "phase3"]
      critical_dependencies: ["dep1", "dep2"]
      parallel_execution_opportunities: ["area1", "area2"]
  
  recommendations:
    immediate_actions: ["action1", "action2"]
    preparation_needed: ["prep1", "prep2"]
    success_factors: ["factor1", "factor2"]
```

# Constraints
- 現実的な工数見積もりを行う
- Kotlin Multiplatformの特性を活用した実装戦略を提案
- 既存アーキテクチャとの整合性を考慮
- リスクを過小評価しない

# Success Criteria
- 技術的実現可能性が明確に評価されている
- プラットフォーム影響度が具体的に分析されている
- 実装戦略が論理的で実行可能である
- リスク・制約が適切に識別されている
```

### 技術的実現性評価プロンプト
```
# Context
Kotlin Multiplatformプロジェクトにおいて、特定の技術要件・機能要求の実現可能性を詳細に評価し、実装アプローチを決定します。

# Input
技術要件:
- 機能概要: {feature_description}
- 技術要求: {technical_requirements}
- 性能要件: {performance_requirements}
- セキュリティ要件: {security_requirements}

現在の技術環境:
- Kotlin Multiplatform version: {kmp_version}
- Compose Multiplatform version: {compose_version}
- 使用中のライブラリ: {current_libraries}
- 対象プラットフォーム: {target_platforms}

# Task
技術要件の実現可能性を以下の観点で評価してください:

1. **Kotlin Multiplatform適合性**
   - KMPで実現可能な範囲
   - expect/actualが必要な部分
   - プラットフォーム固有実装が必要な部分

2. **ライブラリ・技術選択**
   - 使用可能なKMP対応ライブラリ
   - プラットフォーム固有ライブラリの必要性
   - 新規ライブラリ導入の必要性

3. **アーキテクチャ影響**
   - 既存アーキテクチャへの影響
   - 必要な設計変更・拡張
   - パフォーマンスへの影響

4. **実装リスク評価**
   - 技術的リスク
   - スケジュールリスク
   - 品質リスク

# Output Format
```yaml
technical_feasibility:
  feature: "{feature_description}"
  
  kmp_compatibility:
    shared_implementation_scope: "percentage and description"
    expect_actual_requirements: ["requirement1", "requirement2"]
    platform_specific_needs: ["need1", "need2"]
    overall_kmp_fitness: "Excellent/Good/Fair/Poor"
  
  technology_assessment:
    required_libraries:
      - name: "library_name"
        kmp_support: "Full/Partial/None"
        alternatives: ["alt1", "alt2"]
        risk_level: "Low/Medium/High"
    
    new_dependencies:
      - name: "new_library"
        justification: "reason for need"
        evaluation_needed: true/false
        license_compatibility: "Compatible/Review Needed"
  
  architecture_impact:
    current_architecture_changes: ["change1", "change2"]
    new_components_needed: ["component1", "component2"]
    performance_implications: "description"
    scalability_considerations: "description"
  
  risk_assessment:
    technical_risks:
      - risk: "description"
        probability: "High/Medium/Low"
        impact: "High/Medium/Low"
        mitigation: "mitigation strategy"
    
    schedule_risks:
      - risk: "description"
        probability: "High/Medium/Low"
        impact: "High/Medium/Low"
        mitigation: "mitigation strategy"
    
    quality_risks:
      - risk: "description"
        probability: "High/Medium/Low"
        impact: "High/Medium/Low"
        mitigation: "mitigation strategy"
  
  recommendation:
    feasibility_level: "Highly Feasible/Feasible/Challenging/Not Recommended"
    confidence_level: "High/Medium/Low"
    key_success_factors: ["factor1", "factor2"]
    critical_decisions_needed: ["decision1", "decision2"]
```

# Constraints
- 最新のKotlin Multiplatform機能・制約を考慮
- 実装経験に基づく現実的な評価
- リスクを適切に識別・評価
- 代替案・回避策を検討

# Success Criteria
- 技術的実現可能性が客観的に評価されている
- リスクが具体的に識別され、緩和策が提示されている
- 実装に必要な技術・ライブラリが明確になっている
- 意思決定に必要な情報が提供されている
```

## 2. タスク分解プロンプト

### Epic分解プロンプト
```
# Context
承認されたPBIをKotlin Multiplatform開発に適した実装可能なEpicに分解し、各Epicに適切な専門agentを割り当てます。

# Input
PBI詳細:
- PBI ID: {pbi_id}
- 機能概要: {feature_overview}
- 受け入れ条件: {acceptance_criteria}
- 制約条件: {constraints}

プラットフォーム影響分析結果:
- プラットフォーム別影響度: {platform_impacts}
- 実装レイヤー分布: {layer_distribution}

利用可能Agent:
- strategic-project-manager
- product-owner-pbi-manager
- pbi-refinement-facilitator
- market-analyst
- ux-persona-journey-designer
- design-system-ui-architect
- interaction-prototyping-agent
- architecture-strategist
- frontend-generalist-dev
- backend-security-architect
- qa-test-strategist

# Task
PBIを以下の観点で分解してください:

1. **機能・技術ドメイン別分解**
   - ビジネスロジック・データ処理
   - UI/UXデザイン・ユーザーエクスペリエンス
   - アーキテクチャ・技術基盤
   - プラットフォーム統合・実装
   - 品質保証・テスト

2. **実装順序・依存関係**
   - 基盤となるEpic（他の前提条件）
   - 並行実装可能なEpic
   - 統合・完成に必要なEpic

3. **Agent割り当て**
   - 各Epicの主担当Agent
   - 連携・支援が必要なAgent
   - ハンドオフが必要な部分

# Output Format
```yaml
epic_decomposition:
  pbi_id: "{pbi_id}"
  total_story_points: number
  estimated_duration_weeks: number
  
  epics:
    - id: "EPIC-001"
      title: "Epic Title"
      description: "Epic detailed description"
      
      scope:
        functional_scope: "What functionality this epic covers"
        platform_scope: ["Android", "iOS", "Web", "Desktop", "Server"]
        technical_scope: "Technical components included"
      
      assignment:
        primary_agent: "agent-name"
        supporting_agents: ["agent1", "agent2"]
        coordination_needs: "Special coordination requirements"
      
      estimation:
        story_points: number
        duration_weeks: number
        complexity_level: "Low/Medium/High/Very High"
        confidence: "High/Medium/Low"
      
      dependencies:
        depends_on: ["EPIC-ID1", "EPIC-ID2"]
        blocks: ["EPIC-ID3", "EPIC-ID4"]
        external_dependencies: ["External dependency"]
      
      deliverables:
        - type: "deliverable_type"
          description: "deliverable description"
          acceptance_criteria: "how to verify completion"
      
      risks:
        - risk: "risk description"
          impact: "High/Medium/Low"
          probability: "High/Medium/Low"
          mitigation: "mitigation approach"
  
  implementation_plan:
    phases:
      - phase: 1
        name: "Foundation Phase"
        epics: ["EPIC-001", "EPIC-002"]
        duration_weeks: number
        parallel_execution: true/false
      
      - phase: 2
        name: "Implementation Phase"
        epics: ["EPIC-003", "EPIC-004"]
        duration_weeks: number
        parallel_execution: true/false
    
    critical_path: ["EPIC-001", "EPIC-003", "EPIC-005"]
    parallel_opportunities: [["EPIC-002", "EPIC-004"], ["EPIC-006", "EPIC-007"]]
  
  agent_workload:
    - agent: "agent-name"
      primary_epics: ["EPIC-001"]
      supporting_epics: ["EPIC-002", "EPIC-003"]
      estimated_workload_percent: number
      peak_period: "phase or time period"
```

# Constraints
- Epicは独立して価値を提供できる単位
- 各Epicは1-2スプリント(2-4週間)で完了可能
- Agent専門性とEpic内容の適合性を重視
- プラットフォーム間の整合性を維持

# Success Criteria
- 全PBI要求がEpicで網羅されている
- Epic間の依存関係が適切に整理されている
- 各AgentのEpic割り当てが専門性と適合している
- 実装可能性・見積もりが現実的である
```

### Feature分解プロンプト
```
# Context
承認されたEpicを具体的な実装可能なFeatureに分解し、開発者が着手できる詳細レベルまで細分化します。

# Input
Epic詳細:
- Epic ID: {epic_id}
- Epic概要: {epic_description}
- 技術スコープ: {technical_scope}
- 主担当Agent: {primary_agent}

プラットフォーム要件:
- 対象プラットフォーム: {target_platforms}
- 共通実装範囲: {shared_implementation}
- プラットフォーム固有要件: {platform_specific_requirements}

# Task
Epicを以下の基準でFeatureに分解してください:

1. **機能単位分解**
   - 単一の明確な機能を持つ
   - 独立してテスト可能
   - ユーザー価値を提供

2. **技術単位分解**
   - shared/commonMain実装
   - expect/actual抽象化実装
   - プラットフォーム固有実装

3. **実装順序考慮**
   - 依存関係のある順序
   - 段階的な価値提供
   - リスク軽減のための優先順位

# Output Format
```yaml
feature_decomposition:
  epic_id: "{epic_id}"
  epic_title: "{epic_title}"
  decomposition_date: "{date}"
  
  features:
    - id: "FEAT-001"
      title: "Feature Title"
      description: "Detailed feature description"
      
      implementation_layer:
        primary_layer: "shared_common/expect_actual/platform_specific"
        secondary_layers: ["layer1", "layer2"]
      
      platform_breakdown:
        shared_common:
          scope: "What will be implemented in shared/commonMain"
          story_points: number
          complexity: "Low/Medium/High"
        
        expect_actual:
          abstractions_needed: ["abstraction1", "abstraction2"]
          story_points: number
          complexity: "Low/Medium/High"
        
        platform_specific:
          android:
            requirements: "Android specific requirements"
            story_points: number
            complexity: "Low/Medium/High"
          ios:
            requirements: "iOS specific requirements"
            story_points: number
            complexity: "Low/Medium/High"
          web:
            requirements: "Web specific requirements"
            story_points: number
            complexity: "Low/Medium/High"
          desktop:
            requirements: "Desktop specific requirements"
            story_points: number
            complexity: "Low/Medium/High"
          server:
            requirements: "Server specific requirements"
            story_points: number
            complexity: "Low/Medium/High"
      
      acceptance_criteria:
        functional:
          - "Functional requirement 1"
          - "Functional requirement 2"
        non_functional:
          - "Performance requirement"
          - "Security requirement"
        platform_specific:
          - "Platform specific requirement 1"
          - "Platform specific requirement 2"
      
      dependencies:
        feature_dependencies: ["FEAT-ID1", "FEAT-ID2"]
        external_dependencies: ["External system", "Third party library"]
        blocking_features: ["FEAT-ID3", "FEAT-ID4"]
      
      testing_requirements:
        unit_tests:
          shared: "Unit test requirements for shared code"
          platform_specific: "Platform specific unit test requirements"
        integration_tests:
          internal: "Internal integration test requirements"
          external: "External integration test requirements"
        ui_tests:
          shared_behavior: "Common UI behavior tests"
          platform_ui: "Platform specific UI tests"
      
      definition_of_done:
        development:
          - "Code implementation complete"
          - "Unit tests written and passing"
          - "Code review completed"
        integration:
          - "Integration tests passing"
          - "Platform compatibility verified"
          - "Performance requirements met"
        documentation:
          - "Technical documentation updated"
          - "API documentation updated"
          - "User documentation updated"
      
      risks_and_concerns:
        technical_risks: ["Risk 1", "Risk 2"]
        integration_risks: ["Integration concern 1"]
        performance_concerns: ["Performance concern 1"]
  
  implementation_sequence:
    phase_1_foundation:
      features: ["FEAT-001", "FEAT-002"]
      rationale: "Why these features should be implemented first"
      
    phase_2_core:
      features: ["FEAT-003", "FEAT-004", "FEAT-005"]
      rationale: "Core functionality implementation"
      
    phase_3_enhancement:
      features: ["FEAT-006", "FEAT-007"]
      rationale: "Enhancement and optimization features"
  
  quality_gates:
    after_phase_1:
      criteria: ["Foundation stability", "Core interfaces defined"]
      verification: "How to verify phase 1 completion"
      
    after_phase_2:
      criteria: ["Core functionality working", "Basic integration complete"]
      verification: "How to verify phase 2 completion"
      
    final_completion:
      criteria: ["All acceptance criteria met", "Quality standards achieved"]
      verification: "Final completion verification approach"
```

# Constraints
- Featureは1-5日で実装完了可能なサイズ
- 各Featureは独立してテスト可能
- プラットフォーム間の整合性を維持
- 実装順序が論理的で依存関係を考慮

# Success Criteria
- Epic要求が完全にFeatureで網羅されている
- 各Featureが明確で実装可能である
- プラットフォーム別実装要件が具体的
- テスト・品質要件が明確に定義されている
```

## 3. Agent選定・調整プロンプト

### Agent選定プロンプト
```
# Context
分解されたEpic・Featureに対して、11の専門agentから最適な組み合わせを選定し、効率的な連携体制を構築します。

# Input
タスク詳細:
- タスクタイプ: {task_type}
- スコープ: {scope}
- 技術要件: {technical_requirements}
- 期限: {deadline}
- 複雑度: {complexity_level}

利用可能Agent詳細:
- strategic-project-manager: プロジェクト統制、リソース管理
- product-owner-pbi-manager: PBI管理、ステークホルダーヒアリング
- pbi-refinement-facilitator: PBI詳細化、バックログ管理
- market-analyst: 市場分析、競合調査
- ux-persona-journey-designer: ユーザーペルソナ、ユーザージャーニー設計
- design-system-ui-architect: UIデザインシステム、画面レイアウト
- interaction-prototyping-agent: インタラクション設計、プロトタイピング
- architecture-strategist: システムアーキテクチャ、技術戦略
- frontend-generalist-dev: フロントエンド総合開発
- backend-security-architect: バックエンド開発、セキュリティ設計
- qa-test-strategist: テスト戦略、品質保証計画

現在のAgent負荷:
{current_agent_workload}

# Task
以下の観点で最適なAgent組み合わせを選定してください:

1. **主担当Agent選定**
   - タスクの中核となるドメインとagent専門性のマッチング
   - Agent現在の負荷状況
   - 過去の同種タスク実績・成功率

2. **連携Agent選定**
   - クロスファンクショナルな協力が必要な領域
   - 品質保証・レビュー体制
   - 知識・スキル補完関係

3. **連携体制設計**
   - ハンドオフポイント・タイミング
   - 定期的な同期・調整プロセス
   - エスカレーション・課題解決体制

# Output Format
```yaml
agent_assignment:
  task_id: "{task_id}"
  task_title: "{task_title}"
  assignment_date: "{date}"
  
  primary_agent:
    agent_name: "selected-agent"
    assignment_rationale: "Why this agent is the best fit"
    responsibility_scope: "Specific responsibilities and deliverables"
    estimated_effort_percent: number
    
  supporting_agents:
    - agent_name: "supporting-agent-1"
      role: "Supporting role description"
      collaboration_type: "Consultation/Review/Joint Development"
      estimated_effort_percent: number
      key_contributions: ["contribution1", "contribution2"]
    
    - agent_name: "supporting-agent-2"
      role: "Supporting role description"
      collaboration_type: "Consultation/Review/Joint Development"
      estimated_effort_percent: number
      key_contributions: ["contribution1", "contribution2"]
  
  collaboration_plan:
    kick_off:
      participants: ["agent1", "agent2", "agent3"]
      agenda: "Kick-off meeting agenda"
      deliverables: "Initial deliverables and agreements"
      
    regular_sync:
      frequency: "Daily/Weekly/Bi-weekly"
      participants: "Core team or all agents"
      format: "Standup/Review meeting/Status report"
      
    handoff_points:
      - from_agent: "agent1"
        to_agent: "agent2"
        trigger: "Completion criteria"
        deliverables: ["deliverable1", "deliverable2"]
        quality_gate: "Quality verification process"
    
    decision_points:
      - decision: "Key decision to be made"
        participants: ["decision maker", "consultants"]
        criteria: "Decision making criteria"
        timeline: "When decision must be made"
  
  success_metrics:
    quality_metrics:
      - metric: "Quality measure"
        target: "Target value"
        measurement: "How to measure"
    
    efficiency_metrics:
      - metric: "Efficiency measure"
        target: "Target value"
        measurement: "How to measure"
    
    collaboration_metrics:
      - metric: "Collaboration measure"
        target: "Target value"
        measurement: "How to measure"
  
  risk_mitigation:
    agent_availability_risks:
      - risk: "Agent overload or unavailability"
        probability: "High/Medium/Low"
        impact: "High/Medium/Low"
        mitigation: "Backup plan or load balancing"
    
    collaboration_risks:
      - risk: "Communication or coordination issues"
        probability: "High/Medium/Low"
        impact: "High/Medium/Low"
        mitigation: "Communication protocol or escalation process"
    
    technical_risks:
      - risk: "Technical challenge beyond agent expertise"
        probability: "High/Medium/Low"
        impact: "High/Medium/Low"
        mitigation: "Additional expertise or external consultation"
```

# Constraints
- Agent負荷バランスを考慮（単一agentに過度の集中を避ける）
- Agent専門性を最大活用
- プロジェクト全体のスケジュール・品質に貢献
- 実際の協力・連携が可能な現実的な体制

# Success Criteria
- 選定されたAgentがタスク要件に最適に適合している
- 連携体制が具体的で実行可能である
- リスクが適切に識別され緩和策が用意されている
- 成功指標が測定可能で適切である
```

### Agent調整プロンプト
```
# Context
複数のAgentが関わるタスクにおいて、作業の重複・矛盾・遅延を防ぎ、効率的な連携を実現するための調整を行います。

# Input
現在の状況:
- 進行中のタスク: {current_tasks}
- 関係するAgent: {involved_agents}
- 課題・ブロッカー: {current_issues}
- スケジュール状況: {schedule_status}

調整が必要な事項:
- 作業重複: {work_overlaps}
- 依存関係の問題: {dependency_issues}
- リソース競合: {resource_conflicts}
- 品質・一貫性の問題: {quality_issues}

# Task
以下の観点で最適な調整案を作成してください:

1. **作業分担の最適化**
   - 重複作業の排除・統合
   - 各Agent最適な作業割り当て
   - 負荷バランスの調整

2. **依存関係・順序の調整**
   - ブロッカーの解消
   - クリティカルパスの最適化
   - 並行作業機会の拡大

3. **品質・一貫性確保**
   - 成果物の一貫性確保メカニズム
   - レビュー・承認プロセス
   - 統合ポイントでの品質確認

# Output Format
```yaml
coordination_plan:
  situation_analysis:
    current_issues: ["issue1", "issue2", "issue3"]
    root_causes: ["cause1", "cause2"]
    impact_assessment: "High/Medium/Low impact on project"
    urgency: "Immediate/This Week/Next Sprint"
  
  optimization_actions:
    work_redistribution:
      - agent: "agent-name"
        current_work: ["current task1", "current task2"]
        optimized_work: ["optimized task1", "optimized task2"]
        rationale: "Why this redistribution is better"
        effort_change: "+/- X hours or story points"
    
    dependency_resolution:
      - blocking_issue: "Description of blocking issue"
        affected_agents: ["agent1", "agent2"]
        resolution_approach: "How to resolve the blocking issue"
        timeline: "When resolution will be complete"
        owner: "Who is responsible for resolution"
    
    parallel_opportunities:
      - opportunity: "Description of parallel work opportunity"
        agents: ["agent1", "agent2"]
        coordination_mechanism: "How agents will coordinate"
        potential_time_saving: "X days or Y%"
  
  quality_assurance:
    consistency_mechanisms:
      - aspect: "What needs to be consistent"
        approach: "How consistency will be maintained"
        checkpoints: "When consistency will be verified"
        responsible_agent: "Who ensures consistency"
    
    review_process:
      - deliverable_type: "Type of deliverable"
        reviewers: ["reviewer1", "reviewer2"]
        review_criteria: "What will be reviewed"
        approval_process: "How approval will be granted"
    
    integration_points:
      - integration: "What needs to be integrated"
        participants: ["agent1", "agent2"]
        process: "How integration will be done"
        validation: "How integration will be validated"
  
  communication_enhancement:
    sync_meetings:
      - type: "Type of meeting (daily standup, weekly review, etc.)"
        participants: ["agent1", "agent2", "agent3"]
        frequency: "How often"
        agenda: "Standard agenda items"
        duration: "Expected duration"
    
    documentation:
      - document_type: "Type of document"
        owner: "Who maintains it"
        update_frequency: "How often it's updated"
        audience: "Who needs to read it"
    
    escalation_paths:
      - issue_type: "Type of issue"
        escalation_trigger: "When to escalate"
        escalation_to: "Who to escalate to"
        response_time: "Expected response time"
  
  success_metrics:
    efficiency_improvements:
      - metric: "Measurement of efficiency gain"
        baseline: "Current performance"
        target: "Target performance"
        measurement_method: "How to measure"
    
    quality_improvements:
      - metric: "Measurement of quality improvement"
        baseline: "Current quality level"
        target: "Target quality level"
        measurement_method: "How to measure"
    
    collaboration_improvements:
      - metric: "Measurement of collaboration effectiveness"
        baseline: "Current collaboration level"
        target: "Target collaboration level"
        measurement_method: "How to measure"
  
  implementation_timeline:
    immediate_actions:
      - action: "Action to be taken immediately"
        owner: "Who will do it"
        completion_date: "When it will be done"
    
    short_term_actions:
      - action: "Action to be taken this week"
        owner: "Who will do it"
        completion_date: "When it will be done"
    
    ongoing_improvements:
      - improvement: "Ongoing improvement process"
        owner: "Who will manage it"
        review_frequency: "How often it will be reviewed"
```

# Constraints
- 既存のコミットメント・スケジュールを尊重
- Agent能力・専門性を最大活用
- 最小限の変更で最大の効果を実現
- 実行可能で持続可能な改善

# Success Criteria
- 特定された問題が具体的に解決されている
- Agent間の連携が改善されている
- プロジェクト全体のパフォーマンスが向上している
- 提案された変更が実行可能である
```

## 4. 品質統制プロンプト

### 品質ゲート評価プロンプト
```
# Context
Kotlin Multiplatformプロジェクトの開発フェーズ完了時に、次フェーズ進行の可否を判定するため品質ゲート評価を実施します。

# Input
フェーズ情報:
- フェーズ名: {phase_name}
- 完了予定日: {completion_date}
- 関与したAgent: {involved_agents}

成果物:
- 技術成果物: {technical_deliverables}
- ドキュメント: {documentation}
- テスト結果: {test_results}

品質基準:
- 機能要件: {functional_requirements}
- 非機能要件: {non_functional_requirements}
- プラットフォーム要件: {platform_requirements}

# Task
以下の観点で品質ゲート評価を実施してください:

1. **機能品質評価**
   - 要件充足度
   - 機能の完全性・正確性
   - プラットフォーム間一貫性

2. **技術品質評価**
   - コード品質・保守性
   - アーキテクチャ適合性
   - パフォーマンス・セキュリティ

3. **プロセス品質評価**
   - Definition of Done準拠
   - ドキュメント完備状況
   - テストカバレッジ・品質

4. **リリース準備状況**
   - 統合・デプロイ準備
   - 運用準備
   - ユーザー受入準備

# Output Format
```yaml
quality_gate_assessment:
  phase: "{phase_name}"
  assessment_date: "{date}"
  assessor: "multiplatform-dev-orchestrator"
  
  functional_quality:
    requirements_coverage:
      total_requirements: number
      implemented_requirements: number
      coverage_percentage: number
      uncovered_requirements: ["req1", "req2"]
      
    feature_completeness:
      - feature: "feature name"
        implementation_status: "Complete/Partial/Not Started"
        quality_score: "A/B/C/D/F"
        issues: ["issue1", "issue2"]
        
    platform_consistency:
      - aspect: "UI/UX consistency"
        android_score: "A/B/C/D/F"
        ios_score: "A/B/C/D/F"
        web_score: "A/B/C/D/F"
        desktop_score: "A/B/C/D/F"
        server_score: "A/B/C/D/F"
        consistency_issues: ["issue1", "issue2"]
  
  technical_quality:
    code_quality:
      shared_common:
        maintainability_index: number
        cyclomatic_complexity: number
        test_coverage: number
        code_smells: number
        
      platform_specific:
        android:
          lint_issues: number
          test_coverage: number
          performance_score: "A/B/C/D/F"
        ios:
          warnings: number
          test_coverage: number
          performance_score: "A/B/C/D/F"
        web:
          bundle_size_kb: number
          lighthouse_score: number
          accessibility_score: "A/B/C/D/F"
        desktop:
          memory_usage_mb: number
          startup_time_ms: number
        server:
          response_time_ms: number
          throughput_rps: number
          error_rate_percent: number
    
    architecture_compliance:
      design_pattern_adherence: "Excellent/Good/Fair/Poor"
      dependency_management: "Excellent/Good/Fair/Poor"
      security_compliance: "Excellent/Good/Fair/Poor"
      scalability_readiness: "Excellent/Good/Fair/Poor"
  
  process_quality:
    definition_of_done:
      - criteria: "DoD criteria"
        status: "Met/Partially Met/Not Met"
        evidence: "Evidence of compliance"
        
    documentation:
      technical_documentation: "Complete/Partial/Missing"
      api_documentation: "Complete/Partial/Missing"
      user_documentation: "Complete/Partial/Missing"
      deployment_guide: "Complete/Partial/Missing"
      
    testing:
      unit_test_coverage: number
      integration_test_coverage: number
      e2e_test_coverage: number
      manual_testing_complete: true/false
      performance_testing_complete: true/false
  
  release_readiness:
    deployment_readiness:
      build_automation: "Ready/In Progress/Not Started"
      environment_setup: "Ready/In Progress/Not Started"
      rollback_plan: "Ready/In Progress/Not Started"
      monitoring_setup: "Ready/In Progress/Not Started"
      
    operational_readiness:
      support_documentation: "Ready/In Progress/Not Started"
      training_materials: "Ready/In Progress/Not Started"
      incident_response_plan: "Ready/In Progress/Not Started"
      
    user_acceptance:
      stakeholder_approval: "Approved/Pending/Not Started"
      user_testing_complete: true/false
      feedback_incorporated: true/false
  
  overall_assessment:
    gate_decision: "PASS/CONDITIONAL PASS/FAIL"
    confidence_level: "High/Medium/Low"
    key_strengths: ["strength1", "strength2"]
    critical_issues: ["issue1", "issue2"]
    
    conditions_for_pass:
      - condition: "What must be completed for pass"
        owner: "Who is responsible"
        deadline: "When it must be complete"
        
    recommendations:
      immediate_actions: ["action1", "action2"]
      next_phase_preparations: ["prep1", "prep2"]
      continuous_improvements: ["improvement1", "improvement2"]
```

# Constraints
- 客観的・定量的な評価基準を使用
- プラットフォーム間の公平な評価
- リスクベースの評価（高リスク領域に重点）
- ステークホルダー期待値との整合性

# Success Criteria
- 評価が客観的で根拠に基づいている
- 問題・リスクが具体的に識別されている
- 改善・対応策が実行可能である
- 次フェーズ進行の判断材料が提供されている
```

## 5. 統合・最適化プロンプト

### プラットフォーム統合プロンプト
```
# Context
各プラットフォームで個別実装された機能を統合し、一貫性のあるマルチプラットフォーム体験を実現します。

# Input
統合対象:
- 統合する機能: {feature_name}
- 各プラットフォーム実装状況: {platform_implementations}
- 共通仕様: {shared_specifications}
- プラットフォーム固有要件: {platform_specific_requirements}

品質要件:
- 一貫性要件: {consistency_requirements}
- パフォーマンス要件: {performance_requirements}
- ユーザビリティ要件: {usability_requirements}

# Task
以下の観点でプラットフォーム統合を実施してください:

1. **機能一貫性確保**
   - プラットフォーム間の動作統一
   - データ形式・API仕様の標準化
   - エラーハンドリングの統一

2. **ユーザーエクスペリエンス統一**
   - UI/UXパターンの一貫性
   - ナビゲーション・操作性の統一
   - レスポンス・フィードバックの統一

3. **技術統合最適化**
   - 共通ロジックの最大化
   - プラットフォーム固有最適化
   - 統合テスト・品質保証

# Output Format
```yaml
platform_integration:
  feature: "{feature_name}"
  integration_date: "{date}"
  
  consistency_analysis:
    functional_consistency:
      - aspect: "Function or behavior"
        android_implementation: "How it works on Android"
        ios_implementation: "How it works on iOS"
        web_implementation: "How it works on Web"
        desktop_implementation: "How it works on Desktop"
        server_implementation: "How it works on Server"
        consistency_level: "Identical/Similar/Different"
        action_required: "What needs to be done for consistency"
        
    data_consistency:
      - data_type: "Type of data"
        format_standards: "Standardized format"
        validation_rules: "Common validation rules"
        platform_variations: ["variation1", "variation2"]
        harmonization_plan: "How to harmonize differences"
        
    ui_consistency:
      - ui_element: "UI component or pattern"
        design_standard: "Agreed design standard"
        platform_adaptations: ["adaptation1", "adaptation2"]
        consistency_score: "A/B/C/D/F"
        improvement_actions: ["action1", "action2"]
  
  integration_plan:
    shared_code_optimization:
      current_shared_percentage: number
      target_shared_percentage: number
      optimization_opportunities:
        - opportunity: "What can be shared"
          effort_required: "Story points or time"
          benefits: "Benefits of sharing"
          risks: "Risks of sharing"
          
    expect_actual_refinement:
      current_abstractions: ["abstraction1", "abstraction2"]
      missing_abstractions: ["needed1", "needed2"]
      refinement_actions:
        - action: "Refinement action"
          platforms_affected: ["Android", "iOS"]
          implementation_effort: "Story points or time"
          
    platform_optimization:
      android:
        optimizations: ["optimization1", "optimization2"]
        performance_targets: "Target performance metrics"
        implementation_effort: "Story points or time"
      ios:
        optimizations: ["optimization1", "optimization2"]
        performance_targets: "Target performance metrics"
        implementation_effort: "Story points or time"
      web:
        optimizations: ["optimization1", "optimization2"]
        performance_targets: "Target performance metrics"
        implementation_effort: "Story points or time"
      desktop:
        optimizations: ["optimization1", "optimization2"]
        performance_targets: "Target performance metrics"
        implementation_effort: "Story points or time"
      server:
        optimizations: ["optimization1", "optimization2"]
        performance_targets: "Target performance metrics"
        implementation_effort: "Story points or time"
  
  testing_strategy:
    cross_platform_tests:
      - test_type: "Type of cross-platform test"
        test_scenarios: ["scenario1", "scenario2"]
        expected_results: "What should be consistent"
        automation_level: "Manual/Semi-automated/Fully automated"
        
    integration_tests:
      - integration_point: "Where platforms integrate"
        test_approach: "How integration will be tested"
        success_criteria: "How success will be measured"
        risk_mitigation: "How integration risks will be mitigated"
        
    performance_tests:
      - performance_aspect: "What performance will be tested"
        measurement_method: "How it will be measured"
        target_metrics: "Target performance numbers"
        platforms_tested: ["Android", "iOS", "Web"]
  
  quality_assurance:
    validation_checkpoints:
      - checkpoint: "Validation checkpoint"
        criteria: "What will be validated"
        method: "How validation will be done"
        responsible_party: "Who will do validation"
        
    user_acceptance:
      testing_approach: "How user acceptance will be tested"
      success_metrics: "How success will be measured"
      feedback_collection: "How feedback will be collected"
      iteration_plan: "How feedback will be incorporated"
  
  success_metrics:
    consistency_metrics:
      - metric: "Consistency measurement"
        measurement_method: "How it will be measured"
        target_value: "Target consistency level"
        current_baseline: "Current consistency level"
        
    performance_metrics:
      - metric: "Performance measurement"
        measurement_method: "How it will be measured"
        target_value: "Target performance"
        current_baseline: "Current performance"
        
    user_satisfaction:
      - metric: "User satisfaction measurement"
        measurement_method: "How it will be measured"
        target_value: "Target satisfaction level"
        current_baseline: "Current satisfaction level"
```

# Constraints
- プラットフォーム固有の利点を損なわない
- 既存ユーザーエクスペリエンスを劣化させない
- 技術的制約・プラットフォーム制限を考慮
- 実装・保守コストを最小化

# Success Criteria
- プラットフォーム間の一貫性が実現されている
- 各プラットフォームの強みが活用されている
- ユーザーエクスペリエンスが向上している
- 技術的品質・保守性が向上している
```

---

## まとめ

これらの構造化プロンプトにより、`multiplatform-dev-orchestrator`は一貫性のある高品質な分析・判断・指示を行うことができます。

各プロンプトは実際の開発状況に応じてカスタマイズし、継続的な改善により精度・効果を向上させることが重要です。