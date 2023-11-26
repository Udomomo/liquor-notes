import { Routes } from '@angular/router';
import { ReviewListComponent } from './components/review-list/review-list.component';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    // 通常の読み込み
    component: ReviewListComponent,
  },
];
