import { NextResponse } from 'next/server';
import { createSupabaseServerClient } from '@/lib/supabase';

type RouteParams = { params: Promise<{ id: string }> };

export async function GET(_request: Request, { params }: RouteParams) {
  const supabase = await createSupabaseServerClient();
  const { data: { user }, error: authError } = await supabase.auth.getUser();
  if (authError || !user) {
    return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
  }
  const userId = user.id;

  const { id } = await params;

  const { data, error } = await supabase
    .from('drinks')
    .select('image_path')
    .eq('id', id)
    .eq('user_id', userId)
    .eq('archived', false)
    .single();

  const imagePath = (data as { image_path: string | null } | null)?.image_path;
  if (error || !imagePath) {
    return NextResponse.json({ error: 'Image not found' }, { status: 404 });
  }

  // image_path はサムネイルのファイル名（<uuid>.<ext>.webp）なので、末尾の .webp を除くと元画像のファイル名になる
  const originalFileName = imagePath.endsWith('.webp')
    ? imagePath.slice(0, -'.webp'.length)
    : imagePath;

  const { data: signed, error: signError } = await supabase.storage
    .from('liquor-notes')
    .createSignedUrl(`${userId}/${originalFileName}`, 60);

  if (signError || !signed?.signedUrl) {
    return NextResponse.json({ error: 'Failed to create signed URL' }, { status: 500 });
  }

  return NextResponse.json({ originalImageUrl: signed.signedUrl });
}
