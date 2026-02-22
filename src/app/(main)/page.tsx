'use client';

import { useState } from 'react';
import Link from 'next/link';
import Header from '@/components/Header/Header';
import DrinkCard from '@/components/DrinkCard/DrinkCard';
import SearchBar from '@/components/SearchBar/SearchBar';
import RatingFilter from '@/components/RatingFilter/RatingFilter';
import type { Drink } from '@/types';
import styles from './page.module.css';

const SAMPLE_DRINKS: Drink[] = [
  {
    id: 1,
    name: '山崎12年',
    rating: 8.5,
    drunkAt: '2026/02/10',
    memo: '香りが華やかで、口当たりがまろやか。フルーティーなアロマと上品なスモーキーさが絶妙なバランスを保っている。',
  },
  {
    id: 2,
    name: '獺祭 純米大吟醸',
    rating: 9.0,
    drunkAt: '2026/02/05',
    memo: 'フルーティーで飲みやすい。透明感のある甘みと爽やかな酸味が心地よく、後味がすっきりしている。',
  },
];

export default function DrinkListPage() {
  const [search, setSearch] = useState('');
  const [ratingFilter, setRatingFilter] = useState('');

  const filtered = SAMPLE_DRINKS.filter((d) => {
    const matchName = d.name.includes(search);
    const matchRating = ratingFilter === '' || d.rating >= Number(ratingFilter);
    return matchName && matchRating;
  });

  return (
    <>
      <Header variant="site" />

      <div className={styles.toolbar}>
        <div className={styles.toolbarTop}>
          <SearchBar value={search} onChange={setSearch} />
        </div>
        <div className={styles.toolbarBottom}>
          <RatingFilter value={ratingFilter} onChange={setRatingFilter} />
          <Link href="/drinks/new" className={styles.btnNew}>
            ＋ 新規
          </Link>
        </div>
      </div>

      <main className={styles.main}>
        <div className={styles.list}>
          {filtered.map((drink) => (
            <DrinkCard key={drink.id} drink={drink} />
          ))}
        </div>
      </main>
    </>
  );
}
