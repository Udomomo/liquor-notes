'use client';

import styles from './RatingFilter.module.css';

type RatingFilterProps = {
  value: string;
  onChange: (value: string) => void;
};

export default function RatingFilter({ value, onChange }: RatingFilterProps) {
  return (
    <div className={styles.group}>
      <label className={styles.label} htmlFor="rating-filter">
        評価:
      </label>
      <select
        id="rating-filter"
        className={styles.select}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        aria-label="評価でフィルタ"
      >
        <option value="">すべて</option>
        <option value="9">9.0 以上</option>
        <option value="8">8.0 以上</option>
        <option value="7">7.0 以上</option>
        <option value="6">6.0 以上</option>
      </select>
    </div>
  );
}
