'use client';

import styles from './ConfirmDialog.module.css';

type ConfirmDialogProps = {
  message: string;
  onCancel: () => void;
  onConfirm: () => void;
};

export default function ConfirmDialog({ message, onCancel, onConfirm }: ConfirmDialogProps) {
  return (
    <div className={styles.overlay} role="dialog" aria-modal="true">
      <div className={styles.dialog}>
        <p className={styles.message}>{message}</p>
        <div className={styles.actions}>
          <button type="button" className={styles.btnCancel} onClick={onCancel}>
            キャンセル
          </button>
          <button type="button" className={styles.btnConfirm} onClick={onConfirm}>
            削除
          </button>
        </div>
      </div>
    </div>
  );
}
