import { NextResponse } from 'next/server';
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

type SearchRequestBody = {
  name?: string;
  rate?: {
    minimum?: string;
  };
};

export async function POST(request: Request) {
  if (request.headers.get('Content-Type') !== 'application/json') {
    return NextResponse.json({ error: 'Content-Type must be application/json' }, { status: 415 });
  }

  let userId: string;
  try {
    userId = await getAuthenticatedUserId();
  } catch {
    return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
  }

  const body = await request.json() as SearchRequestBody;

  const name = body.name ?? '';
  const minimumRaw = body.rate?.minimum ?? '';

  let minimumRating: number | undefined;
  if (minimumRaw !== '') {
    const parsed = Number(minimumRaw);
    if (Number.isNaN(parsed)) {
      return NextResponse.json({ error: 'rate.minimum must be a number' }, { status: 400 });
    }
    minimumRating = parsed;
  }

  let query = supabase
    .from('drinks')
    .select('id, name, rating, memo, image_path, drunk_at')
    .eq('user_id', userId)
    .order('drunk_at', { ascending: false });

  if (name !== '') {
    query = query.ilike('name', `%${name}%`);
  }
  if (minimumRating !== undefined) {
    query = query.gte('rating', minimumRating);
  }

  const { data, error } = await query;

  if (error) {
    return NextResponse.json({ error: 'Failed to search drinks' }, { status: 500 });
  }

  return NextResponse.json((data as DrinkRow[]).map(toDrink));
}
