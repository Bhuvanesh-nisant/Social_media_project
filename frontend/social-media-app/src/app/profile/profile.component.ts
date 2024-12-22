import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userName: string = '';
  userAbout: string = ''; // Renamed to `userAbout` to reflect the "About" section
  profilePhotoUrl: string | null = null;
  coverPhotoUrl: string | null = null;
  posts: any[] = [];
  errorMessage: string = ''; // For showing errors
  activeTab: string = 'about'; // Default tab is "About"

  private defaultProfilePhoto: string = 'assets/images/profile_default_pic.jpg';
  private defaultCoverPhoto: string = 'assets/images/cover_default_pic.jpg';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken'); // Retrieve token from localStorage
    if (token) {
      this.getProfileData(token);
    } else {
      this.router.navigate(['/login']); // Redirect to login if no token exists
    }
  }

  getProfileData(token: string): void {
    this.http.get<any>(`http://localhost:8080/api/profile/${token}`).subscribe(
      (response) => {
        this.userName = response.name || 'Anonymous';
        this.userAbout = response.bio || 'No about information provided.'; // Set bio as about
        this.profilePhotoUrl = response.profilePhoto || this.defaultProfilePhoto;
        this.coverPhotoUrl = response.coverPhoto || this.defaultCoverPhoto;
        this.errorMessage = '';
      },
      (error) => {
        console.error('Error fetching profile data', error);
        this.errorMessage = 'Unable to load profile data. Please try again later.';
      }
    );
  }

  switchTab(tab: string): void {
    this.activeTab = tab;

    if (tab === 'posts') {
      const token = localStorage.getItem('authToken');
      if (token) {
        this.getProfilePosts(token);
      }
    }
  }

  getProfilePosts(token: string): void {
    this.http.get<any>(`http://localhost:8080/api/profile/${token}/posts`).subscribe(
      (response) => {
        this.posts = response.posts || [];
        this.errorMessage = '';
      },
      (error) => {
        console.error('Error fetching posts', error);
        this.errorMessage = 'Unable to load posts. Please try again later.';
      }
    );
  }
}
