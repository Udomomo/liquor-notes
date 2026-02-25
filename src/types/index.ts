export type Drink = {
  id: string;
  name: string;
  rating: number;
  memo?: string;
  thumbnailUrl?: string;
  drunkAt: string; // "YYYY/MM/DD"
};
