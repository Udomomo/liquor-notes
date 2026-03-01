alter table "public"."drinks" add column "archived" boolean not null default false;

grant delete on table "public"."drinks" to "postgres";

grant insert on table "public"."drinks" to "postgres";

grant references on table "public"."drinks" to "postgres";

grant select on table "public"."drinks" to "postgres";

grant trigger on table "public"."drinks" to "postgres";

grant truncate on table "public"."drinks" to "postgres";

grant update on table "public"."drinks" to "postgres";

grant delete on table "public"."users" to "postgres";

grant insert on table "public"."users" to "postgres";

grant references on table "public"."users" to "postgres";

grant select on table "public"."users" to "postgres";

grant trigger on table "public"."users" to "postgres";

grant truncate on table "public"."users" to "postgres";

grant update on table "public"."users" to "postgres";


  create policy "Give users access to own folder 1hictnb_0"
  on "storage"."objects"
  as permissive
  for insert
  to authenticated
with check (((bucket_id = 'liquor-notes-thumbnail'::text) AND (( SELECT (auth.uid())::text AS uid) = (storage.foldername(name))[1])));



  create policy "Give users access to own folder 1hictnb_1"
  on "storage"."objects"
  as permissive
  for select
  to authenticated
using (((bucket_id = 'liquor-notes-thumbnail'::text) AND (( SELECT (auth.uid())::text AS uid) = (storage.foldername(name))[1])));



  create policy "Give users access to own folder 1n03260_0"
  on "storage"."objects"
  as permissive
  for select
  to authenticated
using (((bucket_id = 'liquor-notes'::text) AND (( SELECT (auth.uid())::text AS uid) = (storage.foldername(name))[1])));



  create policy "Give users access to own folder 1n03260_1"
  on "storage"."objects"
  as permissive
  for insert
  to authenticated
with check (((bucket_id = 'liquor-notes'::text) AND (( SELECT (auth.uid())::text AS uid) = (storage.foldername(name))[1])));



