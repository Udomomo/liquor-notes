import { createAction, props } from '@ngrx/store';
import { Review } from 'src/app/models/Review';

export const loadReviews = createAction('[Reviews] Load Reviews', props<{userId: string}>());
export const loadReviewsSuccess = createAction('[Reviews] Load Reviews Success', props<{reviews: Review[]}>());
