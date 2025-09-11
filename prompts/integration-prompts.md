# 統合・品質チェック用プロンプト

## 概要
Kotlin Multiplatformプロジェクトにおいて、複数のagentが作成した成果物を統合し、品質を確保するための専用プロンプト集です。`multiplatform-dev-orchestrator`が使用して、一貫性のある高品質な統合を実現します。

## 統合プロンプトの基本構造

### 統合プロンプトテンプレート
```
# Integration Context
[統合の背景・目的・範囲]

# Input Components
[統合対象の成果物・データ詳細]

# Integration Requirements
[統合時に満たすべき要件・制約]

# Quality Standards
[品質基準・検証方法]

# Output Specification
[統合後の期待される成果物]

# Validation Criteria
[統合成功の判定基準]
```

## 1. アーキテクチャ統合プロンプト

### システムアーキテクチャ統合
```
# Integration Context
複数のagentが設計したアーキテクチャコンポーネント（フロントエンド、バックエンド、共通ロジック）を統合し、一貫性のあるKotlin Multiplatformシステムアーキテクチャを構築します。

# Input Components
Architecture Components:
- Frontend Architecture: {frontend_architecture_spec}
  - Agent: frontend-generalist-dev
  - Platform Coverage: {android/ios/web/desktop}
  - State Management: {state_management_approach}
  - UI Architecture: {ui_architecture_pattern}

- Backend Architecture: {backend_architecture_spec}
  - Agent: backend-security-architect
  - API Design: {api_specifications}
  - Security Architecture: {security_design}
  - Data Architecture: {database_design}

- Shared Architecture: {shared_architecture_spec}
  - Agent: architecture-strategist
  - Common Business Logic: {shared_logic_design}
  - Platform Abstraction: {expect_actual_design}
  - Cross-platform Communication: {communication_patterns}

# Integration Requirements
Consistency Requirements:
- Data model consistency across frontend and backend
- API contract alignment between client and server
- Security model coherence across all components
- Error handling standardization across platforms

Platform Integration:
- Seamless data flow between shared and platform-specific code
- Consistent state management across platforms
- Unified authentication and authorization flow
- Performance optimization alignment

Technical Constraints:
- Kotlin Multiplatform compatibility
- Platform-specific optimization preservation
- Scalability and maintainability requirements
- Development workflow efficiency

# Quality Standards
Architecture Quality:
- SOLID principles adherence
- Clean Architecture pattern compliance
- Dependency Injection consistency
- Separation of concerns maintenance

Integration Quality:
- Zero duplication of architectural decisions
- Consistent naming conventions
- Aligned technology stack choices
- Coherent deployment strategy

Documentation Quality:
- Complete architecture documentation
- Decision rationale documentation
- Integration point specifications
- Platform-specific adaptation guidelines

# Output Specification
Generate a comprehensive integrated architecture specification including:

```yaml
integrated_architecture:
  overview:
    architecture_style: "chosen architectural style"
    integration_approach: "how components integrate"
    key_principles: ["principle1", "principle2"]
    
  layer_architecture:
    presentation_layer:
      platforms: ["Android", "iOS", "Web", "Desktop"]
      ui_framework: "Compose Multiplatform"
      state_management: "chosen approach"
      navigation: "navigation strategy"
      
    business_layer:
      shared_logic: "business logic organization"
      domain_models: "domain model structure"
      use_cases: "use case implementation"
      validation: "validation strategy"
      
    data_layer:
      repositories: "repository pattern implementation"
      data_sources: "local and remote data sources"
      caching: "caching strategy"
      synchronization: "data sync approach"
      
    infrastructure_layer:
      networking: "network layer design"
      persistence: "data persistence strategy"
      security: "security implementation"
      logging: "logging and monitoring"

  platform_integration:
    shared_components:
      - component: "component name"
        location: "shared/commonMain/..."
        responsibility: "what it handles"
        dependencies: ["dependency1", "dependency2"]
        
    expect_actual_interfaces:
      - interface: "interface name"
        purpose: "why abstraction is needed"
        platforms: ["Android", "iOS"]
        implementation_notes: "platform-specific considerations"
        
    platform_specific:
      android:
        unique_components: ["component1", "component2"]
        integration_points: ["point1", "point2"]
        optimization_strategies: ["strategy1", "strategy2"]
      ios:
        unique_components: ["component1", "component2"]
        integration_points: ["point1", "point2"]
        optimization_strategies: ["strategy1", "strategy2"]

  api_integration:
    rest_api:
      base_url: "API base URL structure"
      authentication: "authentication mechanism"
      serialization: "JSON serialization approach"
      error_handling: "error response handling"
      
    graphql_api:
      schema_organization: "GraphQL schema structure"
      query_optimization: "query optimization strategy"
      caching: "GraphQL caching approach"
      
  security_integration:
    authentication:
      strategy: "OAuth 2.0, JWT, or chosen method"
      token_management: "token storage and refresh"
      session_management: "session handling"
      
    authorization:
      access_control: "role-based or attribute-based"
      permission_model: "permission structure"
      resource_protection: "resource access control"
      
    data_protection:
      encryption: "encryption at rest and in transit"
      sensitive_data: "sensitive data handling"
      compliance: "GDPR, CCPA, or relevant compliance"

  deployment_integration:
    build_system:
      shared_dependencies: "common dependency management"
      platform_builds: "platform-specific build processes"
      continuous_integration: "CI/CD pipeline integration"
      
    environment_management:
      configuration: "environment-specific configuration"
      secrets_management: "API keys and secrets handling"
      feature_flags: "feature flag integration"

  quality_assurance:
    testing_strategy:
      unit_testing: "shared and platform-specific unit tests"
      integration_testing: "cross-platform integration tests"
      end_to_end_testing: "full system testing approach"
      
    monitoring_integration:
      logging: "centralized logging strategy"
      metrics: "performance and business metrics"
      alerting: "error detection and alerting"
      
    performance_optimization:
      shared_optimizations: "common performance improvements"
      platform_optimizations: "platform-specific optimizations"
      monitoring: "performance monitoring strategy"

  documentation:
    architecture_overview: "high-level architecture documentation"
    component_specifications: "detailed component documentation"
    integration_guides: "how to integrate new components"
    platform_guides: "platform-specific implementation guides"
```

# Validation Criteria
Integration Success:
- All architectural components work together seamlessly
- No conflicting design decisions or implementations
- Platform-specific optimizations preserved
- Development workflow remains efficient

Quality Verification:
- Architecture review passed by technical committee
- Security architecture validated by security team
- Performance requirements verified through benchmarking
- Maintainability confirmed through code review

Documentation Completeness:
- All architectural decisions documented with rationale
- Integration points clearly specified
- Platform-specific considerations documented
- Development and deployment guides complete
```

### 技術スタック統合
```
# Integration Context
各agentが選択・実装した技術要素を統合し、一貫性のある技術スタックとライブラリ管理体制を構築します。

# Input Components
Technology Selections:
- Shared Technologies: {shared_tech_choices}
  - Kotlin Multiplatform: {kmp_version}
  - Compose Multiplatform: {compose_version}
  - Coroutines: {coroutines_version}
  - Serialization: {serialization_library}

- Platform-Specific Technologies:
  - Android: {android_specific_libs}
  - iOS: {ios_specific_frameworks}
  - Web: {web_specific_tools}
  - Desktop: {desktop_specific_libs}
  - Server: {server_frameworks}

- Third-Party Dependencies: {external_dependencies}
  - Networking: {networking_library}
  - Database: {database_solutions}
  - Testing: {testing_frameworks}
  - Authentication: {auth_libraries}

# Integration Requirements
Version Compatibility:
- Kotlin Multiplatform version alignment
- Cross-platform library compatibility
- Third-party dependency version management
- Platform SDK compatibility

License Compatibility:
- Open source license compliance
- Commercial license management
- Legal risk assessment
- Attribution requirements

Performance Integration:
- Bundle size optimization
- Runtime performance impact
- Memory usage considerations
- Battery life optimization (mobile platforms)

# Quality Standards
Dependency Management:
- Version catalog usage for consistency
- Dependency conflict resolution
- Transitive dependency management
- Security vulnerability monitoring

Build System Integration:
- Gradle build script consistency
- Build performance optimization
- Platform-specific build configurations
- CI/CD pipeline compatibility

# Output Specification
Generate a unified technology stack specification:

```yaml
integrated_technology_stack:
  platform_baseline:
    kotlin_multiplatform: "1.9.22"
    compose_multiplatform: "1.5.11"
    gradle: "8.5"
    
  shared_dependencies:
    networking:
      library: "Ktor Client"
      version: "2.3.7"
      platforms: ["Android", "iOS", "Web", "Desktop", "Server"]
      rationale: "First-class KMP support, coroutines integration"
      
    serialization:
      library: "kotlinx.serialization"
      version: "1.6.2"
      formats: ["JSON", "ProtoBuf"]
      rationale: "Native Kotlin support, multiplatform"
      
    database:
      library: "SQLDelight"
      version: "2.0.1"
      platforms: ["Android", "iOS", "Desktop"]
      rationale: "Type-safe SQL, multiplatform support"
      
    dependency_injection:
      library: "Koin"
      version: "3.5.3"
      rationale: "Lightweight, KMP-first design"

  platform_specific_dependencies:
    android:
      ui_framework: "Jetpack Compose"
      architecture: "Android Architecture Components"
      specific_libraries:
        - name: "CameraX"
          version: "1.3.1"
          purpose: "Camera functionality"
        - name: "Biometric"
          version: "1.1.0"
          purpose: "Biometric authentication"
          
    ios:
      ui_integration: "Compose Multiplatform"
      native_integration: "Swift/Objective-C interop"
      specific_frameworks:
        - name: "AVFoundation"
          purpose: "Camera and audio processing"
        - name: "LocalAuthentication"
          purpose: "Biometric authentication"
          
    web:
      target: "WASM"
      browser_apis: "Web API bindings"
      specific_libraries:
        - name: "kotlinx-browser"
          purpose: "Browser API access"
        - name: "compose-web"
          purpose: "Web-specific Compose components"
          
    desktop:
      target_platforms: ["Windows", "macOS", "Linux"]
      specific_libraries:
        - name: "compose-desktop"
          purpose: "Desktop-specific Compose features"
        - name: "kotlinx-coroutines-swing"
          purpose: "Swing integration"
          
    server:
      framework: "Ktor Server"
      deployment: "Docker containerization"
      specific_libraries:
        - name: "Ktor Auth"
          version: "2.3.7"
          purpose: "Authentication middleware"
        - name: "Exposed"
          version: "0.45.0"
          purpose: "Database ORM"

  development_tools:
    build_tools:
      gradle_plugins:
        - "kotlin-multiplatform"
        - "compose-multiplatform"
        - "kotlinx-serialization"
        
    testing_frameworks:
      unit_testing: "kotlin-test"
      ui_testing: "compose-ui-test"
      integration_testing: "Ktor testing"
      
    code_quality:
      static_analysis: "detekt"
      formatting: "ktlint"
      documentation: "dokka"

  version_management:
    strategy: "Version catalog (libs.versions.toml)"
    update_policy: "Monthly dependency updates"
    security_monitoring: "Dependabot integration"
    
  license_compliance:
    approved_licenses: ["Apache-2.0", "MIT", "BSD-3-Clause"]
    restricted_licenses: ["GPL", "AGPL"]
    attribution_requirements: "Automated license collection"

  performance_considerations:
    bundle_size_targets:
      android_apk: "< 50MB"
      ios_ipa: "< 50MB"
      web_bundle: "< 10MB initial load"
      
    runtime_performance:
      startup_time: "< 3 seconds cold start"
      memory_usage: "< 100MB average"
      battery_impact: "Minimal background usage"

  migration_strategy:
    current_to_target: "Phased migration approach"
    compatibility_periods: "6-month overlap support"
    rollback_plans: "Version rollback procedures"
```

# Validation Criteria
Technical Integration:
- All dependencies compile and run successfully
- No version conflicts or compatibility issues
- Platform-specific features work as expected
- Performance targets met across platforms

Legal Compliance:
- All licenses reviewed and approved
- Attribution requirements met
- No license conflicts identified
- Legal team approval obtained

Operational Readiness:
- Build system configured for all platforms
- CI/CD pipelines updated with new dependencies
- Documentation updated with technology choices
- Team training completed for new technologies
```

## 2. UI/UX統合プロンプト

### デザインシステム統合
```
# Integration Context
複数のUX/デザインagentが作成したデザイン成果物を統合し、Kotlin Multiplatformの全プラットフォームに対応した一貫性のあるデザインシステムを構築します。

# Input Components
Design Assets:
- User Research: {ux_research_results}
  - Agent: ux-persona-journey-designer
  - User Personas: {personas_definition}
  - Journey Maps: {user_journey_maps}
  - Usability Requirements: {usability_specs}

- Design System: {design_system_specs}
  - Agent: design-system-ui-architect
  - Component Library: {ui_components}
  - Design Tokens: {design_tokens}
  - Style Guide: {style_guidelines}

- Interaction Design: {interaction_specs}
  - Agent: interaction-prototyping-agent
  - Interactive Prototypes: {prototypes}
  - Micro-interactions: {micro_interactions}
  - Animation Specifications: {animation_specs}

Platform Constraints:
- Android: Material Design 3 compliance
- iOS: Human Interface Guidelines compliance
- Web: Responsive design and accessibility
- Desktop: Native OS integration patterns

# Integration Requirements
Cross-Platform Consistency:
- Visual consistency across all platforms
- Interaction pattern standardization
- Brand identity preservation
- User experience continuity

Platform Optimization:
- Platform-specific adaptations
- Native platform conventions respect
- Performance optimization per platform
- Accessibility compliance per platform

Implementation Feasibility:
- Compose Multiplatform compatibility
- Technical implementation validation
- Performance impact assessment
- Maintainability considerations

# Quality Standards
Design Quality:
- Visual hierarchy clarity
- Color contrast accessibility compliance (WCAG 2.1 AA)
- Typography readability across platforms
- Layout consistency and flexibility

User Experience Quality:
- Task completion efficiency
- Error prevention and recovery
- Intuitive navigation patterns
- Accessibility for diverse users

Technical Quality:
- Design token consistency
- Component reusability
- Platform adaptation elegance
- Implementation guidelines clarity

# Output Specification
Create an integrated design system specification:

```yaml
integrated_design_system:
  foundation:
    brand_identity:
      primary_colors: ["#color1", "#color2", "#color3"]
      secondary_colors: ["#color4", "#color5"]
      semantic_colors:
        success: "#00C851"
        warning: "#FFB347"
        error: "#FF4444"
        info: "#33B5E5"
      
    typography:
      font_families:
        primary: "Platform appropriate font"
        secondary: "Fallback font family"
        monospace: "Code font family"
      
      scale:
        heading_1: "32sp/32pt"
        heading_2: "24sp/24pt"
        heading_3: "20sp/20pt"
        body_large: "16sp/16pt"
        body_medium: "14sp/14pt"
        caption: "12sp/12pt"
        
    spacing:
      base_unit: "8dp/8pt"
      scale: [4, 8, 12, 16, 24, 32, 40, 48, 64]
      
    layout:
      grid_system: "12-column grid"
      breakpoints:
        mobile: "0-599dp"
        tablet: "600-904dp"
        desktop: "905dp+"

  components:
    buttons:
      primary_button:
        visual_spec: "Background, foreground, border specifications"
        states: ["default", "hover", "pressed", "disabled", "loading"]
        platform_adaptations:
          android: "Material 3 Button styling"
          ios: "iOS Button styling with haptic feedback"
          web: "Focus indicators for keyboard navigation"
          desktop: "Hover states and keyboard shortcuts"
          
      secondary_button:
        visual_spec: "Alternative button styling"
        use_cases: ["Cancel actions", "Secondary actions"]
        
    input_fields:
      text_input:
        visual_spec: "Input field appearance"
        states: ["empty", "filled", "error", "disabled", "focused"]
        validation: "Real-time validation patterns"
        platform_adaptations:
          android: "Material Design text field"
          ios: "iOS text field with appropriate keyboard types"
          web: "HTML5 validation integration"
          
    navigation:
      top_app_bar:
        structure: "Title, actions, navigation elements"
        platform_adaptations:
          android: "Material Design app bar"
          ios: "iOS navigation bar"
          web: "Responsive header with breadcrumbs"
          desktop: "Menu bar integration"
          
      bottom_navigation:
        tab_structure: "Maximum 5 tabs, icon + label"
        platform_adaptations:
          android: "Material bottom navigation"
          ios: "UITabBar equivalent"
          web: "Fixed bottom navigation for mobile view"
          
    feedback:
      loading_indicators:
        progress_bar: "Determinate progress indication"
        spinner: "Indeterminate loading indication"
        skeleton_screens: "Content placeholder patterns"
        
      notifications:
        snackbars: "Brief messages with optional action"
        dialogs: "Modal confirmations and forms"
        toasts: "Platform-appropriate notification patterns"

  interaction_patterns:
    navigation_flows:
      hierarchical: "Drill-down navigation patterns"
      flat: "Tab-based navigation patterns"
      modal: "Overlay navigation patterns"
      
    gestures:
      touch_interactions:
        tap: "Primary selection gesture"
        long_press: "Context menu trigger"
        swipe: "Navigation and action gestures"
      keyboard_interactions:
        shortcuts: "Platform-appropriate keyboard shortcuts"
        navigation: "Focus management and tab order"
        
    micro_interactions:
      button_press: "Visual and haptic feedback"
      form_submission: "Loading states and success feedback"
      navigation: "Transition animations between screens"
      
  animations:
    motion_principles:
      easing: "Natural motion curves"
      duration: "Appropriate timing for different UI elements"
      choreography: "Coordinated motion across elements"
      
    transition_types:
      screen_transitions: "Navigation between major UI sections"
      component_transitions: "State changes within components"
      loading_transitions: "Content loading and updating"
      
    platform_adaptations:
      android: "Material Motion specifications"
      ios: "iOS animation guidelines"
      web: "Respect user's motion preferences"
      desktop: "Subtle animations appropriate for desktop use"

  accessibility:
    visual_accessibility:
      color_contrast: "WCAG 2.1 AA compliance (4.5:1 ratio minimum)"
      text_size: "Support for user font size preferences"
      focus_indicators: "Clear focus indication for keyboard users"
      
    interaction_accessibility:
      touch_targets: "Minimum 44dp/44pt touch target size"
      keyboard_navigation: "Full keyboard accessibility"
      screen_reader: "Semantic markup and labels"
      
    platform_specific:
      android: "TalkBack optimization"
      ios: "VoiceOver optimization"
      web: "ARIA labels and semantic HTML"
      desktop: "Screen reader and high contrast support"

  responsive_behavior:
    layout_adaptation:
      mobile: "Single column, touch-optimized"
      tablet: "Two-column layouts, hybrid input support"
      desktop: "Multi-column layouts, mouse and keyboard optimized"
      
    component_scaling:
      typography: "Fluid typography scaling"
      spacing: "Proportional spacing adjustments"
      imagery: "Responsive image handling"

  platform_implementation:
    compose_multiplatform:
      theme_implementation: "Material Theme for Compose Multiplatform"
      component_library: "Shared composable components"
      platform_adaptations: "expect/actual for platform-specific styling"
      
    design_tokens:
      token_organization: "Structured design token taxonomy"
      platform_mapping: "Platform-specific token values"
      tooling_integration: "Design tool to code integration"

  documentation:
    design_guidelines: "Comprehensive design usage guidelines"
    component_documentation: "Individual component specifications"
    implementation_guides: "Developer implementation instructions"
    accessibility_guides: "Accessibility implementation requirements"
```

# Validation Criteria
User Experience Validation:
- User testing confirms intuitive navigation
- Accessibility testing passes on all platforms
- Performance testing shows smooth interactions
- Cross-platform experience feels consistent

Design Quality Validation:
- Visual design review approved by design team
- Brand guidelines compliance verified
- Platform design review completed
- Accessibility audit passed

Implementation Validation:
- All components successfully implemented in Compose Multiplatform
- Platform adaptations work correctly
- Performance targets met on all platforms
- Developer documentation enables efficient implementation
```

### UI実装統合
```
# Integration Context
各プラットフォームで実装されたUI要素を統合し、デザインシステム準拠の一貫性あるユーザーインターフェースを実現します。

# Input Components
Platform Implementations:
- Android UI: {android_ui_implementation}
  - Compose for Android components
  - Material Design 3 adaptations
  - Android-specific UI patterns
  
- iOS UI: {ios_ui_implementation}
  - Compose Multiplatform for iOS
  - iOS design guideline adaptations
  - iOS-specific interaction patterns
  
- Web UI: {web_ui_implementation}
  - Compose for Web implementation
  - Responsive design implementation
  - Web accessibility features
  
- Desktop UI: {desktop_ui_implementation}
  - Compose for Desktop
  - Desktop interaction patterns
  - OS-specific integrations

Design Specifications:
- Design System: {design_system_spec}
- Component Library: {component_library}
- Interaction Specifications: {interaction_specs}

# Integration Requirements
Visual Consistency:
- Design system adherence across all platforms
- Color, typography, and spacing consistency
- Component behavior standardization
- Brand identity preservation

Functional Consistency:
- User flow consistency across platforms
- Data presentation standardization
- Error handling uniformity
- Loading state consistency

Platform Optimization:
- Native platform convention respect
- Performance optimization maintenance
- Platform-specific feature utilization
- User expectation alignment

# Quality Standards
Implementation Quality:
- Code reuse maximization through shared components
- Platform-specific optimizations preservation
- Performance benchmark achievement
- Maintainability and extensibility

User Experience Quality:
- Smooth and responsive interactions
- Intuitive navigation patterns
- Accessible design implementation
- Error-free user workflows

# Output Specification
Generate integrated UI implementation specification:

```yaml
integrated_ui_implementation:
  shared_components:
    button_component:
      implementation_location: "shared/commonMain/ui/components/Button.kt"
      shared_logic:
        - "Click handling"
        - "State management"
        - "Accessibility properties"
      platform_adaptations:
        android: "Material 3 styling, ripple effects"
        ios: "iOS styling, haptic feedback"
        web: "Focus management, keyboard interaction"
        desktop: "Hover effects, keyboard shortcuts"
        
    text_field_component:
      implementation_location: "shared/commonMain/ui/components/TextField.kt"
      shared_logic:
        - "Input validation"
        - "State management"
        - "Error handling"
      platform_adaptations:
        android: "Material Design input styling"
        ios: "iOS text field appearance"
        web: "HTML5 validation integration"
        desktop: "Desktop input conventions"

  platform_specific_implementations:
    android:
      unique_components:
        - component: "AndroidPermissionDialog"
          purpose: "Android runtime permissions"
          implementation: "androidMain/ui/dialogs/"
          
      platform_integrations:
        - integration: "System UI visibility"
          implementation: "Status bar, navigation bar theming"
        - integration: "Material Theme"
          implementation: "Dynamic color support"
          
    ios:
      unique_components:
        - component: "IOSActionSheet"
          purpose: "iOS-style action selection"
          implementation: "iosMain/ui/dialogs/"
          
      platform_integrations:
        - integration: "Safe area handling"
          implementation: "iPhone notch and Dynamic Island support"
        - integration: "iOS navigation patterns"
          implementation: "Back gesture, navigation bar"
          
    web:
      unique_components:
        - component: "WebFileUpload"
          purpose: "Web file selection"
          implementation: "webMain/ui/inputs/"
          
      platform_integrations:
        - integration: "Responsive breakpoints"
          implementation: "CSS media query integration"
        - integration: "Web accessibility"
          implementation: "ARIA labels, semantic HTML"
          
    desktop:
      unique_components:
        - component: "DesktopContextMenu"
          purpose: "Right-click context menus"
          implementation: "desktopMain/ui/menus/"
          
      platform_integrations:
        - integration: "Window management"
          implementation: "Resize, minimize, maximize handling"
        - integration: "System integration"
          implementation: "System tray, native dialogs"

  state_management_integration:
    shared_state:
      location: "shared/commonMain/ui/state/"
      approach: "MVI (Model-View-Intent) pattern"
      components:
        - "UI State data classes"
        - "UI Event sealed classes"
        - "ViewModel base classes"
        
    platform_state:
      android: "Android ViewModel integration"
      ios: "iOS lifecycle integration"
      web: "Browser state management"
      desktop: "Window state persistence"

  navigation_integration:
    shared_navigation:
      location: "shared/commonMain/navigation/"
      approach: "Compose Navigation integration"
      structure:
        - "Navigation graphs"
        - "Route definitions"
        - "Navigation state management"
        
    platform_navigation:
      android: "Android Navigation Component integration"
      ios: "iOS navigation controller integration"
      web: "Browser history integration"
      desktop: "Multi-window navigation"

  theming_integration:
    shared_theming:
      location: "shared/commonMain/ui/theme/"
      components:
        - "Design tokens"
        - "Color schemes"
        - "Typography definitions"
        - "Shape definitions"
        
    platform_theming:
      android: "Material 3 dynamic theming"
      ios: "iOS appearance mode support"
      web: "CSS custom properties integration"
      desktop: "OS theme detection"

  accessibility_integration:
    shared_accessibility:
      approach: "Accessibility-first component design"
      features:
        - "Semantic content descriptions"
        - "Focus management"
        - "Screen reader support"
        
    platform_accessibility:
      android: "TalkBack integration"
      ios: "VoiceOver integration"
      web: "ARIA implementation"
      desktop: "Screen reader and high contrast support"

  performance_optimization:
    shared_optimizations:
      - "Compose performance best practices"
      - "State hoisting optimization"
      - "Recomposition minimization"
      
    platform_optimizations:
      android: "Android-specific performance tuning"
      ios: "iOS memory and battery optimization"
      web: "Bundle size and loading optimization"
      desktop: "Desktop UI responsiveness optimization"

  testing_integration:
    shared_tests:
      location: "shared/commonTest/ui/"
      coverage:
        - "Component unit tests"
        - "State management tests"
        - "Navigation tests"
        
    platform_tests:
      android: "Android UI tests (Espresso/Compose)"
      ios: "iOS UI tests (XCUITest integration)"
      web: "Web UI tests (Selenium/Playwright)"
      desktop: "Desktop UI tests (TestFX equivalent)"

  build_integration:
    shared_build:
      configuration: "Common UI module configuration"
      dependencies: "Shared UI dependency management"
      
    platform_build:
      android: "Android-specific UI dependencies"
      ios: "iOS framework integration"
      web: "Web-specific build configuration"
      desktop: "Desktop packaging configuration"
```

# Validation Criteria
UI Consistency Validation:
- Visual regression testing passes on all platforms
- Component behavior matches design specifications
- Cross-platform user flows work identically
- Brand guidelines compliance verified

Performance Validation:
- UI performance benchmarks met on all platforms
- Memory usage within acceptable limits
- Smooth animations and transitions verified
- Loading times meet performance targets

Accessibility Validation:
- Accessibility testing passes on all platforms
- Screen reader compatibility confirmed
- Keyboard navigation works completely
- High contrast mode support verified

Code Quality Validation:
- Code review approval from UI/UX team
- Automated tests pass for all components
- Code coverage meets minimum requirements
- Documentation complete and accurate
```

## 3. データ・API統合プロンプト

### API統合・データフロー統合
```
# Integration Context
フロントエンドとバックエンドのagentが実装したAPI・データ関連コンポーネントを統合し、一貫性のあるデータフローを実現します。

# Input Components
Backend API Implementation:
- REST API Endpoints: {api_endpoints}
  - Agent: backend-security-architect
  - Authentication: {auth_implementation}
  - Data Models: {server_data_models}
  - Error Handling: {server_error_handling}

Frontend Data Layer:
- API Client Implementation: {api_client}
  - Agent: frontend-generalist-dev
  - Network Layer: {network_implementation}
  - Data Models: {client_data_models}
  - Caching Strategy: {caching_implementation}

Shared Data Models:
- Common Models: {shared_models}
  - Location: shared/commonMain/data/models/
  - Serialization: {serialization_implementation}
  - Validation: {validation_rules}

# Integration Requirements
Data Model Consistency:
- Client and server model alignment
- Serialization format standardization
- Validation rule synchronization
- Version compatibility maintenance

API Contract Compliance:
- Request/response format consistency
- Error response standardization
- HTTP status code alignment
- Authentication flow integration

Cross-Platform Data Access:
- Consistent data access patterns across platforms
- Offline capability synchronization
- Data synchronization strategies
- Conflict resolution mechanisms

# Quality Standards
Data Integrity:
- No data loss during serialization/deserialization
- Consistent data validation across client and server
- Proper error propagation and handling
- Data security and privacy compliance

Performance Standards:
- Efficient data transfer (minimal payload size)
- Appropriate caching strategies
- Network request optimization
- Background data synchronization

API Design Quality:
- RESTful design principles adherence
- Consistent naming conventions
- Proper HTTP method usage
- Comprehensive error responses

# Output Specification
Generate integrated data and API specification:

```yaml
integrated_data_api:
  api_contract:
    base_configuration:
      base_url: "https://api.example.com/v1"
      authentication: "Bearer token (JWT)"
      content_type: "application/json"
      api_version: "v1"
      
    endpoints:
      authentication:
        login:
          method: "POST"
          path: "/auth/login"
          request_model: "LoginRequest"
          response_model: "LoginResponse"
          error_responses: [400, 401, 429, 500]
          
        refresh_token:
          method: "POST"
          path: "/auth/refresh"
          request_model: "RefreshTokenRequest"
          response_model: "TokenResponse"
          error_responses: [400, 401, 500]
          
      user_management:
        get_profile:
          method: "GET"
          path: "/users/profile"
          response_model: "UserProfile"
          error_responses: [401, 404, 500]
          
        update_profile:
          method: "PUT"
          path: "/users/profile"
          request_model: "UpdateProfileRequest"
          response_model: "UserProfile"
          error_responses: [400, 401, 403, 500]
          
      data_operations:
        get_items:
          method: "GET"
          path: "/items"
          query_parameters: ["page", "limit", "filter", "sort"]
          response_model: "PaginatedItemResponse"
          error_responses: [400, 401, 500]
          
        create_item:
          method: "POST"
          path: "/items"
          request_model: "CreateItemRequest"
          response_model: "Item"
          error_responses: [400, 401, 403, 422, 500]

  shared_data_models:
    location: "shared/commonMain/data/models/"
    
    user_models:
      User:
        properties:
          id: "String (UUID)"
          email: "String (validated)"
          displayName: "String"
          avatar: "String? (URL)"
          createdAt: "Instant"
          updatedAt: "Instant"
        validation:
          email: "Valid email format"
          displayName: "1-50 characters"
          
      UserProfile:
        inherits_from: "User"
        additional_properties:
          preferences: "UserPreferences"
          statistics: "UserStatistics"
          
    business_models:
      Item:
        properties:
          id: "String (UUID)"
          title: "String"
          description: "String?"
          category: "ItemCategory"
          status: "ItemStatus"
          ownerId: "String (UUID)"
          createdAt: "Instant"
          updatedAt: "Instant"
        validation:
          title: "1-100 characters, not blank"
          description: "0-1000 characters"
          
      ItemCategory:
        type: "enum"
        values: ["ELECTRONICS", "BOOKS", "CLOTHING", "OTHER"]
        
      ItemStatus:
        type: "enum"
        values: ["ACTIVE", "INACTIVE", "DELETED"]

  serialization_integration:
    configuration:
      library: "kotlinx.serialization"
      json_configuration:
        ignore_unknown_keys: true
        encode_defaults: false
        pretty_print: false (production), true (debug)
        
    custom_serializers:
      instant_serializer:
        purpose: "ISO 8601 timestamp serialization"
        implementation: "shared/commonMain/serialization/"
        
      uuid_serializer:
        purpose: "UUID string serialization"
        implementation: "shared/commonMain/serialization/"

  network_layer_integration:
    shared_networking:
      location: "shared/commonMain/network/"
      client_configuration:
        library: "Ktor Client"
        timeout_configuration:
          request_timeout: "30 seconds"
          connect_timeout: "10 seconds"
          socket_timeout: "30 seconds"
        retry_configuration:
          max_retries: 3
          retry_delays: [1000, 2000, 4000] # milliseconds
          
    authentication_integration:
      token_management:
        storage: "Platform-specific secure storage"
        refresh_strategy: "Automatic refresh on 401"
        logout_handling: "Clear tokens and redirect"
        
    error_handling:
      network_errors:
        no_connection: "Display offline mode"
        timeout: "Retry with exponential backoff"
        server_error: "Display user-friendly error message"
        
      api_errors:
        400_bad_request: "Display field-specific validation errors"
        401_unauthorized: "Redirect to login"
        403_forbidden: "Display permission error"
        404_not_found: "Display not found message"
        422_validation_error: "Display field validation errors"
        500_server_error: "Display generic error message"

  platform_specific_implementations:
    android:
      network_security:
        cleartext_traffic: false
        certificate_pinning: true
        network_security_config: "res/xml/network_security_config.xml"
        
    ios:
      network_configuration:
        app_transport_security: "Require HTTPS"
        certificate_pinning: "Implemented via URLSession"
        
    web:
      cors_handling:
        allowed_origins: "Configured server-side"
        credentials_mode: "include"
        
    desktop:
      proxy_support: "System proxy detection"
      certificate_handling: "System certificate store"

  caching_integration:
    caching_strategy:
      approach: "Multi-level caching"
      levels:
        memory_cache: "In-memory LRU cache"
        disk_cache: "Platform-specific persistent storage"
        network_cache: "HTTP cache headers"
        
    cache_policies:
      user_data:
        ttl: "5 minutes"
        strategy: "Cache-first with background refresh"
        
      static_data:
        ttl: "1 hour"
        strategy: "Cache-first"
        
      dynamic_data:
        ttl: "30 seconds"
        strategy: "Network-first"

  offline_support:
    offline_detection:
      implementation: "Platform-specific connectivity monitoring"
      fallback_behavior: "Use cached data with offline indicators"
      
    data_synchronization:
      sync_strategy: "Queue operations for retry when online"
      conflict_resolution: "Last-write-wins with user notification"
      
    offline_capabilities:
      read_operations: "Full offline support with cached data"
      write_operations: "Queue for sync when online"
      critical_operations: "Require online connection"

  testing_integration:
    api_testing:
      mock_server: "Ktor Mock Server for testing"
      test_data: "Predefined test datasets"
      error_simulation: "Network error and API error simulation"
      
    integration_testing:
      end_to_end_tests: "Full API integration tests"
      platform_testing: "Platform-specific networking tests"
      offline_testing: "Offline behavior verification"

  monitoring_integration:
    api_monitoring:
      request_logging: "Request/response logging (debug mode)"
      performance_metrics: "Request timing and payload size"
      error_tracking: "API error rate monitoring"
      
    data_quality:
      serialization_errors: "Track serialization failures"
      cache_performance: "Cache hit/miss rates"
      sync_success_rate: "Data synchronization success tracking"
```

# Validation Criteria
API Integration Validation:
- All API endpoints working correctly from all platforms
- Data serialization/deserialization working flawlessly
- Authentication flow working across platforms
- Error handling providing appropriate user feedback

Data Consistency Validation:
- No data model mismatches between client and server
- Validation rules consistent between frontend and backend
- Data transformation working correctly in all scenarios
- Cache invalidation working properly

Performance Validation:
- API response times meeting performance targets
- Network payload sizes optimized
- Caching strategies improving performance
- Offline functionality working seamlessly

Security Validation:
- Authentication and authorization working correctly
- Data encryption in transit verified
- Sensitive data handling compliant with security requirements
- API security measures (rate limiting, etc.) working properly
```

## 4. 品質統制統合プロンプト

### 総合品質評価
```
# Integration Context
各agentが実装した機能とQAagentが実施したテスト結果を統合し、リリース可能性の総合的な品質評価を実施します。

# Input Components
Implementation Results:
- Feature Implementation Status: {implementation_status}
  - Completed Features: {completed_features}
  - Platform Coverage: {platform_coverage}
  - Known Issues: {known_issues}

Testing Results:
- Unit Test Results: {unit_test_results}
  - Agent: qa-test-strategist
  - Coverage: {test_coverage}
  - Pass/Fail Status: {test_status}

- Integration Test Results: {integration_test_results}
  - Cross-platform Integration: {cross_platform_tests}
  - API Integration: {api_integration_tests}
  - End-to-End Tests: {e2e_test_results}

- Performance Test Results: {performance_results}
  - Load Testing: {load_test_results}
  - Platform Performance: {platform_performance}
  - Resource Usage: {resource_usage}

Quality Metrics:
- Code Quality: {code_quality_metrics}
- Security Assessment: {security_assessment}
- Accessibility Compliance: {accessibility_results}
- User Acceptance Testing: {uat_results}

# Integration Requirements
Comprehensive Quality Assessment:
- Functional requirement compliance verification
- Non-functional requirement assessment
- Platform-specific quality verification
- Security and privacy compliance

Release Readiness Evaluation:
- Production deployment readiness
- Rollback capability verification
- Support and maintenance readiness
- User communication preparation

Risk Assessment:
- Technical risks identification
- Business impact assessment
- Mitigation strategy validation
- Go/No-Go decision support

# Quality Standards
Release Quality Criteria:
- Zero critical defects
- < 5 major defects with workarounds
- All acceptance criteria met
- Performance targets achieved

Platform Quality Criteria:
- Consistent functionality across platforms
- Platform-specific optimizations verified
- Native user experience maintained
- Accessibility requirements met

Operational Quality Criteria:
- Deployment processes tested
- Monitoring and alerting configured
- Support documentation complete
- Team training completed

# Output Specification
Generate comprehensive quality assessment report:

```yaml
integrated_quality_assessment:
  executive_summary:
    overall_quality_score: "A/B/C/D/F"
    release_recommendation: "GO/CONDITIONAL GO/NO-GO"
    confidence_level: "High/Medium/Low"
    key_findings: ["finding1", "finding2", "finding3"]
    
  functional_quality:
    requirement_coverage:
      total_requirements: number
      implemented_requirements: number
      coverage_percentage: number
      missing_requirements: ["req1", "req2"]
      
    feature_completeness:
      total_features: number
      completed_features: number
      completion_percentage: number
      incomplete_features:
        - feature: "feature name"
          status: "In Progress/Blocked/Not Started"
          impact: "High/Medium/Low"
          mitigation: "planned mitigation approach"
          
    acceptance_criteria_status:
      total_criteria: number
      met_criteria: number
      success_rate: number
      failed_criteria:
        - criteria: "acceptance criteria description"
          feature: "related feature"
          failure_reason: "why it failed"
          resolution_plan: "how it will be addressed"

  platform_quality:
    android:
      implementation_status: "Complete/Partial/Not Started"
      test_coverage: number
      performance_score: "A/B/C/D/F"
      specific_issues: ["issue1", "issue2"]
      certification_status: "Passed/Conditional/Failed"
      
    ios:
      implementation_status: "Complete/Partial/Not Started"
      test_coverage: number
      performance_score: "A/B/C/D/F"
      specific_issues: ["issue1", "issue2"]
      certification_status: "Passed/Conditional/Failed"
      
    web:
      implementation_status: "Complete/Partial/Not Started"
      test_coverage: number
      performance_score: "A/B/C/D/F"
      browser_compatibility: ["Chrome: Pass", "Firefox: Pass", "Safari: Issues"]
      accessibility_score: "AA/A/Fail"
      certification_status: "Passed/Conditional/Failed"
      
    desktop:
      implementation_status: "Complete/Partial/Not Started"
      test_coverage: number
      performance_score: "A/B/C/D/F"
      os_compatibility: ["Windows: Pass", "macOS: Pass", "Linux: Pass"]
      certification_status: "Passed/Conditional/Failed"
      
    server:
      implementation_status: "Complete/Partial/Not Started"
      api_coverage: number
      performance_score: "A/B/C/D/F"
      security_score: "A/B/C/D/F"
      scalability_rating: "Excellent/Good/Fair/Poor"
      certification_status: "Passed/Conditional/Failed"

  technical_quality:
    code_quality:
      overall_score: "A/B/C/D/F"
      maintainability_index: number
      technical_debt_ratio: number
      code_coverage: number
      static_analysis_score: number
      
    architecture_quality:
      design_compliance: "Full/Partial/Non-compliant"
      architecture_review_status: "Approved/Conditional/Rejected"
      scalability_assessment: "Excellent/Good/Fair/Poor"
      maintainability_score: "High/Medium/Low"
      
    security_quality:
      vulnerability_scan_results: "Pass/Fail"
      penetration_test_results: "Pass/Conditional/Fail"
      compliance_status: "GDPR: Compliant, CCPA: Compliant"
      security_score: "A/B/C/D/F"

  performance_quality:
    response_times:
      api_response_95th_percentile: "200ms"
      ui_load_time: "< 3 seconds"
      background_task_performance: "Within targets"
      
    resource_utilization:
      memory_usage: "< 100MB average"
      cpu_usage: "< 50% average"
      battery_impact: "Minimal"
      network_usage: "Optimized"
      
    scalability_testing:
      load_test_results: "Pass - 1000 concurrent users"
      stress_test_results: "System stable under 150% normal load"
      spike_test_results: "Recovery time < 30 seconds"

  user_experience_quality:
    usability_testing:
      task_completion_rate: "95%"
      user_satisfaction_score: "8.5/10"
      error_rate: "< 5%"
      time_to_complete_tasks: "Within expected ranges"
      
    accessibility_compliance:
      wcag_compliance_level: "AA"
      screen_reader_compatibility: "Pass"
      keyboard_navigation: "Pass"
      color_contrast: "Pass"
      
    cross_platform_consistency:
      ui_consistency_score: "95%"
      functional_consistency_score: "98%"
      user_flow_consistency: "Pass"

  defect_analysis:
    defect_summary:
      total_defects: number
      critical_defects: 0
      major_defects: number
      minor_defects: number
      cosmetic_defects: number
      
    defect_categories:
      functional: number
      ui_ux: number
      performance: number
      security: number
      compatibility: number
      
    platform_defect_distribution:
      android: number
      ios: number
      web: number
      desktop: number
      server: number
      shared: number

  risk_assessment:
    technical_risks:
      - risk: "Performance degradation under high load"
        probability: "Low"
        impact: "Medium"
        mitigation: "Load balancer configuration and monitoring"
        status: "Mitigated"
        
    business_risks:
      - risk: "User adoption slower than expected"
        probability: "Medium"
        impact: "High"
        mitigation: "Enhanced user onboarding and support"
        status: "Monitoring"
        
    operational_risks:
      - risk: "Deployment complexity"
        probability: "Low"
        impact: "Medium"
        mitigation: "Automated deployment pipelines tested"
        status: "Mitigated"

  release_readiness:
    deployment_readiness:
      build_automation: "Ready"
      environment_preparation: "Complete"
      rollback_procedures: "Tested"
      monitoring_setup: "Configured"
      
    operational_readiness:
      support_documentation: "Complete"
      team_training: "Complete"
      incident_response: "Procedures defined"
      success_metrics: "KPIs defined and tracking setup"
      
    business_readiness:
      stakeholder_approval: "Obtained"
      marketing_materials: "Ready"
      user_communication: "Planned"
      legal_compliance: "Verified"

  recommendations:
    immediate_actions:
      - "Fix remaining major defects"
      - "Complete performance optimization for iOS"
      - "Finalize user documentation"
      
    go_live_conditions:
      - "All critical and major defects resolved"
      - "Performance targets met on all platforms"
      - "Security review completed and approved"
      - "Rollback procedures validated"
      
    post_launch_monitoring:
      - "Monitor user adoption and feedback closely"
      - "Track performance metrics for first 30 days"
      - "Prepare hotfix capabilities for immediate issues"
      
    continuous_improvement:
      - "Conduct post-launch retrospective in 2 weeks"
      - "Plan performance optimization for next release"
      - "Enhance automated testing coverage"

  certification:
    quality_gate_status: "Passed/Conditional/Failed"
    certification_date: "ISO 8601 timestamp"
    certified_by: "QA Lead name"
    valid_until: "certification expiration date"
    conditions: ["condition1", "condition2"] # if conditional pass
```

# Validation Criteria
Quality Assessment Validation:
- Assessment covers all critical quality dimensions
- Risk assessment is comprehensive and realistic
- Recommendations are actionable and prioritized
- Certification decision is well-justified

Stakeholder Alignment:
- Quality standards met according to stakeholder expectations
- Business requirements fully addressed
- Technical requirements satisfied
- User experience meets design goals

Release Decision Support:
- Clear go/no-go recommendation with rationale
- Risk mitigation strategies are viable
- Rollback plans are tested and ready
- Success metrics and monitoring are in place
```

---

## 使用ガイドライン

### プロンプト選択基準
1. **統合の複雑度**に応じてプロンプトを選択
2. **関係するAgent数**と**成果物の種類**を考慮
3. **品質要件**と**リリース期限**のバランスを取る

### カスタマイズポイント
- プロジェクト固有の制約・要件を追加
- プラットフォーム固有の考慮事項を調整
- チーム習熟度に応じた詳細レベル調整

### 継続的改善
- 統合結果の品質・効率を定期的に評価
- プロンプトの精度向上とベストプラクティス蓄積
- チームフィードバックに基づくテンプレート改善

---

## まとめ

これらの統合・品質チェック用プロンプトにより、Kotlin Multiplatformプロジェクトにおける複数agent成果物の効果的な統合と包括的な品質保証が実現されます。

継続的な使用と改善により、プロジェクト固有の最適化を進め、開発チーム全体の生産性と品質向上を図ります。