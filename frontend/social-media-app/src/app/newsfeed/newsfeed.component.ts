import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Comment {
  content: string;
  timestamp: string;
}

interface Post {
  id: number; // Added for identifying posts
  username: string;
  postedAgo: string;
  content: string;
  image: string |null; 
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

  // Load posts from the backend API
  loadPosts(): void {
    this.http.get<Post[]>('http://localhost:8080/api/newsfeed').subscribe(
      (data) => {
        // Process posts to include base64 image conversion if needed
        this.posts = data.map(post => ({
          ...post,
          image: post.image ? `data:image/jpeg;base64,${post.image}` : null
        }));
      },
      (error) => {
        console.error('Error loading posts', error);
      }
    );
  }

  // Format the timestamp for display
  formatTime(timestamp: string): string {
    const date = new Date(timestamp);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
  }

  // Increase like count (local operation)
  increaseLike(post: Post): void {
    post.likeCount++;
    // Optional: Call the backend to update the like count in the database
    // this.http.post(`http://localhost:8080/api/posts/${post.id}/like`, {}).subscribe();
  }

  // Add a comment to a post
  addComment(post: Post, event: Event): void {
    event.preventDefault(); // Prevent the form from submitting
    if (this.newComment.trim()) {
      const newComment: Comment = {
        content: this.newComment.trim(),
        timestamp: new Date().toISOString() // Use current time for new comment
      };

      // Add comment locally for immediate UI update
      post.comments.push(newComment);
      this.newComment = ''; // Clear input field

      // Optional: Call the backend to persist the comment in the database
      // this.http.post(`http://localhost:8080/api/posts/${post.id}/comments`, newComment).subscribe();
    }
  }
}
