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
  userBio: string = '';
  profilePhotoUrl: string | null = null;
  coverPhotoUrl: string | null = null;
  errorMessage: string = ''; // To show error messages if any

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken'); // Retrieve the token from localStorage
    if (token) {
      this.getProfileData(token);
    } else {
      // Redirect to login if no token exists
      this.router.navigate(['/login']);
    }
  }

  getProfileData(token: string): void {
    this.http.get<any>(`http://localhost:8080/api/profile/${token}`).subscribe(
      (response) => {
        this.userName = response.name || 'Anonymous';
        this.userBio = response.bio || 'No bio available';

        // Handle profile photo
        this.profilePhotoUrl = response.profilePhoto
          ? `data:image/jpeg;base64,${response.profilePhoto}`
          : 'assets/images/default-profile.png'; // Default profile photo

        // Handle cover photo
        this.coverPhotoUrl = response.coverPhoto
          ? `data:image/jpeg;base64,${response.coverPhoto}`
          : ''; // Default or empty cover photo
      },
      (error) => {
        console.error('Error fetching profile data', error);
        this.errorMessage = 'Unable to load profile data. Please try again later.';
      }
    );
  }
}
