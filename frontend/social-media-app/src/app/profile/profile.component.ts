import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userName: string = '';
  userAbout: string = '';
  profilePhotoUrl: string | null = null;
  coverPhotoUrl: string | null = null;
  posts: any[] = [];
  errorMessage: string = '';
  activeTab: string = 'about';

  private defaultProfilePhoto: string = 'assets/images/profile_default_pic.jpg';
  private defaultCoverPhoto: string = 'assets/images/cover_default_pic.jpg';

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      const token = this.authService.getToken();
      if (token) {
        this.getProfileAndPostsData(token);
      }
    } else {
      this.router.navigate(['/home']);
    }
  }

  getProfileAndPostsData(token: string): void {
    this.http.get<any>(`http://localhost:8080/api/profile/${token}`).subscribe(
      (response) => {
        const { profile, posts } = response;
        this.userName = profile.name || 'Anonymous';
        this.userAbout = profile.bio || 'No about information provided.';
        this.profilePhotoUrl = profile.profilePhoto || this.defaultProfilePhoto;
        this.coverPhotoUrl = profile.coverPhoto || this.defaultCoverPhoto;
        this.errorMessage = '';

        this.posts = posts || [];
      },
      (error) => {
        console.error('Error fetching profile data', error);
        this.errorMessage = 'Unable to load profile data. Please try again later.';
      }
    );
  }

  switchTab(tab: string): void {
    this.activeTab = tab;
  }
}
