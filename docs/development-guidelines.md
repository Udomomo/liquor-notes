# 開発ガイドライン

## 1. コーディング規約

### 言語
- TypeScript を使用（`strict: true`）
- `any` 型の使用は禁止。やむを得ない場合は `unknown` を使用し型ガードで絞り込む

### ファイル
- 1ファイル1エクスポートを基本とする（例外: 型定義ファイル）
- ファイルの末尾には改行を入れる

### インポート
- 相対パスではなくパスエイリアス（`@/`）を使用する
  - `import { Drink } from "@/types"`
  - `import { supabase } from "@/lib/supabase/client"`

### 関数
- React コンポーネントはアロー関数で定義する
- コンポーネント以外の関数もアロー関数を基本とする
- 副作用のない関数を優先する

### エラーハンドリング
- API Route Handlers ではtry-catchで例外を捕捉し、適切なHTTPステータスコードを返す
- クライアントサイドではユーザーに分かりやすいエラーメッセージを表示する

## 2. 命名規則

### ファイル・ディレクトリ
| 対象 | 規則 | 例 |
|------|------|-----|
| コンポーネントディレクトリ | PascalCase | `DrinkCard/` |
| コンポーネントファイル | PascalCase | `DrinkCard.tsx` |
| CSS Modules | PascalCase + `.module.css` | `DrinkCard.module.css` |
| ページファイル | Next.js規約に従う | `page.tsx`, `layout.tsx` |
| Route Handler | Next.js規約に従う | `route.ts` |
| ユーティリティ | camelCase | `image.ts` |
| 型定義ファイル | camelCase | `index.ts` |

### コード
| 対象 | 規則 | 例 |
|------|------|-----|
| 変数・関数 | camelCase | `drinkName`, `handleSubmit` |
| 型・インターフェース | PascalCase | `Drink`, `DrinkFormData` |
| 定数 | UPPER_SNAKE_CASE | `MAX_IMAGE_SIZE` |
| CSSクラス名 | camelCase | `styles.cardContainer` |
| DB カラム名 | snake_case | `drunk_at`, `image_path` |
| API パラメータ | snake_case | `rating_min`, `per_page` |

## 3. スタイリング規約

### CSS Modules
- コンポーネント固有のスタイルは CSS Modules を使用する
- グローバルスタイルは `src/app/globals.css` に記載する
- クラス名は camelCase で記述する

### レスポンシブデザイン
- モバイルファーストで記述する（デフォルトがモバイル、`@media` でPC対応）
- ブレイクポイント:
  - `768px` 以上: タブレット・PC

## 4. テスト規約

### ユニットテスト
- テスト可能な粒度のコンポーネント・関数は、実装時にユニットテストを作成する
- Vitest + React Testing Library を使用
- テストファイルは対象ファイルと同階層に `*.test.ts(x)` として配置

### 統合テスト・E2Eテスト
- 機能が安定した段階で段階的に導入する
- E2Eテスト: Playwright を使用

## 5. Git規約

### ブランチ戦略
- `master` ブランチを本番環境とする
- 作業ブランチは `master` から作成し、作業完了後にマージする

### ブランチ命名規則
```
feature/[説明]    # 新機能
fix/[説明]        # バグ修正
refactor/[説明]   # リファクタリング
docs/[説明]       # ドキュメント
```

例: `feature/drink-list`, `fix/image-upload-heic`

### コミットメッセージ
[Conventional Commits](https://www.conventionalcommits.org/) に従う。

```
<type>: <description>
```

| type | 用途 |
|------|------|
| feat | 新機能 |
| fix | バグ修正 |
| refactor | リファクタリング |
| style | スタイル変更（機能に影響なし） |
| docs | ドキュメント |
| chore | ビルド・設定変更 |

例:
- `feat: add drink list page with infinite scroll`
- `fix: handle HEIC image upload on iOS`
- `docs: update functional design`

### コミット粒度
- 1つの論理的変更につき1コミット
- 動作する状態でコミットする
