'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import Header from '@/components/Header/Header';
import DrinkCard from '@/components/DrinkCard/DrinkCard';
import SearchBar from '@/components/SearchBar/SearchBar';
import RatingFilter from '@/components/RatingFilter/RatingFilter';
import type { Drink } from '@/types';
import styles from './page.module.css';

export default function DrinkListPage() {
  const [drinks, setDrinks] = useState<Drink[]>([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [ratingFilter, setRatingFilter] = useState('');

  useEffect(() => {
    fetch('/api/drinks')
      .then((res) => res.json())
      .then((data: Drink[]) => setDrinks(data))
      .finally(() => setLoading(false));
  }, []);

  const filtered = drinks.filter((d) => {
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
          {loading ? (
            <p className={styles.status}>読み込み中...</p>
          ) : filtered.length === 0 ? (
            <p className={styles.status}>記録がありません</p>
          ) : (
            filtered.map((drink) => (
              <DrinkCard key={drink.id} drink={drink} />
            ))
          )}
        </div>
      </main>
    </>
  );
}
