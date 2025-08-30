# PBI (Product Backlog Item) 管理

このディレクトリはプロダクトバックログアイテム（PBI）の管理用です。

## ディレクトリ構造

```
docs/pbi/
├── README.md           # このファイル
├── templates/          # PBIテンプレート
├── active/            # 進行中のPBI
├── completed/         # 完了したPBI
└── archived/          # アーカイブされたPBI
```

## PBI命名規則

PBIファイルは以下の命名規則に従います：
- `PBI-{ID}-{簡潔なタイトル}.md`
- 例: `PBI-001-ユーザー認証機能.md`

## PBIテンプレート

新しいPBIを作成する際は、`templates/`ディレクトリのテンプレートを使用してください。

## 管理フロー

1. **新規作成**: `templates/`のテンプレートを使用してPBIを作成
2. **開発開始**: `active/`ディレクトリに移動
3. **完了**: `completed/`ディレクトリに移動
4. **アーカイブ**: 必要に応じて`archived/`に移動