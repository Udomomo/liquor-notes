-- seed.sql
-- liquor-notes シードデータ

WITH auth_user AS (
    INSERT INTO auth.users (
        instance_id,
        role,
        aud,
        email,
        encrypted_password,
        raw_app_meta_data,
        raw_user_meta_data,
        is_super_admin,
        created_at,
        updated_at,
        last_sign_in_at,
        email_confirmed_at,
        confirmation_sent_at,
        confirmation_token,
        recovery_token,
        email_change_token_new,
        email_change
    )
    VALUES (
        '00000000-0000-0000-0000-000000000000',
        'authenticated',
        'authenticated',
        'test@example.com',
        crypt('password123', gen_salt('bf')),
        '{"provider":"email","providers":["email"]}',
        '{}',
        FALSE,
        NOW(), NOW(), NOW(), NOW(), NOW(),
        '', '', '', ''
    )
    RETURNING id
),
public_user AS (
    INSERT INTO public.users (id, created_at, updated_at)
    SELECT id, NOW(), NOW() FROM auth_user
    RETURNING id
)
INSERT INTO public.drinks (user_id, name, rating, memo, image_path, drunk_at, created_at, updated_at)
SELECT
    public_user.id,
    '山崎12年',
    8.5,
    '香りが華やかで、口当たりがまろやか。フルーティーなアロマと上品なスモーキーさが絶妙なバランスを保っている。',
    NULL, '2026-02-10', NOW(), NOW()
FROM public_user
UNION ALL
SELECT
    public_user.id,
    '獺祭 純米大吟醸',
    9.0,
    'フルーティーで飲みやすい。透明感のある甘みと爽やかな酸味が心地よく、後味がすっきりしている。',
    NULL, '2026-02-05', NOW(), NOW()
FROM public_user;
