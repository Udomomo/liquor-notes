import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewComponent } from '../review/review.component';

@Component({
  selector: 'app-review-list',
  standalone: true,
  imports: [CommonModule, ReviewComponent],
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.scss']
})
export class ReviewListComponent {
  reviews = [1]
}
