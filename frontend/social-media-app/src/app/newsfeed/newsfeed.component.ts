import { Component } from '@angular/core';

@Component({
  selector: 'app-newsfeed',
  templateUrl: './newsfeed.component.html',
  styleUrls: ['./newsfeed.component.css']
})
export class NewsfeedComponent {
  likeCount: number = 0;
  newComment: string = '';
  comments: string[] = [];

  increaseLike() {
    this.likeCount++;
  }

  addComment(event: Event) {
    event.preventDefault();
    if (this.newComment.trim()) {
      this.comments.push(this.newComment.trim());
      this.newComment = '';
    }
  }
}
