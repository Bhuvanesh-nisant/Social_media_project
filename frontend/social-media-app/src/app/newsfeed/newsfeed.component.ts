import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Comment {
  content: string;
  timestamp: string;
}

interface Post {
  postId: number;
  id?: number; 
  username: string;
  postedAgo: string;
  content: string;
  image: string | null;
  likeCount: number;
  comments: Comment[];
}

@Component({
  selector: 'app-newsfeed',
  templateUrl: './newsfeed.component.html',
  styleUrls: ['./newsfeed.component.css']
})
export class NewsfeedComponent implements OnInit {
  posts: Post[] = [];
  newComment: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.http.get<Post[]>('http://localhost:8080/api/newsfeed').subscribe(
      (data) => {
        
        this.posts = data.map(post => ({
          ...post,
          id: post.postId, 
          image: post.image ? `data:image/jpeg;base64,${post.image}` : null
        }));
        console.log('Loaded posts:', this.posts); // Debugging: Log loaded posts
      },
      (error) => {
        console.error('Error loading posts', error);
      }
    );
  }

  formatTime(timestamp: string): string {
    const date = new Date(timestamp);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
  }

  increaseLike(postId: number): void {
    this.http.post(`http://localhost:8080/api/newsfeed/${postId}/like`, {}, { responseType: 'text' })
      .subscribe({
        next: (response) => {
          console.log('Like response:', response);
          const post = this.posts.find(post => post.postId === postId);
          if (post) {
            post.likeCount++;
          }
        },
        error: (error) => {
          console.error('Error liking post', error);
        }
      });
  }

  addComment(post: Post, event: Event): void {
    event.preventDefault();
    if (this.newComment.trim()) {
      const newComment: Comment = {
        content: this.newComment.trim(),
        timestamp: new Date().toISOString()
      };

      post.comments.push(newComment); // Add comment locally for instant feedback
      this.newComment = ''; // Clear the input field

      this.http.post(`http://localhost:8080/api/newsfeed/${post.id}/comments`, newComment).subscribe(
        () => console.log(`Comment added to post ${post.id}`),
        (error) => console.error('Error adding comment', error)
      );
    }
  }
}
