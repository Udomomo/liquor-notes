'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Header from '@/components/Header/Header';
import ImageUploader from '@/components/ImageUploader/ImageUploader';
import RatingInput from '@/components/RatingInput/RatingInput';
import Toast from '@/components/Toast/Toast';
import styles from './page.module.css';

export default function DrinkNewPage() {
  const router = useRouter();
  const [rating, setRating] = useState(7.5);
  const [submitting, setSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  async function handleSubmit(e: React.SyntheticEvent<HTMLFormElement>) {
    e.preventDefault();
    const form = e.currentTarget;
    const data = new FormData(form);

    setSubmitting(true);
    setErrorMessage(null);

    try {
      const res = await fetch('/api/drinks', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: data.get('name') as string,
          rating,
          memo: (data.get('memo') as string) || undefined,
          drunk_at: data.get('drunk_at') as string,
        }),
      });

      if (!res.ok) {
        throw new Error('送信に失敗しました');
      }

      router.push('/');
    } catch {
      setErrorMessage('保存に失敗しました。もう一度お試しください。');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <>
      <Header variant="page" title="記録作成" backHref="/" />

      <main className={styles.main}>
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
              defaultValue="2026-02-22"
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
            />
          </div>

          <div className={styles.actions}>
            <button type="submit" className={styles.btnSave} disabled={submitting}>
              {submitting ? '保存中...' : '保存する'}
            </button>
          </div>

        </form>
      </main>

      {errorMessage && (
        <Toast message={errorMessage} onClose={() => setErrorMessage(null)} />
      )}
    </>
  );
}
