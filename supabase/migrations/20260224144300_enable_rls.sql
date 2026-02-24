alter table "public"."drinks" alter column "id" set default gen_random_uuid();

alter table "public"."drinks" alter column "id" set data type uuid using "id"::uuid;

alter table "public"."drinks" alter column "user_id" set data type uuid using "user_id"::uuid;

alter table "public"."users" alter column "id" set default gen_random_uuid();

alter table "public"."users" alter column "id" set data type uuid using "id"::uuid;

drop sequence if exists "public"."drinks_id_seq";

drop sequence if exists "public"."users_id_seq";

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


  create policy "Enable delete for users based on user_id"
  on "public"."drinks"
  as permissive
  for delete
  to authenticated
using ((( SELECT auth.uid() AS uid) = user_id));



  create policy "Enable insert for users based on user_id"
  on "public"."drinks"
  as permissive
  for insert
  to authenticated
with check ((( SELECT auth.uid() AS uid) = user_id));



  create policy "Enable users to view their own data only"
  on "public"."drinks"
  as permissive
  for select
  to authenticated
using ((( SELECT auth.uid() AS uid) = user_id));



  create policy "Policy with table joins"
  on "public"."drinks"
  as permissive
  for update
  to authenticated
using ((auth.uid() = user_id))
with check ((auth.uid() = user_id));



  create policy "Enable users to view their own data only"
  on "public"."users"
  as permissive
  for select
  to authenticated
using ((( SELECT auth.uid() AS uid) = id));



