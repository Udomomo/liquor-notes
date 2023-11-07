import { Component, OnInit, Signal, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewComponent } from '../review/review.component';
import { Store } from '@ngrx/store';
import { ReviewListState } from 'src/app/stores/review-list.store';
import { reviewListSelector } from './review-list.selectors';
import { Review } from 'src/app/models/Review';
import { loadReviews } from './review-list.actions';

@Component({
  selector: 'app-review-list',
  standalone: true,
  imports: [CommonModule, ReviewComponent],
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.scss']
})
export class ReviewListComponent implements OnInit {
  reviews$: Signal<Review[]> = signal([]);

  constructor(private store: Store<ReviewListState>) {
    loadReviews({userId:"01ARZ3NDEKTSV4RRFFQ69G5FAV"})
  }

  ngOnInit(): void {
    this.reviews$ = this.store.selectSignal(reviewListSelector)
  }
}
