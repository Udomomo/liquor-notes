import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Review } from "../models/Review";

@Injectable()
export class ReviewRepository{
  constructor(private http: HttpClient) {}

  listReviews(userId: string): Observable<Review[]> {
    return this.http.get<Review[]>(`api/${userId}/reviews`)
  }
}
