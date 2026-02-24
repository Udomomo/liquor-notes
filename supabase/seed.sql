-- seed.sql
-- liquor-notes シードデータ

INSERT INTO users (id, email, password_hash, created_at, updated_at)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'test@example.com',
    '$2b$12$dummyhashforseeddataonlydonotuseinproduction000000000',
    NOW(),
    NOW()
);

INSERT INTO drinks (id, user_id, name, rating, memo, image_path, drunk_at, created_at, updated_at)
VALUES
    (
        '00000000-0000-0000-0001-000000000001',
        '00000000-0000-0000-0000-000000000001',
        '山崎12年',
        8.5,
        '香りが華やかで、口当たりがまろやか。フルーティーなアロマと上品なスモーキーさが絶妙なバランスを保っている。',
        NULL,
        '2026-02-10',
        NOW(),
        NOW()
    ),
    (
        '00000000-0000-0000-0001-000000000002',
        '00000000-0000-0000-0000-000000000001',
        '獺祭 純米大吟醸',
        9.0,
        'フルーティーで飲みやすい。透明感のある甘みと爽やかな酸味が心地よく、後味がすっきりしている。',
        NULL,
        '2026-02-05',
        NOW(),
        NOW()
    );
