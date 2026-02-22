export type Drink = {
  id: number;
  name: string;
  rating: number;
  memo?: string;
  thumbnailUrl?: string;
  drunkAt: string; // "YYYY/MM/DD"
};
