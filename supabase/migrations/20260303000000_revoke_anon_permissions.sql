-- anon ロールの全権限を削除（未認証ユーザーはDBに直接アクセス不可）
revoke all on table "public"."drinks" from "anon";
revoke all on table "public"."users" from "anon";

-- authenticated ロールの TRUNCATE 権限を削除（不要な破壊的権限）
revoke truncate on table "public"."drinks" from "authenticated";
revoke truncate on table "public"."users" from "authenticated";
