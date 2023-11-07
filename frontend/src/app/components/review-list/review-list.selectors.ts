import { createFeatureSelector, createSelector } from '@ngrx/store';
import { ReviewListState } from 'src/app/stores/review-list.store';

export const reviewListFeature = (state: ReviewListState) => state;

export const reviewListSelector = createSelector(
  reviewListFeature,
  (state: ReviewListState) => state.reviews
);
