import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { loadReviews, loadReviewsSuccess } from "../components/review-list/review-list.actions";
import { catchError, map, of, switchMap } from "rxjs";
import { ReviewRepository } from "../repositories/review.repository";

@Injectable()
export class ReviewListEffects {
  constructor(private actions$: Actions, private reviewRepository: ReviewRepository) {}

  loadReviews$ = createEffect(() => 
    this.actions$.pipe(
      ofType(loadReviews),
      switchMap((action) => 
        this.reviewRepository.listReviews(action.userId).pipe(
          map((reviews) => loadReviewsSuccess({ reviews })),
          // TODO: エラートーストを出す
          catchError((error) => of(error)))
        )
      )
    )
}
