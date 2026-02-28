import Header from '@/components/Header/Header';
import styles from './page.module.css';

export default function LoginPage() {
  return (
    <>
      <Header variant="auth" />

      <main className={styles.main}>
        <form className={styles.form} action="#" method="post" noValidate>

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
