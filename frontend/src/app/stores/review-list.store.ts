import { Injectable, Signal, signal } from "@angular/core";
import { Review } from "../models/Review";
import { Store } from "@ngrx/store";

export interface ReviewListState {
  $reviews: Signal<Review[]>
}

const initialState: ReviewListState = {
  $reviews: signal<Review[]>([])
}
