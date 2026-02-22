'use client';

import { useState, useRef } from 'react';
import styles from './ImageUploader.module.css';

type ImageUploaderProps = {
  onChange?: (file: File | null) => void;
};

export default function ImageUploader({ onChange }: ImageUploaderProps) {
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0] ?? null;
    if (previewUrl) URL.revokeObjectURL(previewUrl);
    if (file) {
      setPreviewUrl(URL.createObjectURL(file));
    } else {
      setPreviewUrl(null);
    }
    onChange?.(file);
  };

  return (
    <div className={styles.section}>
      <label className={styles.area} htmlFor="image-input">
        {previewUrl ? (
          <div className={styles.preview}>
            {/* eslint-disable-next-line @next/next/no-img-element */}
            <img src={previewUrl} alt="選択した画像" className={styles.previewImg} />
          </div>
        ) : (
          <div className={styles.placeholder}>
            <svg
              className={styles.icon}
              xmlns="http://www.w3.org/2000/svg"
              width="32"
              height="32"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="1.5"
              strokeLinecap="round"
              strokeLinejoin="round"
              aria-hidden="true"
            >
              <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z" />
              <circle cx="12" cy="13" r="4" />
            </svg>
            <span className={styles.text}>写真を選択</span>
            <span className={styles.sub}>またはカメラ撮影</span>
          </div>
        )}
      </label>
      <input
        ref={inputRef}
        type="file"
        id="image-input"
        name="image"
        accept="image/*"
        capture="environment"
        className={styles.hiddenInput}
        onChange={handleChange}
      />
    </div>
  );
}
