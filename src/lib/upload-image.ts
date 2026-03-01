import sharp from 'sharp';
import { supabase } from '@/lib/supabase';

const ALLOWED_EXTENSIONS = ['jpeg', 'jpg', 'png'] as const;
const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

function getExtension(filename: string): string {
  return filename.split('.').pop()?.toLowerCase() ?? '';
}

function isAllowedExtension(ext: string): ext is (typeof ALLOWED_EXTENSIONS)[number] {
  return (ALLOWED_EXTENSIONS as readonly string[]).includes(ext);
}

export async function uploadImage(
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
