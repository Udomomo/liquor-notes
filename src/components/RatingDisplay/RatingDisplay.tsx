import styles from './RatingDisplay.module.css';

type RatingDisplayProps = {
  value: number;
};

export default function RatingDisplay({ value }: RatingDisplayProps) {
  return (
    <div className={styles.rating} aria-label={`評価 ${value.toFixed(1)}`}>
      <span className={styles.star} aria-hidden="true">★</span>
      <span className={styles.value}>{value.toFixed(1)}</span>
    </div>
  );
}
