# リポジトリ構造定義書

## ディレクトリ構成

```
liquor-notes/
├── .github/                    # GitHub設定
│   └── workflows/              # GitHub Actions
├── .steering/                  # 作業単位のステアリングドキュメント
├── docs/                       # 永続的ドキュメント
├── public/                     # 静的ファイル
├── src/
│   ├── app/                    # Next.js App Router
│   │   ├── (auth)/             # 認証不要のルートグループ
│   │   │   └── login/
│   │   │       ├── page.tsx
│   │   │       └── page.module.css
│   │   ├── (main)/             # 認証必須のルートグループ
│   │   │   ├── page.tsx        # 記録一覧画面（ホーム）
│   │   │   ├── page.module.css
│   │   │   └── drinks/
│   │   │       ├── new/
│   │   │       │   ├── page.tsx
│   │   │       │   └── page.module.css
│   │   │       └── [id]/
│   │   │           └── edit/
│   │   │               ├── page.tsx
│   │   │               └── page.module.css
│   │   ├── api/                # Route Handlers
│   │   │   ├── auth/
│   │   │   │   ├── login/
│   │   │   │   │   └── route.ts
│   │   │   │   └── logout/
│   │   │   │       └── route.ts
│   │   │   └── drinks/
│   │   │       ├── route.ts    # GET（一覧）, POST（作成）
│   │   │       └── [id]/
│   │   │           └── route.ts # PUT（更新）, DELETE（削除）
│   │   ├── layout.tsx          # ルートレイアウト
│   │   └── globals.css         # グローバルスタイル
│   ├── components/             # 共通UIコンポーネント
│   │   ├── Header/
│   │   │   ├── Header.tsx
│   │   │   └── Header.module.css
│   │   ├── DrinkCard/
│   │   │   ├── DrinkCard.tsx
│   │   │   └── DrinkCard.module.css
│   │   ├── RatingInput/
│   │   │   ├── RatingInput.tsx
│   │   │   └── RatingInput.module.css
│   │   ├── RatingDisplay/
│   │   │   ├── RatingDisplay.tsx
│   │   │   └── RatingDisplay.module.css
│   │   ├── ImageUploader/
│   │   │   ├── ImageUploader.tsx
│   │   │   └── ImageUploader.module.css
│   │   ├── ImageOverlay/
│   │   │   ├── ImageOverlay.tsx
│   │   │   └── ImageOverlay.module.css
│   │   ├── SearchBar/
│   │   │   ├── SearchBar.tsx
│   │   │   └── SearchBar.module.css
│   │   ├── RatingFilter/
│   │   │   ├── RatingFilter.tsx
│   │   │   └── RatingFilter.module.css
│   │   └── ConfirmDialog/
│   │       ├── ConfirmDialog.tsx
│   │       └── ConfirmDialog.module.css
│   ├── lib/                    # ユーティリティ・設定
│   │   ├── supabase/
│   │   │   ├── client.ts       # ブラウザ用Supabaseクライアント
│   │   │   └── server.ts       # サーバー用Supabaseクライアント
│   │   └── image.ts            # 画像処理（リサイズ・サムネイル生成・HEIC変換）
│   ├── types/                  # 型定義
│   │   └── index.ts            # Drink, User 等の型
│   └── middleware.ts           # 認証ミドルウェア（未認証リダイレクト）
├── supabase/                   # Supabase設定
│   ├── migrations/             # DBマイグレーション
│   └── seed.sql                # 初期ユーザー作成用シード
├── .env.local                  # 環境変数（Git管理外）
├── .env.example                # 環境変数テンプレート
├── .gitignore
├── CLAUDE.md
├── next.config.ts
├── tsconfig.json
├── eslint.config.mjs
├── package.json
└── pnpm-lock.yaml
```

## ディレクトリの役割

| ディレクトリ | 役割 |
|-------------|------|
| `src/app/` | Next.js App Router のページとAPIルート |
| `src/app/(auth)/` | 認証不要のルートグループ（ログイン画面） |
| `src/app/(main)/` | 認証必須のルートグループ（一覧・作成・編集） |
| `src/app/api/` | Route Handlers（REST APIエンドポイント） |
| `src/components/` | 再利用可能なUIコンポーネント |
| `src/lib/` | Supabaseクライアント、画像処理などのユーティリティ |
| `src/types/` | TypeScript型定義 |
| `supabase/` | マイグレーション、シードデータ |
| `docs/` | 永続的な設計ドキュメント |
| `.steering/` | 作業単位のステアリングドキュメント |

## ファイル配置ルール

### コンポーネント
- 1コンポーネント = 1ディレクトリ（`ComponentName/ComponentName.tsx` + `ComponentName.module.css`）
- ページ固有のスタイルは `page.module.css` としてページと同階層に配置

### API Route Handlers
- RESTリソースに対応するディレクトリ構造
- 1ファイルに関連するHTTPメソッドをまとめる（例: `route.ts` に GET と POST）

### スタイル
- グローバルスタイルは `src/app/globals.css`
- コンポーネント固有のスタイルは CSS Modules（`.module.css`）

### 環境変数
- `.env.local` は Git 管理外（`.gitignore` に含める）
- `.env.example` にキー名のみ記載しテンプレートとして管理
