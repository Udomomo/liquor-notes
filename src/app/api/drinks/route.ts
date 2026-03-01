import { NextResponse } from 'next/server';
import sharp from 'sharp';
import { supabase } from '@/lib/supabase';
import { getAuthenticatedUserId } from '@/lib/get-authenticated-user-id';
import type { Drink } from '@/types';

type DrinkRow = {
  id: string;
  name: string;
  rating: number;
  memo: string | null;
  image_path: string | null;
  drunk_at: string;
};

function toDrink(row: DrinkRow): Drink {
  return {
    id: row.id,
    name: row.name,
    rating: row.rating,
    memo: row.memo ?? undefined,
    thumbnailUrl: row.image_path ?? undefined,
    drunkAt: row.drunk_at.replaceAll('-', '/'),
  };
}

const ALLOWED_EXTENSIONS = ['jpeg', 'jpg', 'png'] as const;
const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

function getExtension(filename: string): string {
  return filename.split('.').pop()?.toLowerCase() ?? '';
}

function isAllowedExtension(ext: string): ext is (typeof ALLOWED_EXTENSIONS)[number] {
  return (ALLOWED_EXTENSIONS as readonly string[]).includes(ext);
}

async function uploadImage(
  userId: string,
  file: File,
): Promise<{ imagePath: string }> {
  const ext = getExtension(file.name);

  if (!isAllowedExtension(ext)) {
    throw Object.assign(new Error('Invalid file type'), { status: 400 });
  }
  if (file.size > MAX_FILE_SIZE) {
    throw Object.assign(new Error('File too large'), { status: 400 });
  }

  const imageUuid = crypto.randomUUID();
  const originalBuffer = Buffer.from(await file.arrayBuffer());
  const originalPath = `${userId}/${imageUuid}.${ext}`;
  const thumbnailPath = `${userId}/${imageUuid}.${ext}.webp`;

  // 元画像をアップロード
  const { error: originalError } = await supabase.storage
    .from('liquor-notes')
    .upload(originalPath, originalBuffer, { contentType: file.type, upsert: false });
  if (originalError) throw Object.assign(new Error('Failed to upload image'), { status: 500 });

  // sharp でサムネイル生成
  let thumbnailBuffer: Buffer;
  try {
    thumbnailBuffer = await sharp(originalBuffer)
      .resize(80, 80, { fit: 'cover' })
      .webp()
      .toBuffer();
  } catch {
    throw Object.assign(new Error('Failed to process image'), { status: 500 });
  }

  // サムネイルをアップロード
  const { error: thumbnailError } = await supabase.storage
    .from('liquor-notes-thumbnail')
    .upload(thumbnailPath, thumbnailBuffer, { contentType: 'image/webp', upsert: false });
  if (thumbnailError) throw Object.assign(new Error('Failed to upload thumbnail'), { status: 500 });

  return { imagePath: `${imageUuid}.${ext}.webp` };
}

async function buildSignedUrlMap(
  userId: string,
  rows: DrinkRow[],
): Promise<Record<string, string>> {
  const paths = rows
    .filter((r) => r.image_path)
    .map((r) => `${userId}/${r.image_path}`);

  if (paths.length === 0) return {};

  const { data } = await supabase.storage
    .from('liquor-notes-thumbnail')
    .createSignedUrls(paths, 60);

  if (!data) return {};

  const map: Record<string, string> = {};
  for (const item of data) {
    if (item.signedUrl && item.path) {
      // path は "<user_id>/<image_path>" なのでファイル名部分を key にする
      const imagePath = item.path.replace(`${userId}/`, '');
      map[imagePath] = item.signedUrl;
    }
  }
  return map;
}

export async function GET() {
  let userId: string;
  try {
    userId = await getAuthenticatedUserId();
  } catch {
    return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
  }

  const { data, error } = await supabase
    .from('drinks')
    .select('id, name, rating, memo, image_path, drunk_at')
    .eq('user_id', userId)
    .order('drunk_at', { ascending: false });

  if (error) {
    return NextResponse.json({ error: 'Failed to fetch drinks' }, { status: 500 });
  }

  const rows = data as DrinkRow[];
  const signedUrlMap = await buildSignedUrlMap(userId, rows);

  return NextResponse.json(
    rows.map((row) => ({
      ...toDrink(row),
      thumbnailUrl: row.image_path ? signedUrlMap[row.image_path] : undefined,
    })),
  );
}

export async function POST(request: Request) {
  let userId: string;
  try {
    userId = await getAuthenticatedUserId();
  } catch {
    return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
  }

  const formData = await request.formData();
  const name = formData.get('name') as string;
  const rating = Number(formData.get('rating'));
  const memo = (formData.get('memo') as string) || null;
  const drunk_at = formData.get('drunk_at') as string;
  const imageFile = formData.get('image') as File | null;

  let imagePath: string | null = null;

  if (imageFile && imageFile.size > 0) {
    try {
      const result = await uploadImage(userId, imageFile);
      imagePath = result.imagePath;
    } catch (err) {
      const e = err as { message: string; status?: number };
      const status = e.status ?? 500;
      return NextResponse.json({ error: e.message }, { status });
    }
  }

  const { data, error } = await supabase
    .from('drinks')
    .insert({
      user_id: userId,
      name,
      rating,
      memo,
      drunk_at,
      image_path: imagePath,
    })
    .select('id, name, rating, memo, image_path, drunk_at')
    .single();

  if (error) {
    return NextResponse.json({ error: 'Failed to create drink' }, { status: 500 });
  }

  return NextResponse.json(toDrink(data as DrinkRow), { status: 201 });
}
