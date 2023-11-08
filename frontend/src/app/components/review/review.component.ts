import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { Review } from 'src/app/models/Review';

@Component({
  selector: 'app-review',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule],
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent {
  @Input()
  title: string = ""
  
  @Input()
  star: number = 0

  @Input()
  userName: string | undefined = ""

  @Input()
  content: string = ""

  @Input()
  createdAt: string = ""

  @Input()
  locationName: string | undefined = ""

  @Input()
  tags: string[] = []

  tagNames(): string {
    return this.tags.join(", ")
  }
}
