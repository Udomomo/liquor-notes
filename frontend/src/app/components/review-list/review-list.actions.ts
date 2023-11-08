import { createAction, props } from '@ngrx/store';
import { Review } from 'src/app/models/Review';

export const loadReviewsType = '[Reviews] Load Reviews'
export const loadReviewsSuccessType = '[Reviews] Load Reviews Success'

export const loadReviews = createAction(loadReviewsType, props<{userId: string}>());
export const loadReviewsSuccess = createAction(loadReviewsSuccessType, props<{reviews: Review[]}>());
