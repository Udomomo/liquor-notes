'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Header from '@/components/Header/Header';
import Toast from '@/components/Toast/Toast';
import { supabaseBrowser } from '@/lib/supabase-browser';
import styles from './page.module.css';

export default function LoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  async function handleSubmit(e: React.SyntheticEvent<HTMLFormElement>) {
    e.preventDefault();
    setErrorMessage(null);

    const { error } = await supabaseBrowser.auth.signInWithPassword({ email, password });
    if (error) {
      setErrorMessage('メールアドレスかパスワードが誤っています');
      return;
    }
    router.push('/');
  }

  return (
    <>
      <Header variant="auth" />

      {errorMessage && (
        <Toast message={errorMessage} onClose={() => setErrorMessage(null)} />
      )}

      <main className={styles.main}>
        <form className={styles.form} onSubmit={handleSubmit} noValidate>

          <div className={styles.formGroup}>
            <label className={styles.label} htmlFor="email">
              メールアドレス
            </label>
            <input
              type="email"
              id="email"
              name="email"
              className={styles.input}
              placeholder="example@email.com"
              autoComplete="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className={styles.formGroup}>
            <label className={styles.label} htmlFor="password">
              パスワード
            </label>
            <input
              type="password"
              id="password"
              name="password"
              className={styles.input}
              placeholder="パスワードを入力"
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <div className={styles.actions}>
            <button type="submit" className={styles.btnLogin}>
              ログイン
            </button>
          </div>

        </form>
      </main>
    </>
  );
}
