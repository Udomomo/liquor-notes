drop extension if exists "pg_net";

alter table "public"."drinks" drop constraint "drinks_user_id_fkey";

alter table "public"."drinks" add constraint "drinks_user_id_fkey" FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE not valid;

alter table "public"."drinks" validate constraint "drinks_user_id_fkey";


