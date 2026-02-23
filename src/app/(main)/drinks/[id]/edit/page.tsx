'use client';

import Header from '@/components/Header/Header';
import ImageUploader from '@/components/ImageUploader/ImageUploader';
import RatingInput from '@/components/RatingInput/RatingInput';
import styles from './page.module.css';

export default function DrinkEditPage() {
  return (
    <>
      <Header variant="page" title="記録編集" backHref="/" />

      <main className={styles.main}>
        <div className={styles.deleteRow}>
          <button type="button" className={styles.btnDelete}>
            削除
          </button>
        </div>

        <form className={styles.form} action="#" method="post" encType="multipart/form-data" noValidate>

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
            <RatingInput />
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
            <button type="submit" className={styles.btnSave}>
              保存する
            </button>
          </div>

        </form>
      </main>
    </>
  );
}
