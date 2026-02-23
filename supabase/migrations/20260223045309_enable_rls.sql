alter table "public"."drinks" enable row level security;

alter table "public"."users" enable row level security;

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


