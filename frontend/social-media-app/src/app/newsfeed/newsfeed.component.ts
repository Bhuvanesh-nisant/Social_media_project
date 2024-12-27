import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface Comment {
  id?: number; // Optional because the backend generates it
  content: string;
  timestamp: string;
}

interface Post {
  postId: number; // Primary identifier for posts
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

  constructor(private http: HttpClient, private route: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken');
    if (!token) {
      this.route.navigate(['/login']);
    } else {
      this.loadPosts();
    }
  }

  goToProfile(): void {
    this.route.navigate(['/profile']);
  }

  loadPosts(): void {
    this.http.get<Post[]>('http://localhost:8080/api/newsfeed').subscribe(
      (data) => {
        this.posts = data; // Assign the response directly without modification
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

    // Ensure the comment input is valid
    if (this.newComment.trim()) {
      const newComment: Comment = {
        content: this.newComment.trim(),
        timestamp: new Date().toISOString(), // Timestamp for the comment
      };

      // Optimistic UI update for instant feedback
      post.comments.push(newComment);
      this.newComment = ''; // Clear the input field

      // Send the comment to the backend
      this.http.post(`http://localhost:8080/api/newsfeed/${post.postId}/comments`, newComment, { responseType: 'text' })
        .subscribe(
          (response) => {
            console.log(response); // Log the plain text response
          },
          (error) => {
            console.error('Error adding comment', error);
            // Roll back UI change if the request fails
            post.comments.pop();
          }
        );
    }
  }

  Logout(): void {
    // Clear token and timestamp from localStorage
    localStorage.removeItem('authToken');
    localStorage.removeItem('tokenTimestamp');

    // Redirect to home
    this.route.navigate(['/home']);
  }
}
