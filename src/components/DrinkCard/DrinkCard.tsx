'use client';

import { useState } from 'react';
import Link from 'next/link';
import RatingDisplay from '@/components/RatingDisplay/RatingDisplay';
import styles from './DrinkCard.module.css';
import type { Drink } from '@/types';

type DrinkCardProps = {
  drink: Drink;
  onThumbnailClick?: (drink: Drink) => void;
};

export default function DrinkCard({ drink, onThumbnailClick }: DrinkCardProps) {
  const [imgLoaded, setImgLoaded] = useState(false);

  const thumbContent = (
    <>
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
    </>
  );

  return (
    <article className={styles.card}>
      <div className={styles.cardBody}>
        <div className={styles.thumb}>
          {drink.thumbnailUrl && onThumbnailClick ? (
            <button
              type="button"
              className={styles.thumbButton}
              aria-label={`${drink.name}の写真を拡大表示`}
              onClick={() => onThumbnailClick(drink)}
            >
              {thumbContent}
            </button>
          ) : (
            thumbContent
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
