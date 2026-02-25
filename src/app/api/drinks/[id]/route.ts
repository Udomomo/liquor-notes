import { NextResponse } from 'next/server';
import { supabase } from '@/lib/supabase';
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

// 暫定: 認証実装後にセッションユーザーのIDを使用
async function getCurrentUserId(): Promise<string> {
  const { data, error } = await supabase
    .from('users')
    .select('id')
    .eq('email', 'test@example.com')
    .single();

  if (error || !data) throw new Error('Seed user not found');
  return data.id as string;
}

type RouteParams = { params: Promise<{ id: string }> };

export async function GET(_request: Request, { params }: RouteParams) {
  const [{ id }, userId] = await Promise.all([params, getCurrentUserId()]);

  const { data, error } = await supabase
    .from('drinks')
    .select('id, name, rating, memo, image_path, drunk_at')
    .eq('id', id)
    .eq('user_id', userId)
    .single();

  if (error || !data) {
    return NextResponse.json({ error: 'Drink not found' }, { status: 404 });
  }

  return NextResponse.json(toDrink(data as DrinkRow));
}

export async function PUT(request: Request, { params }: RouteParams) {
  const [{ id }, userId] = await Promise.all([params, getCurrentUserId()]);

  const body = await request.json() as {
    name: string;
    rating: number;
    memo?: string;
    drunk_at: string;
  };

  const { data, error } = await supabase
    .from('drinks')
    .update({
      name: body.name,
      rating: body.rating,
      memo: body.memo || null,
      drunk_at: body.drunk_at,
      updated_at: new Date().toISOString(),
    })
    .eq('id', id)
    .eq('user_id', userId)
    .select('id, name, rating, memo, image_path, drunk_at')
    .single();

  if (error || !data) {
    return NextResponse.json({ error: 'Failed to update drink' }, { status: 500 });
  }

  return NextResponse.json(toDrink(data as DrinkRow));
}

export async function DELETE(_request: Request, { params }: RouteParams) {
  const [{ id }, userId] = await Promise.all([params, getCurrentUserId()]);

  const { error } = await supabase
    .from('drinks')
    .delete()
    .eq('id', id)
    .eq('user_id', userId);

  if (error) {
    return NextResponse.json({ error: 'Failed to delete drink' }, { status: 500 });
  }

  return new NextResponse(null, { status: 204 });
}
