'use client';

import { useState } from 'react';
import RatingDisplay from '@/components/RatingDisplay/RatingDisplay';
import styles from './RatingInput.module.css';

type RatingInputProps = {
  value?: number;
  onChange?: (value: number) => void;
};

export default function RatingInput({ value: initialValue = 7.5, onChange }: RatingInputProps) {
  const [value, setValue] = useState(initialValue);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const next = parseFloat(e.target.value);
    setValue(next);
    onChange?.(next);
  };

  return (
    <div className={styles.wrap}>
      <div className={styles.row}>
        <span className={styles.edge}>1.0</span>
        <input
          type="range"
          id="rating"
          name="rating"
          className={styles.slider}
          min="1"
          max="10"
          step="0.1"
          value={value}
          aria-label="評価"
          onChange={handleChange}
        />
        <span className={styles.edge}>10.0</span>
      </div>
      <div className={styles.displayRow}>
        <RatingDisplay value={value} />
      </div>
    </div>
  );
}
