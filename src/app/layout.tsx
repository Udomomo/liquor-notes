import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'liquor-notes',
  description: '飲んだお酒を写真つきで記録するアプリ',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ja">
      <body>{children}</body>
    </html>
  );
}
