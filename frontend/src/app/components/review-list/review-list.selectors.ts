import { createFeatureSelector, createSelector } from "@ngrx/store";
import { ReviewListState } from "src/app/stores/review-list.store";

// main.tsに登録した当該storeのkey名を使う
const reviewListStateKey = 'reviewList';

export const selectReviewListState = createFeatureSelector<ReviewListState>(reviewListStateKey);

export const selectReviews = createSelector(
  selectReviewListState,
  (state: ReviewListState) => state.reviews
);
