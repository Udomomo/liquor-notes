import { Routes } from '@angular/router';
import { ReviewListComponent } from './components/review-list/review-list.component';

export const routes: Routes = [
  {
    path: '',
    // 通常の読み込み
    component: ReviewListComponent,
  },
];
