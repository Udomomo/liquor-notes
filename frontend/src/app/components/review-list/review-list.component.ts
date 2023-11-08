import { Component, OnInit, Signal, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewComponent } from '../review/review.component';
import { Store } from '@ngrx/store';
import { ReviewListState } from 'src/app/stores/review-list.store';
import { Review } from 'src/app/models/Review';
import { loadReviewsType } from './review-list.actions';
import { Observable } from 'rxjs';
import { selectReviews } from './review-list.selector';

@Component({
  selector: 'app-review-list',
  standalone: true,
  imports: [CommonModule, ReviewComponent],
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.scss']
})
export class ReviewListComponent implements OnInit {
  reviews: Signal<Review[]>;

  constructor(private store: Store<ReviewListState>) {
    this.reviews = this.store.selectSignal(selectReviews)
  }

  ngOnInit(): void {
    this.store.dispatch({type: loadReviewsType, userId: "01ARZ3NDEKTSV4RRFFQ69G5FAV"})
  }
}
