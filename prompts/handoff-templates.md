# Agent間ハンドオフテンプレート

## 概要
Kotlin Multiplatformプロジェクトにおける11の専門agentと`multiplatform-dev-orchestrator`間のハンドオフを標準化するテンプレート集です。効率的で確実な作業引き継ぎと品質保証を実現します。

## 基本ハンドオフ構造

### 標準ハンドオフパッケージ
```yaml
handoff_package:
  # メタデータ
  metadata:
    handoff_id: "HO-{YYYYMMDD}-{sequential}"
    created_date: "ISO8601 timestamp"
    from_agent: "sender agent name"
    to_agent: "receiver agent name"
    orchestrator_approval: true/false
    
  # タスク情報
  task_context:
    pbi_id: "related PBI identifier"
    epic_id: "related Epic identifier"
    feature_id: "related Feature identifier"
    phase: "development phase"
    priority: "High/Medium/Low"
    deadline: "target completion date"
    
  # 作業成果物
  deliverables:
    primary_outputs: []
    supporting_documents: []
    test_results: []
    quality_evidence: []
    
  # 要求事項
  requirements:
    functional_requirements: []
    technical_constraints: []
    platform_requirements: []
    quality_standards: []
    
  # 成功基準
  success_criteria:
    acceptance_conditions: []
    quality_gates: []
    performance_targets: []
    completion_indicators: []
    
  # 次ステップ
  next_actions:
    immediate_tasks: []
    follow_up_items: []
    decision_points: []
    escalation_triggers: []
```

## 1. プロジェクト管理系Agent間ハンドオフ

### Strategic Project Manager → Multiplatform Dev Orchestrator
```yaml
handoff_template: "spm_to_orchestrator"
scenario: "プロジェクト承認後の技術実装計画移管"

handoff_package:
  metadata:
    handoff_type: "project_initiation"
    approval_level: "executive_approved"
    
  deliverables:
    primary_outputs:
      - type: "project_charter"
        location: "docs/project/charter.md"
        version: "1.0"
        approval_status: "approved"
        
      - type: "resource_allocation_plan"
        location: "docs/project/resources.md"
        allocated_story_points: number
        team_capacity: "team members and skills"
        
      - type: "milestone_schedule"
        location: "docs/project/schedule.md"
        key_milestones: ["milestone1", "milestone2"]
        release_targets: ["release1", "release2"]
        
    supporting_documents:
      - "stakeholder_analysis.md"
      - "risk_register.md"
      - "communication_plan.md"
      
  requirements:
    business_objectives: ["objective1", "objective2"]
    success_metrics: ["metric1", "metric2"]
    budget_constraints: "budget limitations"
    timeline_constraints: "deadline requirements"
    
    platform_priorities:
      primary_platforms: ["Android", "iOS"]
      secondary_platforms: ["Web"]
      future_platforms: ["Desktop", "Server"]
      
  success_criteria:
    project_success:
      - "Technical implementation plan created"
      - "Agent assignments completed"
      - "Risk mitigation strategies defined"
      
    quality_standards:
      - "Architecture decisions documented"
      - "Technology choices justified"
      - "Implementation approach validated"
      
  next_actions:
    for_orchestrator:
      immediate_tasks:
        - "Conduct technical feasibility analysis"
        - "Create detailed implementation plan"
        - "Assign specialist agents to work streams"
        
      decision_points:
        - decision: "Technology stack finalization"
          deadline: "within 1 week"
          stakeholders: ["Architecture team", "Development team"]
          
    coordination_requirements:
      reporting_frequency: "weekly"
      escalation_triggers: ["major technical risks", "schedule deviations >20%"]
      approval_gates: ["architecture review", "technology selection"]
```

### Multiplatform Dev Orchestrator → Strategic Project Manager
```yaml
handoff_template: "orchestrator_to_spm"
scenario: "技術実装計画完了後のプロジェクト統制移管"

handoff_package:
  metadata:
    handoff_type: "implementation_planning_complete"
    technical_approval: "approved"
    
  deliverables:
    primary_outputs:
      - type: "technical_implementation_plan"
        location: "docs/technical/implementation_plan.md"
        epic_breakdown: "Epic-level task breakdown"
        agent_assignments: "Detailed agent responsibilities"
        
      - type: "platform_impact_analysis"
        location: "docs/technical/platform_analysis.md"
        effort_estimates: "Platform-specific effort estimates"
        risk_assessments: "Technical and schedule risks"
        
      - type: "agent_coordination_plan"
        location: "docs/agents/coordination_plan.md"
        collaboration_matrix: "Agent interaction patterns"
        handoff_schedule: "Planned handoff timeline"
        
    quality_evidence:
      - "architecture_review_approval.md"
      - "technology_selection_rationale.md"
      - "risk_mitigation_strategies.md"
      
  requirements:
    resource_needs:
      specialist_agents: ["agent1: X hours", "agent2: Y hours"]
      external_resources: ["cloud services", "third-party tools"]
      training_requirements: ["new technology training"]
      
    timeline_adjustments:
      original_estimate: "X weeks"
      refined_estimate: "Y weeks"
      schedule_impact: "impact description"
      mitigation_measures: ["measure1", "measure2"]
      
  success_criteria:
    implementation_readiness:
      - "All agents understand their responsibilities"
      - "Technical infrastructure is prepared"
      - "Quality gates are defined and understood"
      
    project_control:
      - "Progress tracking mechanisms in place"
      - "Risk monitoring procedures established"
      - "Escalation paths clearly defined"
      
  next_actions:
    for_spm:
      immediate_tasks:
        - "Approve refined project plan"
        - "Secure additional resources if needed"
        - "Communicate updates to stakeholders"
        
      ongoing_responsibilities:
        - "Monitor overall project progress"
        - "Manage resource allocation adjustments"
        - "Handle stakeholder communications"
        
    coordination_requirements:
      status_reports: "weekly technical progress reports"
      decision_escalation: "architectural changes, scope adjustments"
      resource_requests: "additional team members, tools, training"
```

## 2. プロダクト・要件系Agent間ハンドオフ

### Product Owner PBI Manager → PBI Refinement Facilitator
```yaml
handoff_template: "pbi_manager_to_refinement"
scenario: "ステークホルダーヒアリング完了後のPBI詳細化移管"

handoff_package:
  metadata:
    handoff_type: "requirements_to_refinement"
    stakeholder_approval: "confirmed"
    
  deliverables:
    primary_outputs:
      - type: "stakeholder_requirements"
        location: "docs/requirements/stakeholder_needs.md"
        interviewed_stakeholders: ["stakeholder1", "stakeholder2"]
        requirement_priorities: "MoSCoW analysis results"
        
      - type: "user_stories_draft"
        location: "docs/pbi/draft/user_stories.md"
        story_count: number
        epic_groupings: ["epic1", "epic2"]
        
      - type: "business_value_analysis"
        location: "docs/analysis/business_value.md"
        roi_projections: "return on investment analysis"
        user_impact_assessment: "expected user benefits"
        
    supporting_documents:
      - "stakeholder_interview_notes.md"
      - "competitive_analysis.md"
      - "market_research_findings.md"
      
  requirements:
    refinement_standards:
      story_format: "Given-When-Then format preferred"
      acceptance_criteria: "SMART criteria mandatory"
      platform_coverage: "all target platforms addressed"
      
    quality_expectations:
      story_clarity: "unambiguous and testable"
      business_value: "clearly articulated value proposition"
      technical_feasibility: "preliminary feasibility confirmed"
      
  success_criteria:
    refinement_completeness:
      - "All user stories meet INVEST criteria"
      - "Acceptance criteria are measurable"
      - "Dependencies are identified and documented"
      - "Estimation is ready for development team"
      
    stakeholder_alignment:
      - "Requirements accurately reflect stakeholder needs"
      - "Priorities are validated with business owners"
      - "Scope boundaries are clearly defined"
      
  next_actions:
    for_refinement_facilitator:
      immediate_tasks:
        - "Review and validate user stories"
        - "Detail acceptance criteria"
        - "Identify technical dependencies"
        - "Prepare stories for estimation session"
        
      collaboration_needs:
        - agent: "ux-persona-journey-designer"
          purpose: "User experience validation"
        - agent: "architecture-strategist"
          purpose: "Technical feasibility review"
        
    validation_checkpoints:
      - checkpoint: "Story completeness review"
        criteria: "All stories have complete acceptance criteria"
        timeline: "within 3 days"
        
      - checkpoint: "Stakeholder validation"
        criteria: "Key stakeholders approve refined stories"
        timeline: "within 1 week"
```

### PBI Refinement Facilitator → Architecture Strategist
```yaml
handoff_template: "refinement_to_architecture"
scenario: "PBI詳細化完了後のアーキテクチャ設計移管"

handoff_package:
  metadata:
    handoff_type: "requirements_to_architecture"
    definition_of_ready: "completed"
    
  deliverables:
    primary_outputs:
      - type: "refined_pbi_specifications"
        location: "docs/pbi/ready/"
        story_count: number
        total_story_points: number
        epic_breakdown: "Epic-level organization"
        
      - type: "acceptance_criteria_complete"
        location: "docs/pbi/ready/{pbi_id}/acceptance_criteria.md"
        functional_requirements: "detailed functional specs"
        non_functional_requirements: "performance, security, usability"
        
      - type: "dependency_analysis"
        location: "docs/analysis/dependencies.md"
        internal_dependencies: "PBI to PBI dependencies"
        external_dependencies: "third-party, platform dependencies"
        
    quality_evidence:
      - "stakeholder_sign_off.md"
      - "estimation_consensus.md"
      - "definition_of_ready_checklist.md"
      
  requirements:
    architecture_considerations:
      platform_requirements:
        shared_functionality: "business logic that should be shared"
        platform_specific: "UI and platform integration needs"
        performance_targets: "response time, throughput requirements"
        
      integration_requirements:
        external_apis: "third-party service integrations"
        data_storage: "database and caching requirements"
        security_requirements: "authentication, authorization needs"
        
    technical_constraints:
      technology_preferences: "preferred frameworks and libraries"
      platform_limitations: "known platform-specific constraints"
      compatibility_requirements: "backward compatibility needs"
      
  success_criteria:
    architecture_design:
      - "System architecture supports all PBI requirements"
      - "Platform-specific needs are properly abstracted"
      - "Performance and scalability requirements are met"
      - "Security and compliance requirements are addressed"
      
    implementation_readiness:
      - "Development teams can estimate implementation effort"
      - "Technical risks are identified and mitigated"
      - "Architecture enables efficient development workflow"
      
  next_actions:
    for_architecture_strategist:
      immediate_tasks:
        - "Design overall system architecture"
        - "Define platform abstraction layers"
        - "Specify shared vs platform-specific components"
        - "Create technical implementation guidelines"
        
      collaboration_needs:
        - agent: "backend-security-architect"
          purpose: "Security architecture and API design"
        - agent: "design-system-ui-architect"
          purpose: "UI architecture and design system integration"
        
    validation_requirements:
      architecture_review:
        participants: ["senior developers", "technical leads"]
        criteria: "architecture supports requirements and is implementable"
        timeline: "within 1 week"
        
      platform_validation:
        requirement: "verify architecture works on all target platforms"
        approach: "proof of concept implementations"
        timeline: "within 2 weeks"
```

## 3. UX・デザイン系Agent間ハンドオフ

### UX Persona Journey Designer → Design System UI Architect
```yaml
handoff_template: "ux_to_design_system"
scenario: "ユーザーリサーチ完了後のUI設計移管"

handoff_package:
  metadata:
    handoff_type: "ux_research_to_ui_design"
    user_validation: "completed"
    
  deliverables:
    primary_outputs:
      - type: "user_personas"
        location: "docs/ux/personas/"
        primary_personas: ["persona1", "persona2"]
        persona_details: "demographics, needs, pain points, goals"
        
      - type: "user_journey_maps"
        location: "docs/ux/journey_maps/"
        journey_scenarios: ["scenario1", "scenario2"]
        touchpoints: "interaction points across platforms"
        
      - type: "ux_requirements"
        location: "docs/ux/requirements.md"
        usability_requirements: "specific usability goals"
        accessibility_requirements: "WCAG compliance level"
        user_experience_goals: "measured UX objectives"
        
    supporting_documents:
      - "user_research_findings.md"
      - "competitive_ux_analysis.md"
      - "accessibility_audit.md"
      
  requirements:
    design_principles:
      platform_consistency: "consistent experience across platforms"
      native_feel: "platform-appropriate interactions and patterns"
      accessibility: "inclusive design for diverse users"
      performance: "responsive and efficient interactions"
      
    ui_specifications:
      responsive_design: "mobile-first, scalable to desktop"
      interaction_patterns: "standardized interaction behaviors"
      visual_hierarchy: "clear information architecture"
      
  success_criteria:
    user_centered_design:
      - "Design solutions address identified user needs"
      - "User journeys are optimized for efficiency"
      - "Accessibility requirements are fully integrated"
      - "Design supports measured UX objectives"
      
    platform_optimization:
      - "UI adapts appropriately to each platform"
      - "Native platform conventions are respected"
      - "Consistent brand experience maintained"
      
  next_actions:
    for_design_system_architect:
      immediate_tasks:
        - "Create comprehensive design system"
        - "Design platform-specific UI adaptations"
        - "Develop component library specifications"
        - "Define interaction and animation standards"
        
      validation_approaches:
        user_testing: "prototype testing with target users"
        accessibility_testing: "assistive technology compatibility"
        platform_testing: "native feel validation on each platform"
        
    collaboration_requirements:
      - agent: "interaction-prototyping-agent"
        purpose: "Interactive prototype creation"
        timing: "after initial design system creation"
        
      - agent: "frontend-generalist-dev"
        purpose: "Technical implementation feasibility"
        timing: "during component specification"
```

### Design System UI Architect → Interaction Prototyping Agent
```yaml
handoff_template: "design_system_to_prototyping"
scenario: "デザインシステム完成後のプロトタイプ作成移管"

handoff_package:
  metadata:
    handoff_type: "design_to_prototyping"
    design_approval: "stakeholder_approved"
    
  deliverables:
    primary_outputs:
      - type: "design_system_specification"
        location: "docs/design/design_system/"
        component_library: "comprehensive component definitions"
        style_guide: "colors, typography, spacing standards"
        
      - type: "ui_screen_designs"
        location: "docs/design/screens/"
        screen_count: number
        platform_adaptations: "platform-specific variations"
        responsive_breakpoints: "mobile, tablet, desktop layouts"
        
      - type: "interaction_specifications"
        location: "docs/design/interactions/"
        user_flows: "detailed interaction flows"
        micro_interactions: "button states, transitions, animations"
        
    supporting_documents:
      - "design_tokens.json"
      - "component_specifications.md"
      - "platform_adaptation_guidelines.md"
      
  requirements:
    prototyping_standards:
      fidelity_level: "high-fidelity interactive prototypes"
      platform_coverage: "representative prototypes for each platform"
      interaction_completeness: "all major user flows prototyped"
      
    validation_requirements:
      user_testing: "prototypes suitable for user testing"
      stakeholder_review: "business stakeholder validation"
      technical_review: "developer feasibility confirmation"
      
  success_criteria:
    prototype_quality:
      - "Prototypes accurately represent final product experience"
      - "All critical user interactions are demonstrable"
      - "Platform differences are clearly illustrated"
      - "Performance expectations are realistic"
      
    validation_readiness:
      - "Prototypes support comprehensive user testing"
      - "Business stakeholders can evaluate complete experience"
      - "Developers can estimate implementation effort accurately"
      
  next_actions:
    for_interaction_prototyping:
      immediate_tasks:
        - "Create high-fidelity interactive prototypes"
        - "Implement platform-specific interaction patterns"
        - "Design micro-interactions and transitions"
        - "Prepare prototypes for user testing"
        
      testing_coordination:
        user_testing_setup: "recruit representative users"
        testing_scenarios: "based on user journey maps"
        feedback_collection: "structured feedback mechanisms"
        
    validation_schedule:
      internal_review:
        timeline: "within 1 week"
        participants: ["design team", "product owner"]
        
      user_testing:
        timeline: "within 2 weeks"
        methodology: "moderated usability testing"
        
      stakeholder_approval:
        timeline: "within 3 weeks"
        criteria: "business and user validation complete"
```

## 4. 開発・実装系Agent間ハンドオフ

### Architecture Strategist → Backend Security Architect
```yaml
handoff_template: "architecture_to_backend"
scenario: "システムアーキテクチャ設計完了後のバックエンド実装移管"

handoff_package:
  metadata:
    handoff_type: "architecture_to_backend_implementation"
    architecture_approval: "technical_committee_approved"
    
  deliverables:
    primary_outputs:
      - type: "system_architecture_specification"
        location: "docs/architecture/system_design.md"
        architecture_patterns: "microservices, layered architecture"
        platform_integration: "cross-platform communication patterns"
        
      - type: "api_design_specification"
        location: "docs/api/api_specification.md"
        endpoint_definitions: "RESTful API endpoints"
        data_models: "request/response schemas"
        
      - type: "security_requirements"
        location: "docs/security/requirements.md"
        authentication_strategy: "OAuth 2.0, JWT implementation"
        authorization_model: "role-based access control"
        data_protection: "encryption, data privacy requirements"
        
    supporting_documents:
      - "technology_stack_rationale.md"
      - "scalability_considerations.md"
      - "platform_abstraction_strategy.md"
      
  requirements:
    implementation_standards:
      coding_standards: "Kotlin coding conventions"
      testing_requirements: "unit test coverage >80%"
      documentation_standards: "API documentation with examples"
      
    security_standards:
      authentication: "secure authentication implementation"
      data_encryption: "data-at-rest and data-in-transit encryption"
      vulnerability_protection: "OWASP Top 10 protection"
      
    performance_requirements:
      api_response_time: "< 200ms for 95th percentile"
      throughput: "> 1000 requests per second"
      availability: "99.9% uptime requirement"
      
  success_criteria:
    backend_implementation:
      - "All API endpoints implemented and tested"
      - "Security requirements fully implemented"
      - "Performance targets achieved"
      - "Database design optimized for multiplatform access"
      
    platform_integration:
      - "APIs work seamlessly with all client platforms"
      - "Data formats compatible with shared models"
      - "Error handling consistent across platforms"
      
  next_actions:
    for_backend_security_architect:
      immediate_tasks:
        - "Implement core API endpoints"
        - "Set up authentication and authorization"
        - "Design and implement database schema"
        - "Implement security measures and encryption"
        
      collaboration_requirements:
        - agent: "frontend-generalist-dev"
          purpose: "API integration and shared model alignment"
          frequency: "weekly sync meetings"
        
    quality_assurance:
      security_testing: "penetration testing and vulnerability assessment"
      performance_testing: "load testing and optimization"
      integration_testing: "cross-platform API integration testing"
```

### Architecture Strategist → Frontend Generalist Dev
```yaml
handoff_template: "architecture_to_frontend"
scenario: "システムアーキテクチャ設計完了後のフロントエンド実装移管"

handoff_package:
  metadata:
    handoff_type: "architecture_to_frontend_implementation"
    design_integration: "design_system_included"
    
  deliverables:
    primary_outputs:
      - type: "frontend_architecture_specification"
        location: "docs/architecture/frontend_architecture.md"
        shared_code_structure: "shared/commonMain organization"
        platform_specific_structure: "platform-specific module organization"
        
      - type: "state_management_design"
        location: "docs/architecture/state_management.md"
        state_architecture: "MVI, Redux, or chosen pattern"
        data_flow: "unidirectional data flow specifications"
        
      - type: "platform_integration_guide"
        location: "docs/architecture/platform_integration.md"
        expect_actual_interfaces: "platform abstraction definitions"
        platform_specific_requirements: "per-platform implementation needs"
        
    design_assets:
      - "design_system_tokens.json"
      - "component_specifications.md"
      - "ui_screen_designs/"
      
  requirements:
    implementation_standards:
      shared_code_maximization: ">70% of logic in shared modules"
      platform_consistency: "consistent behavior across platforms"
      performance_optimization: "60fps UI, fast startup times"
      
    platform_requirements:
      android:
        min_sdk: "API 24 (Android 7.0)"
        target_sdk: "latest stable API"
        specific_features: ["camera integration", "biometric auth"]
        
      ios:
        min_ios_version: "iOS 14.0"
        device_support: "iPhone and iPad"
        specific_features: ["Face ID/Touch ID", "Core Location"]
        
      web:
        browser_support: ["Chrome 90+", "Firefox 88+", "Safari 14+"]
        pwa_features: "offline capability, push notifications"
        
      desktop:
        platforms: ["Windows 10+", "macOS 11+", "Linux Ubuntu 20+"]
        native_integration: "file system, system notifications"
        
  success_criteria:
    multiplatform_excellence:
      - "Consistent user experience across all platforms"
      - "Optimal performance on each platform"
      - "Maximum code reuse while respecting platform conventions"
      - "Seamless integration with backend services"
      
    code_quality:
      - "Clean architecture with clear separation of concerns"
      - "Comprehensive test coverage (>80%)"
      - "Well-documented codebase"
      - "Maintainable and extensible code structure"
      
  next_actions:
    for_frontend_generalist_dev:
      immediate_tasks:
        - "Set up multiplatform project structure"
        - "Implement shared business logic"
        - "Create platform-specific UI implementations"
        - "Integrate with design system components"
        
      phased_implementation:
        phase_1: "Core shared functionality and basic UI"
        phase_2: "Platform-specific features and optimizations"
        phase_3: "Advanced features and performance tuning"
        
    collaboration_coordination:
      - agent: "design-system-ui-architect"
        purpose: "Design implementation validation"
        deliverable: "UI component implementations"
        
      - agent: "backend-security-architect"
        purpose: "API integration and data flow"
        deliverable: "Network layer and data models"
        
      - agent: "qa-test-strategist"
        purpose: "Testing strategy implementation"
        deliverable: "Automated test suites"
```

## 5. 品質保証系Agent間ハンドオフ

### Frontend/Backend → QA Test Strategist
```yaml
handoff_template: "implementation_to_qa"
scenario: "実装完了後の包括的品質保証移管"

handoff_package:
  metadata:
    handoff_type: "implementation_to_quality_assurance"
    implementation_status: "feature_complete"
    
  deliverables:
    primary_outputs:
      - type: "implemented_features"
        location: "implemented feature documentation"
        feature_list: "completed feature inventory"
        platform_coverage: "implementation status per platform"
        
      - type: "unit_test_results"
        location: "test_reports/unit_tests/"
        coverage_percentage: number
        test_results: "passing/failing test summary"
        
      - type: "integration_test_results"
        location: "test_reports/integration_tests/"
        api_tests: "API integration test results"
        platform_integration: "cross-platform integration results"
        
    technical_documentation:
      - "implementation_notes.md"
      - "known_issues.md"
      - "performance_benchmarks.md"
      
  requirements:
    testing_scope:
      functional_testing: "all user stories and acceptance criteria"
      non_functional_testing: "performance, security, usability"
      platform_testing: "comprehensive testing on all target platforms"
      regression_testing: "ensure existing functionality not broken"
      
    quality_standards:
      defect_tolerance: "zero critical defects, <5 major defects"
      performance_targets: "meet specified performance requirements"
      usability_standards: "pass usability testing criteria"
      accessibility_compliance: "WCAG 2.1 AA compliance"
      
  success_criteria:
    quality_validation:
      - "All acceptance criteria verified and passing"
      - "Performance requirements met on all platforms"
      - "Security testing passed with no critical vulnerabilities"
      - "User acceptance testing completed successfully"
      
    release_readiness:
      - "Production deployment successfully tested"
      - "Rollback procedures verified"
      - "Support documentation complete"
      - "Training materials prepared"
      
  next_actions:
    for_qa_test_strategist:
      immediate_tasks:
        - "Execute comprehensive test plan"
        - "Perform cross-platform compatibility testing"
        - "Conduct performance and security testing"
        - "Coordinate user acceptance testing"
        
      testing_phases:
        phase_1_functional:
          duration: "1 week"
          focus: "feature functionality verification"
          
        phase_2_non_functional:
          duration: "1 week"
          focus: "performance, security, usability"
          
        phase_3_integration:
          duration: "3 days"
          focus: "end-to-end system testing"
          
    collaboration_requirements:
      defect_resolution:
        process: "defect triage and priority assignment"
        participants: ["developers", "product owner", "qa"]
        
      release_certification:
        criteria: "quality gate passage requirements"
        approvers: ["qa lead", "product owner", "technical lead"]
```

### QA Test Strategist → Strategic Project Manager
```yaml
handoff_template: "qa_to_project_manager"
scenario: "品質保証完了後のリリース承認移管"

handoff_package:
  metadata:
    handoff_type: "quality_assurance_to_release_approval"
    quality_certification: "passed/conditional/failed"
    
  deliverables:
    primary_outputs:
      - type: "comprehensive_test_report"
        location: "test_reports/final_report.md"
        test_execution_summary: "test results across all categories"
        defect_summary: "defects found, resolved, remaining"
        
      - type: "quality_certification"
        location: "quality_reports/certification.md"
        quality_gate_results: "pass/fail status for each quality gate"
        risk_assessment: "remaining risks and mitigation strategies"
        
      - type: "release_readiness_assessment"
        location: "release/readiness_assessment.md"
        production_readiness: "deployment and operational readiness"
        rollback_validation: "rollback procedures tested and verified"
        
    supporting_evidence:
      - "test_execution_details/"
      - "performance_test_results/"
      - "security_test_results/"
      - "user_acceptance_test_results/"
      
  quality_metrics:
    test_coverage:
      functional_coverage: number
      code_coverage: number
      platform_coverage: "percentage of features tested per platform"
      
    defect_metrics:
      total_defects_found: number
      critical_defects: number
      major_defects: number
      minor_defects: number
      defects_resolved: number
      
    performance_results:
      response_time_95th_percentile: "ms"
      throughput_achieved: "requests per second"
      resource_utilization: "CPU, memory usage"
      
  risk_assessment:
    remaining_risks:
      - risk: "risk description"
        impact: "High/Medium/Low"
        probability: "High/Medium/Low"
        mitigation: "mitigation strategy"
        
    release_recommendations:
      recommendation: "GO/NO-GO/CONDITIONAL GO"
      conditions: ["condition1", "condition2"]
      monitoring_requirements: ["metric1", "metric2"]
      
  next_actions:
    for_strategic_project_manager:
      immediate_decisions:
        - "Release approval decision"
        - "Go-live date confirmation"
        - "Risk acceptance and mitigation approval"
        
      release_coordination:
        - "Stakeholder communication"
        - "Deployment coordination"
        - "Support team preparation"
        - "User communication planning"
        
    post_release_requirements:
      monitoring_setup: "production monitoring and alerting"
      support_preparation: "support team training and documentation"
      success_measurement: "KPI tracking and success metrics"
```

## 6. 緊急時・例外ハンドオフ

### 緊急エスカレーション
```yaml
handoff_template: "emergency_escalation"
scenario: "重大な問題発生時の緊急エスカレーション"

handoff_package:
  metadata:
    handoff_type: "emergency_escalation"
    severity: "Critical/High/Medium"
    impact: "Production/Development/Schedule"
    
  issue_description:
    problem_summary: "concise description of the issue"
    affected_components: ["component1", "component2"]
    affected_platforms: ["Android", "iOS", "Web"]
    user_impact: "description of user impact"
    
  immediate_context:
    when_discovered: "timestamp of discovery"
    who_discovered: "person/team that found the issue"
    current_status: "current state of the issue"
    attempted_solutions: ["solution1", "solution2"]
    
  impact_assessment:
    business_impact: "revenue, customer satisfaction impact"
    technical_impact: "system stability, data integrity impact"
    timeline_impact: "effect on project schedule"
    resource_impact: "additional resources needed"
    
  required_actions:
    immediate_actions:
      - action: "immediate action required"
        owner: "responsible person/team"
        deadline: "when action must be completed"
        
    escalation_needs:
      decision_makers: ["person1", "person2"]
      additional_resources: ["resource1", "resource2"]
      external_support: ["vendor", "consultant"]
      
  success_criteria:
    resolution_criteria: "how success will be measured"
    acceptable_workarounds: "temporary solutions if applicable"
    communication_requirements: "who needs to be informed"
```

### ブロッカー解決ハンドオフ
```yaml
handoff_template: "blocker_resolution"
scenario: "作業ブロッカー解決後の作業再開"

handoff_package:
  metadata:
    handoff_type: "blocker_resolution_handoff"
    original_blocker: "description of resolved blocker"
    resolution_approach: "how the blocker was resolved"
    
  resolution_details:
    problem_root_cause: "identified root cause"
    solution_implemented: "solution description"
    validation_performed: "how solution was validated"
    side_effects: "any side effects or additional considerations"
    
  updated_context:
    changed_requirements: ["requirement1", "requirement2"]
    new_constraints: ["constraint1", "constraint2"]
    updated_timeline: "revised schedule if applicable"
    additional_resources: "new resources available or needed"
    
  work_resumption:
    ready_to_proceed: true/false
    prerequisite_validation: "what needs to be checked before proceeding"
    updated_approach: "any changes to original approach"
    
  lessons_learned:
    prevention_measures: "how to prevent similar blockers"
    process_improvements: "suggested process improvements"
    knowledge_sharing: "what should be shared with team"
```

---

## 品質保証チェックリスト

### 送信側Agent責任
```yaml
sender_checklist:
  deliverable_quality:
    - "成果物が完成し、品質基準を満たしている"
    - "必要なドキュメントがすべて用意されている"
    - "テスト・検証が適切に実施されている"
    
  handoff_package_completeness:
    - "ハンドオフパッケージがテンプレートに従って作成されている"
    - "受信者が作業開始に必要な情報がすべて含まれている"
    - "依存関係・制約が明確に記載されている"
    
  validation:
    - "成功基準がすべて満たされている"
    - "品質ゲートを通過している"
    - "ステークホルダー承認が得られている（必要な場合）"
```

### 受信側Agent責任
```yaml
receiver_checklist:
  understanding_verification:
    - "要求内容を正確に理解している"
    - "制約・前提条件を把握している"
    - "成功基準・完了条件を認識している"
    
  readiness_confirmation:
    - "必要なリソース・ツールが準備されている"
    - "技術的前提条件が満たされている"
    - "依存関係が解決されている"
    
  handoff_acceptance:
    - "ハンドオフを正式に受領・承認している"
    - "スケジュール・期待値について合意している"
    - "不明点・課題がクリアになっている"
```

### Orchestrator監視責任
```yaml
orchestrator_monitoring:
  handoff_quality:
    - "ハンドオフが標準テンプレートに従っている"
    - "必要な情報・成果物がすべて含まれている"
    - "品質基準が適切に維持されている"
    
  process_efficiency:
    - "ハンドオフが適切なタイミングで実施されている"
    - "不必要な遅延・手戻りが発生していない"
    - "Agent間の連携が円滑に機能している"
    
  continuous_improvement:
    - "ハンドオフの問題・改善点を収集している"
    - "プロセス改善を継続的に実施している"
    - "ベストプラクティスを蓄積・共有している"
```

---

## まとめ

これらのハンドオフテンプレートにより、Kotlin Multiplatformプロジェクトにおける11の専門agentと統括orchestrator間の効率的で確実な作業引き継ぎが実現されます。

標準化されたテンプレートと品質保証チェックリストにより、一貫性のある高品質なハンドオフを維持し、プロジェクト全体の成功に貢献します。