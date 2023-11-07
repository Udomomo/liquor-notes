import { createSelector } from '@ngrx/store';
import { ReviewListState } from 'src/app/stores/review-list.store';

export const reviewListFeature = (state: ReviewListState) => state;

export const reviewListSelector = createSelector(
  reviewListFeature,
  (state: ReviewListState) => Array.from(state.reviews)
);
