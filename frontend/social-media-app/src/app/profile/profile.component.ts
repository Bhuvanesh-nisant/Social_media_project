import { Component } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent {
  // User Details
  coverPhotoUrl: string = 'assets/images/cover-photo.jpg';
  profilePhotoUrl: string = 'assets/images/profile-photo.jpg';
  userName: string = 'John Doe';
  userBio: string = 'Web Developer | Tech Enthusiast';

  // Sample Posts
  posts = [
    {
      author: 'John Doe',
      content: 'Just finished working on a new project! 🚀',
      timestamp: '2 hours ago',
    },
    {
      author: 'John Doe',
      content: 'Enjoying a sunny day at the beach 🌞',
      timestamp: '1 day ago',
    },
    {
      author: 'John Doe',
      content: 'Learning Angular is fun and powerful! 💻',
      timestamp: '3 days ago',
    },
  ];
}
