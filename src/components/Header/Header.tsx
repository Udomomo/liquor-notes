'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { supabaseBrowser } from '@/lib/supabase-browser';
import styles from './Header.module.css';

type HeaderProps =
  | { variant: 'site' }
  | { variant: 'auth' }
  | { variant: 'page'; title: string; backHref: string };

export default function Header(props: HeaderProps) {
  const router = useRouter();

  async function handleLogout() {
    await supabaseBrowser.auth.signOut();
    router.push('/login');
  }

  return (
    <header className={styles.header}>
      <div className={styles.inner}>
        {props.variant === 'site' ? (
          <>
            <h1 className={styles.logo}>liquor-notes</h1>
            <button className={styles.btnLogout} onClick={handleLogout}>ログアウト</button>
          </>
        ) : props.variant === 'auth' ? (
          <h1 className={styles.logo}>liquor-notes</h1>
        ) : (
          <>
            <Link href={props.backHref} className={styles.btnBack}>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2.2"
                strokeLinecap="round"
                strokeLinejoin="round"
                aria-hidden="true"
              >
                <polyline points="15 18 9 12 15 6" />
              </svg>
              戻る
            </Link>
            <h1 className={styles.pageTitle}>{props.title}</h1>
            <span className={styles.spacer} aria-hidden="true" />
          </>
        )}
      </div>
    </header>
  );
}
