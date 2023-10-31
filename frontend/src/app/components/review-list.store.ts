import { Injectable } from "@angular/core";
import { Review } from "../models/Review";
import { ComponentStore } from "@ngrx/component-store";

export interface ReviewListState {
  reviews: Review[]
}

const initialState: ReviewListState = {
  reviews: []
}

@Injectable()
export class ReviewListStore extends ComponentStore<ReviewListState> {
  constructor() {
    super(initialState)
  }

  readonly $reviews = this.selectSignal(state => state.reviews)
}
