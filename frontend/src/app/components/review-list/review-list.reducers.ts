import { createReducer, createSelector, on } from '@ngrx/store';import { loadReviewsSuccess } from './review-list.actions';
import { ReviewListState, initialReviewListState } from 'src/app/stores/review-list.store';
;

export const reviewListReducer = createReducer(
  initialReviewListState,
  on(loadReviewsSuccess, (state: ReviewListState, { reviews }) => ({
    reviews: state.reviews.concat(reviews)
  })
));
