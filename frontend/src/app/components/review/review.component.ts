import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { Review } from 'src/app/domains/Review';

@Component({
  selector: 'app-review',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule],
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent {
  review: Review = {
    id: '1',
    user: {
      id: '1',
      name: 'Udomomo'
    },
    title: 'グレンドロナック 15年 2004 モリソン&マッカイ 信濃屋プライベートボトル',
    star: 9.1,
    contents: '香りは穏やかな木材。かすかにリンゴ。\n飲んでみると、口の中にマシュマロやケーキのような甘さが広がるが、それと同時にわずかなスパイスも感じる。余韻には辛味が残るがすっと消える。\n信濃屋オータムフェスタの限定ボトル。試飲することができて良い機会だった。',
    createdAt: '2023-01-01 00:00:00',
    location: {
      id: '1',
      name: '信濃屋 銀座三丁目店'
    },
    tags: [
      {
        id: '1',
        name: 'ウイスキー'
      },
      {
        id: '2',
        name: 'モリソン&マッカイ'
      }
    ]
  }

  tagNames= this.review.tags.map(tag => tag.name)
}
