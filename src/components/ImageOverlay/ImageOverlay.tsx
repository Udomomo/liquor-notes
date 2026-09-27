'use client';

import { useEffect, useRef, useState } from 'react';
import styles from './ImageOverlay.module.css';

type ImageOverlayProps = {
  drinkId: string;
  alt: string;
  onClose: () => void;
  onError: (message: string) => void;
};

type OriginalImageResponse = {
  originalImageUrl: string;
};

export default function ImageOverlay({ drinkId, alt, onClose, onError }: ImageOverlayProps) {
  const [imageUrl, setImageUrl] = useState<string | null>(null);
  const [imgLoaded, setImgLoaded] = useState(false);

  // 親が毎回新しい関数を渡しても再取得しないよう、onError は ref 経由で参照する
  const onErrorRef = useRef(onError);
  useEffect(() => {
    onErrorRef.current = onError;
  }, [onError]);

  useEffect(() => {
    const controller = new AbortController();

    fetch(`/api/drinks/${encodeURIComponent(drinkId)}/originalImage`, { signal: controller.signal })
      .then((res) => {
        if (!res.ok) throw new Error('Failed to fetch');
        return res.json();
      })
      .then((data: OriginalImageResponse) => setImageUrl(data.originalImageUrl))
      .catch(() => {
        if (controller.signal.aborted) return;
        onErrorRef.current('画像の取得に失敗しました');
      });

    return () => controller.abort();
  }, [drinkId]);

  return (
    <div className={styles.overlay} role="dialog" aria-modal="true" aria-label={alt} onClick={onClose}>
      <button type="button" className={styles.btnClose} aria-label="閉じる" onClick={onClose}>
        ×
      </button>
      {!imgLoaded && <div className={styles.spinner} role="status" aria-label="読み込み中" />}
      {imageUrl && (
        // eslint-disable-next-line @next/next/no-img-element
        <img
          src={imageUrl}
          alt={alt}
          className={styles.image}
          style={{ display: imgLoaded ? 'block' : 'none' }}
          onClick={(e) => e.stopPropagation()}
          onLoad={() => setImgLoaded(true)}
          onError={() => onError('画像の読み込みに失敗しました')}
        />
      )}
    </div>
  );
}
