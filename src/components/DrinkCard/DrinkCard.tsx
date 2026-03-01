'use client';

import { useState } from 'react';
import Link from 'next/link';
import RatingDisplay from '@/components/RatingDisplay/RatingDisplay';
import styles from './DrinkCard.module.css';
import type { Drink } from '@/types';

type DrinkCardProps = {
  drink: Drink;
};

export default function DrinkCard({ drink }: DrinkCardProps) {
  const [imgLoaded, setImgLoaded] = useState(false);

  return (
    <article className={styles.card}>
      <div className={styles.cardBody}>
        <div className={styles.thumb}>
          {drink.thumbnailUrl && (
            // eslint-disable-next-line @next/next/no-img-element
            <img
              src={drink.thumbnailUrl}
              alt={drink.name}
              className={styles.thumbImg}
              style={{ display: imgLoaded ? 'block' : 'none' }}
              onLoad={() => setImgLoaded(true)}
            />
          )}
          {(!drink.thumbnailUrl || !imgLoaded) && (
            <div className={styles.thumbPlaceholder} aria-label={`${drink.name}の画像`} role="img">
              🥃
            </div>
          )}
        </div>
        <div className={styles.info}>
          <div className={styles.infoHeader}>
            <h2 className={styles.name}>{drink.name}</h2>
            <Link href={`/drinks/${drink.id}/edit`} className={styles.btnEdit}>
              編集
            </Link>
          </div>
          <RatingDisplay value={drink.rating} />
          <time className={styles.date} dateTime={drink.drunkAt}>
            {drink.drunkAt}
          </time>
        </div>
      </div>
      {drink.memo && <p className={styles.memo}>{drink.memo}</p>}
    </article>
  );
}
