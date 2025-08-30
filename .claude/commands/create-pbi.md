# Create PBI Command

**Description**: ステークホルダーからのヒアリングを通じてPBI（Product Backlog Item）を作成します

**Usage**: `/create-pbi [プロジェクト名またはテーマ]`

**Examples**:
- `/create-pbi ユーザー認証機能`
- `/create-pbi 決済システム改善`
- `/create-pbi`

## Command Implementation

```javascript
async function execute(args) {
  // PBI作成プロセスを実行
  const theme = args.join(' ') || 'new feature';
  
  // product-owner-pbi-manager agentを使用してインタラクティブヒアリングを実行
  await useAgent('product-owner-pbi-manager', {
    task: 'interactive_pbi_creation',
    theme: theme,
    workflow: '/Users/shota/AndroidStudioProjects/Playground/docs/pbi/workflow.md',
    template: '/Users/shota/AndroidStudioProjects/Playground/docs/pbi/templates/pbi-template.md',
    output_directory: '/Users/shota/AndroidStudioProjects/Playground/docs/pbi/active/'
  });
}
```

## Process Flow

1. **初期化**: テーマ設定とPBIワークフロー開始
2. **ステークホルダーヒアリング**: 対話形式での要件聞き取り
3. **要件分析**: 収集した情報の整理と分析
4. **PBI作成**: テンプレートベースのドキュメント生成
5. **レビュー**: 作成したPBIの確認と調整
6. **保存**: active/ディレクトリへの保存

## Agent Instructions

product-owner-pbi-manager agentは以下の手順でPBI作成を実行します：

### Phase 1: ヒアリング準備
- テーマの確認と背景情報の収集
- ヒアリング計画の立案

### Phase 2: インタラクティブヒアリング
- 5W1H+技術要件の体系的質問
- プラットフォーム要件の詳細確認
- ユーザーエクスペリエンス要件の聞き取り

### Phase 3: 要件分析・整理
- MoSCoW分析による優先度付け
- プラットフォーム影響度分析
- 技術的実現可能性評価

### Phase 4: PBI作成
- テンプレートベースのドキュメント作成
- ユーザーストーリーの作成
- 受け入れ条件の定義
- 見積もりと優先度の設定

### Phase 5: 確認・調整
- 作成したPBIの内容確認
- 必要に応じた修正・調整
- 最終承認プロセス

## Output

作成されるPBIは以下の形式で保存されます：
- **ファイル名**: `PBI-{AUTO_ID}-{テーマ}.md`
- **場所**: `docs/pbi/active/`
- **形式**: PBIテンプレートに準拠