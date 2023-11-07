import { Review } from "../models/Review";

export interface ReviewListState {
  reviews: Review[]
}

export const initialReviewListState: ReviewListState = {
  reviews: []
}
