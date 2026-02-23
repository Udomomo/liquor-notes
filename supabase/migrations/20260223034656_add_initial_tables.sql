create sequence "public"."drinks_id_seq";

create sequence "public"."users_id_seq";


  create table "public"."drinks" (
    "id" integer not null default nextval('public.drinks_id_seq'::regclass),
    "user_id" integer not null,
    "name" character varying(255) not null,
    "rating" numeric(3,1) not null,
    "memo" text,
    "image_path" character varying(500),
    "drunk_at" date not null,
    "created_at" timestamp with time zone not null default now(),
    "updated_at" timestamp with time zone not null default now()
      );



  create table "public"."users" (
    "id" integer not null default nextval('public.users_id_seq'::regclass),
    "email" character varying(255) not null,
    "password_hash" character varying(255) not null,
    "created_at" timestamp with time zone not null default now(),
    "updated_at" timestamp with time zone not null default now()
      );


alter sequence "public"."drinks_id_seq" owned by "public"."drinks"."id";

alter sequence "public"."users_id_seq" owned by "public"."users"."id";

CREATE UNIQUE INDEX drinks_pkey ON public.drinks USING btree (id);

CREATE INDEX idx_drinks_user_id ON public.drinks USING btree (user_id);

CREATE INDEX idx_drinks_user_id_drunk_at ON public.drinks USING btree (user_id, drunk_at);

CREATE INDEX idx_drinks_user_id_name ON public.drinks USING btree (user_id, name);

CREATE UNIQUE INDEX users_email_key ON public.users USING btree (email);

CREATE UNIQUE INDEX users_pkey ON public.users USING btree (id);

alter table "public"."drinks" add constraint "drinks_pkey" PRIMARY KEY using index "drinks_pkey";

alter table "public"."users" add constraint "users_pkey" PRIMARY KEY using index "users_pkey";

alter table "public"."drinks" add constraint "drinks_rating_check" CHECK (((rating >= 1.0) AND (rating <= 10.0))) not valid;

alter table "public"."drinks" validate constraint "drinks_rating_check";

alter table "public"."drinks" add constraint "drinks_user_id_fkey" FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE not valid;

alter table "public"."drinks" validate constraint "drinks_user_id_fkey";

alter table "public"."users" add constraint "users_email_key" UNIQUE using index "users_email_key";

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


