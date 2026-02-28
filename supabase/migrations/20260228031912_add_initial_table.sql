
  create table "public"."drinks" (
    "id" uuid not null default gen_random_uuid(),
    "user_id" uuid not null,
    "name" character varying(255) not null,
    "rating" numeric(3,1) not null,
    "memo" text,
    "image_path" character varying(500),
    "drunk_at" date not null,
    "created_at" timestamp with time zone not null default now(),
    "updated_at" timestamp with time zone not null default now()
      );


alter table "public"."drinks" enable row level security;


  create table "public"."users" (
    "id" uuid not null,
    "created_at" timestamp with time zone not null default now(),
    "updated_at" timestamp with time zone not null default now()
      );


alter table "public"."users" enable row level security;

CREATE UNIQUE INDEX drinks_pkey ON public.drinks USING btree (id);

CREATE INDEX idx_drinks_user_id ON public.drinks USING btree (user_id);

CREATE INDEX idx_drinks_user_id_drunk_at ON public.drinks USING btree (user_id, drunk_at);

CREATE INDEX idx_drinks_user_id_name ON public.drinks USING btree (user_id, name);

CREATE UNIQUE INDEX users_pkey ON public.users USING btree (id);

alter table "public"."drinks" add constraint "drinks_pkey" PRIMARY KEY using index "drinks_pkey";

alter table "public"."users" add constraint "users_pkey" PRIMARY KEY using index "users_pkey";

alter table "public"."drinks" add constraint "drinks_rating_check" CHECK (((rating >= 1.0) AND (rating <= 10.0))) not valid;

alter table "public"."drinks" validate constraint "drinks_rating_check";

alter table "public"."drinks" add constraint "drinks_user_id_fkey" FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE not valid;

alter table "public"."drinks" validate constraint "drinks_user_id_fkey";

alter table "public"."users" add constraint "fk_user_id" FOREIGN KEY (id) REFERENCES auth.users(id) not valid;

alter table "public"."users" validate constraint "fk_user_id";

grant delete on table "public"."drinks" to "anon";

grant insert on table "public"."drinks" to "anon";

grant references on table "public"."drinks" to "anon";

grant select on table "public"."drinks" to "anon";

grant trigger on table "public"."drinks" to "anon";

grant truncate on table "public"."drinks" to "anon";

grant update on table "public"."drinks" to "anon";

grant delete on table "public"."drinks" to "authenticated";

grant insert on table "public"."drinks" to "authenticated";

grant references on table "public"."drinks" to "authenticated";

grant select on table "public"."drinks" to "authenticated";

grant trigger on table "public"."drinks" to "authenticated";

grant truncate on table "public"."drinks" to "authenticated";

grant update on table "public"."drinks" to "authenticated";

grant delete on table "public"."drinks" to "postgres";

grant insert on table "public"."drinks" to "postgres";

grant references on table "public"."drinks" to "postgres";

grant select on table "public"."drinks" to "postgres";

grant trigger on table "public"."drinks" to "postgres";

grant truncate on table "public"."drinks" to "postgres";

grant update on table "public"."drinks" to "postgres";

grant delete on table "public"."drinks" to "service_role";

grant insert on table "public"."drinks" to "service_role";

grant references on table "public"."drinks" to "service_role";

grant select on table "public"."drinks" to "service_role";

grant trigger on table "public"."drinks" to "service_role";

grant truncate on table "public"."drinks" to "service_role";

grant update on table "public"."drinks" to "service_role";

grant delete on table "public"."users" to "anon";

grant insert on table "public"."users" to "anon";

grant references on table "public"."users" to "anon";

grant select on table "public"."users" to "anon";

grant trigger on table "public"."users" to "anon";

grant truncate on table "public"."users" to "anon";

grant update on table "public"."users" to "anon";

grant delete on table "public"."users" to "authenticated";

grant insert on table "public"."users" to "authenticated";

grant references on table "public"."users" to "authenticated";

grant select on table "public"."users" to "authenticated";

grant trigger on table "public"."users" to "authenticated";

grant truncate on table "public"."users" to "authenticated";

grant update on table "public"."users" to "authenticated";

grant delete on table "public"."users" to "postgres";

grant insert on table "public"."users" to "postgres";

grant references on table "public"."users" to "postgres";

grant select on table "public"."users" to "postgres";

grant trigger on table "public"."users" to "postgres";

grant truncate on table "public"."users" to "postgres";

grant update on table "public"."users" to "postgres";

grant delete on table "public"."users" to "service_role";

grant insert on table "public"."users" to "service_role";

grant references on table "public"."users" to "service_role";

grant select on table "public"."users" to "service_role";

grant trigger on table "public"."users" to "service_role";

grant truncate on table "public"."users" to "service_role";

grant update on table "public"."users" to "service_role";


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



