import { Component } from '@angular/core';

interface Comment {
  content: string;
  timestamp: string;
}

interface Post {
  username: string;
  postedAgo: string;
  content: string;
  image: string;
  likeCount: number;
  comments: Comment[];
}

@Component({
  selector: 'app-newsfeed',
  templateUrl: './newsfeed.component.html',
  styleUrls: ['./newsfeed.component.css']
})
export class NewsfeedComponent {
  posts: Post[] = [
    {
      username: "Bhuvanesh",
      postedAgo: "2024-11-08T10:00:00Z",
      content: "Test Post",
      image: "data:image/jpg;base64,...", 
      likeCount: 0,
      comments: [
        { content: "Nice post!", timestamp: "2024-11-08T12:00:00Z" }
      ]
    }
  ];
  newComment: string = '';

  increaseLike(post: Post) {
    post.likeCount++;
  }

  addComment(post: Post, event: Event) {
    event.preventDefault();
    if (this.newComment.trim()) {
      const newComment: Comment = {
        content: this.newComment.trim(),
        timestamp: new Date().toISOString()
      };
      post.comments.push(newComment);
      this.newComment = '';
    }
  }

  formatTime(timestamp: string): string {
    const date = new Date(timestamp);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
  }
}
