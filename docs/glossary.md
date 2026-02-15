# ユビキタス言語定義

## 1. ドメイン用語

| 日本語 | 英語 | コード上の命名 | 説明 |
|--------|------|---------------|------|
| お酒の記録 | Drink | `Drink` / `drink` | ユーザーが飲んだお酒の1件の記録。写真・名前・評価・感想メモ・飲んだ日付を含む |
| お酒の名前 | Drink Name | `name` | 記録するお酒の名称 |
| 評価 | Rating | `rating` | お酒に対する評価。1.0〜10.0の0.1刻み |
| 感想メモ | Memo | `memo` | お酒に対する自由記述の感想 |
| 飲んだ日付 | Drunk Date | `drunkAt` / `drunk_at` | お酒を飲んだ日付（DATE型） |

## 2. 画像関連用語

| 日本語 | 英語 | コード上の命名 | 説明 |
|--------|------|---------------|------|
| 元画像 | Original Image | `image` | ユーザーがアップロードした画像（リサイズ・圧縮後） |
| サムネイル | Thumbnail | `thumbnail` | 元画像から中央クロップで生成した正方形画像 |
| 画像パス | Image Path | `imagePath` / `image_path` | Supabase Storage 上の画像ファイルパス |
| 画像URL | Image URL | `imageUrl` / `image_url` | クライアントに返す画像アクセス用URL |
| サムネイルURL | Thumbnail URL | `thumbnailUrl` / `thumbnail_url` | クライアントに返すサムネイルアクセス用URL |

## 3. 認証関連用語

| 日本語 | 英語 | コード上の命名 | 説明 |
|--------|------|---------------|------|
| ユーザー | User | `User` / `user` | サービスの利用者（本サービスでは1名のみ） |
| ログイン | Login | `login` | メールアドレスとパスワードによる認証 |
| ログアウト | Logout | `logout` | セッションの終了 |
| セッション | Session | `session` | Supabase Auth が管理する認証状態 |

## 4. UI/UX用語

| 日本語 | 英語 | コード上の命名 | 説明 |
|--------|------|---------------|------|
| 記録一覧画面 | Drink List Page | `DrinkListPage` | すべての記録を表示するホーム画面 |
| 記録作成画面 | New Drink Page | `DrinkNewPage` | 新しい記録を作成する画面 |
| 記録編集画面 | Edit Drink Page | `DrinkEditPage` | 既存の記録を編集する画面 |
| ログイン画面 | Login Page | `LoginPage` | 認証を行う画面 |
| カード | Card | `DrinkCard` | 一覧画面における1件の記録の表示単位 |
| オーバーレイ | Overlay | `ImageOverlay` | サムネイルタップ時に元画像を全画面表示するUI |
| 無限スクロール | Infinite Scroll | - | スクロール末尾で自動的に次のデータを読み込むUI |
| 検索 | Search | `search` / `q` | お酒の名前による部分一致検索 |
| フィルタ | Filter | `filter` | 評価による記録の絞り込み |

## 5. 命名の使い分け

### camelCase と snake_case
| 場面 | 規則 | 例 |
|------|------|-----|
| TypeScript 変数・プロパティ | camelCase | `drunkAt`, `imageUrl` |
| DB カラム名 | snake_case | `drunk_at`, `image_url` |
| API リクエスト/レスポンス | snake_case | `drunk_at`, `rating_min` |
| CSS クラス名 | camelCase | `drinkCard`, `cardContainer` |
