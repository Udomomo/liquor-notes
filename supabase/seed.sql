-- seed.sql
-- liquor-notes シードデータ

INSERT INTO users (email, password_hash, created_at, updated_at)
VALUES (
    'test@example.com',
    '$2b$12$dummyhashforseeddataonlydonotuseinproduction000000000',
    NOW(),
    NOW()
);

INSERT INTO drinks (user_id, name, rating, memo, image_path, drunk_at, created_at, updated_at)
VALUES
    (
        1,
        '山崎12年',
        8.5,
        '香りが華やかで、口当たりがまろやか。フルーティーなアロマと上品なスモーキーさが絶妙なバランスを保っている。',
        NULL,
        '2026-02-10',
        NOW(),
        NOW()
    ),
    (
        1,
        '獺祭 純米大吟醸',
        9.0,
        'フルーティーで飲みやすい。透明感のある甘みと爽やかな酸味が心地よく、後味がすっきりしている。',
        NULL,
        '2026-02-05',
        NOW(),
        NOW()
    );
