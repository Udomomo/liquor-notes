'use client';

import { useState, useEffect, use } from 'react';
import { useRouter } from 'next/navigation';
import Header from '@/components/Header/Header';
import ImageUploader from '@/components/ImageUploader/ImageUploader';
import RatingInput from '@/components/RatingInput/RatingInput';
import Toast from '@/components/Toast/Toast';
import styles from './page.module.css';

type Props = { params: Promise<{ id: string }> };

export default function DrinkEditPage({ params }: Props) {
  const { id } = use(params);
  const router = useRouter();

  const [name, setName] = useState('');
  const [rating, setRating] = useState(7.5);
  const [drunkAt, setDrunkAt] = useState('');
  const [memo, setMemo] = useState('');
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    fetch(`/api/drinks/${id}`)
      .then((res) => {
        if (!res.ok) throw new Error('Not found');
        return res.json();
      })
      .then((drink) => {
        setName(drink.name);
        setRating(drink.rating);
        setDrunkAt(drink.drunkAt.replaceAll('/', '-'));
        setMemo(drink.memo ?? '');
      })
      .catch(() => setErrorMessage('データの取得に失敗しました。'))
      .finally(() => setLoading(false));
  }, [id]);

  async function handleSubmit(e: React.SyntheticEvent<HTMLFormElement>) {
    e.preventDefault();

    setSubmitting(true);
    setErrorMessage(null);

    try {
      const res = await fetch(`/api/drinks/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, rating, memo: memo || undefined, drunk_at: drunkAt }),
      });

      if (!res.ok) throw new Error('Failed to update');

      router.push('/');
    } catch {
      setErrorMessage('保存に失敗しました。もう一度お試しください。');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <>
      <Header variant="page" title="記録編集" backHref="/" />

      <main className={styles.main}>
        <div className={styles.deleteRow}>
          <button type="button" className={styles.btnDelete}>
            削除
          </button>
        </div>

        {loading ? (
          <p className={styles.loadingText}>読み込み中...</p>
        ) : (
          <form className={styles.form} onSubmit={handleSubmit} noValidate>

            <ImageUploader />

            <div className={styles.formGroup}>
              <label className={styles.label} htmlFor="name">
                お酒の名前
                <span className={styles.required} aria-label="必須">*</span>
              </label>
              <input
                type="text"
                id="name"
                name="name"
                className={styles.input}
                placeholder="例：山崎12年"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>

            <div className={styles.formGroup}>
              <label className={styles.label}>
                評価
                <span className={styles.required} aria-label="必須">*</span>
              </label>
              <RatingInput value={rating} onChange={setRating} />
            </div>

            <div className={styles.formGroup}>
              <label className={styles.label} htmlFor="drunk-at">
                飲んだ日付
                <span className={styles.required} aria-label="必須">*</span>
              </label>
              <input
                type="date"
                id="drunk-at"
                name="drunk_at"
                className={styles.input}
                value={drunkAt}
                onChange={(e) => setDrunkAt(e.target.value)}
                required
              />
            </div>

            <div className={styles.formGroup}>
              <label className={styles.label} htmlFor="memo">
                感想メモ
              </label>
              <textarea
                id="memo"
                name="memo"
                className={`${styles.input} ${styles.textarea}`}
                rows={4}
                placeholder="香り、味、余韻など感じたことを..."
                value={memo}
                onChange={(e) => setMemo(e.target.value)}
              />
            </div>

            <div className={styles.actions}>
              <button type="submit" className={styles.btnSave} disabled={submitting}>
                {submitting ? '保存中...' : '保存する'}
              </button>
            </div>

          </form>
        )}
      </main>

      {errorMessage && (
        <Toast message={errorMessage} onClose={() => setErrorMessage(null)} />
      )}
    </>
  );
}
