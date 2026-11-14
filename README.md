# Liquor Notes
お酒のレビューを記録するWebアプリケーションです。Next.js + Vercel + Supabaseで構築されています。

# 開発方法
- devcontainerを使用します。VS Codeでレポジトリを開き、devcontainerを起動してください。
- 原則としてsupabaseの設定やデータスキーマはsupabaseディレクトリ内で管理し、supabase CLIを使用してremoteにpushする運用です。remoteでの直接の変更は可能な限り避けてください。
  - remoteとの同期の際は `supabase project link -p <project-ref>` を実行します。project-refはsupabaseプロジェクトのURLの一部です。
  - `supabase db push` でローカルのスキーマをremoteに反映させます。
  - `supabase config push` で設定変更をremoteに反映させます。

# デプロイ
- masterブランチが更新されると自動的にprd環境にデプロイされます。
- featureブランチが更新されると自動的にstg環境にデプロイされます。
  - stg環境は、VercelはPreviewを利用、Supabaseはstg用のプロジェクトを使用します。
