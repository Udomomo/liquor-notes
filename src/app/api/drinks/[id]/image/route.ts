import { NextResponse } from 'next/server';
import { supabase } from '@/lib/supabase';
import { getAuthenticatedUserId } from '@/lib/get-authenticated-user-id';
import { uploadImage } from '@/lib/upload-image';

type RouteParams = { params: Promise<{ id: string }> };

export async function POST(request: Request, { params }: RouteParams) {
  let userId: string;
  try {
    userId = await getAuthenticatedUserId();
  } catch {
    return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
  }

  const { id } = await params;

  // レビューの所有者確認
  const { data: drink, error: fetchError } = await supabase
    .from('drinks')
    .select('id')
    .eq('id', id)
    .eq('user_id', userId)
    .single();

  if (fetchError || !drink) {
    return NextResponse.json({ error: 'Drink not found' }, { status: 404 });
  }

  const formData = await request.formData();
  const imageFile = formData.get('image') as File | null;

  if (!imageFile || imageFile.size === 0) {
    return NextResponse.json({ error: 'Image is required' }, { status: 400 });
  }

  let imagePath: string;
  try {
    const result = await uploadImage(userId, imageFile);
    imagePath = result.imagePath;
  } catch (err) {
    const e = err as { message: string; status?: number };
    return NextResponse.json({ error: e.message }, { status: e.status ?? 500 });
  }

  const { error: updateError } = await supabase
    .from('drinks')
    .update({ image_path: imagePath, updated_at: new Date().toISOString() })
    .eq('id', id)
    .eq('user_id', userId);

  if (updateError) {
    return NextResponse.json({ error: 'Failed to update drink' }, { status: 500 });
  }

  return NextResponse.json({});
}
