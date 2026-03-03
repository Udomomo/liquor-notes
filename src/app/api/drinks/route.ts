import { NextResponse } from 'next/server';
import { createSupabaseServerClient } from '@/lib/supabase';
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

export async function GET() {
  const supabase = await createSupabaseServerClient();
  const { data: { user }, error: authError } = await supabase.auth.getUser();
  if (authError || !user) {
    return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
  }
  const userId = user.id;

  const { data, error } = await supabase
    .from('drinks')
    .select('id, name, rating, memo, image_path, drunk_at')
    .eq('user_id', userId)
    .eq('archived', false)
    .order('drunk_at', { ascending: false });

  if (error) {
    return NextResponse.json({ error: 'Failed to fetch drinks' }, { status: 500 });
  }

  const rows = data as DrinkRow[];

  const paths = rows
    .filter((r) => r.image_path)
    .map((r) => `${userId}/${r.image_path}`);

  const signedUrlMap: Record<string, string> = {};
  if (paths.length > 0) {
    const { data: signedUrls } = await supabase.storage
      .from('liquor-notes-thumbnail')
      .createSignedUrls(paths, 60);

    if (signedUrls) {
      for (const item of signedUrls) {
        if (item.signedUrl && item.path) {
          const imagePath = item.path.replace(`${userId}/`, '');
          signedUrlMap[imagePath] = item.signedUrl;
        }
      }
    }
  }

  return NextResponse.json(
    rows.map((row) => ({
      ...toDrink(row),
      thumbnailUrl: row.image_path ? signedUrlMap[row.image_path] : undefined,
    })),
  );
}

export async function POST(request: Request) {
  const supabase = await createSupabaseServerClient();
  const { data: { user }, error: authError } = await supabase.auth.getUser();
  if (authError || !user) {
    return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
  }
  const userId = user.id;

  const body = (await request.json()) as {
    name: string;
    rating: number;
    memo?: string;
    drunk_at: string;
  };

  const { data, error } = await supabase
    .from('drinks')
    .insert({
      user_id: userId,
      name: body.name,
      rating: body.rating,
      memo: body.memo || null,
      drunk_at: body.drunk_at,
    })
    .select('id')
    .single();

  if (error) {
    return NextResponse.json({ error: 'Failed to create drink' }, { status: 500 });
  }

  return NextResponse.json({ id: (data as { id: string }).id }, { status: 201 });
}
