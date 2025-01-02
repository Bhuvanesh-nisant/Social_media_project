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

  //Add post//
  showAddPostModal: boolean = false; // Track the modal visibility
  postContent: string = ''; // Store the post content
  selectedImage: string | null = null; // Store the selected image as a base64 string
  isUploadPostDisabled: boolean = true

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

  // Method to navigate to Add Post page
  navigateToAddPost(): void {
    this.router.navigate(['/add-post']); // You can replace with your actual route for adding a post
  }

  // Method to upload Profile Photo
  uploadProfilePhoto(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const reader = new FileReader();

      reader.onload = () => {
        const base64Image = reader.result as string; // Convert to Base64
        const token = this.authService.getToken(); // Get the token from AuthService

        const payload = {
          token: token,
          profilePhoto: base64Image, // Send profile photo in base64
          coverPhoto: null // If not updating cover photo, send null
        };

        // Send request to update profile photo
        this.http.post('http://localhost:8080/api/profile/update', payload).subscribe(
          () => {
            this.profilePhotoUrl = base64Image; // Update the displayed profile photo
          },
          (error) => {
            console.error('Error uploading profile photo', error);
            this.errorMessage = 'Failed to upload profile photo. Please try again later.';
          }
        );
      };

      reader.readAsDataURL(file); // Convert the file to Base64 string
    }
  }

  // Method to upload Cover Photo
  uploadCoverPhoto(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const reader = new FileReader();

      reader.onload = () => {
        const base64Image = reader.result as string; // Convert to Base64
        const token = this.authService.getToken(); // Get the token from AuthService

        const payload = {
          token: token,
          profilePhoto: null, // If not updating profile photo, send null
          coverPhoto: base64Image // Send cover photo in base64
        };

        // Send request to update cover photo
        this.http.post('http://localhost:8080/api/profile/update', payload).subscribe(
          () => {
            this.coverPhotoUrl = base64Image; // Update the displayed cover photo
          },
          (error) => {
            console.error('Error uploading cover photo', error);
            this.errorMessage = 'Failed to upload cover photo. Please try again later.';
          }
        );
      };

      reader.readAsDataURL(file); // Convert the file to Base64 string
    }
  }
  openAddPostModal(): void {
    this.showAddPostModal = true;
  }

  closeAddPostModal(): void {
    this.showAddPostModal = false;
    this.postContent = '';
    this.selectedImage = null;
    this.isUploadPostDisabled = true;
  }

  handleFileInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      const reader = new FileReader();

      reader.onload = () => {
        this.selectedImage = reader.result as string;
        this.updateUploadButtonState();
      };

      reader.readAsDataURL(file);
    }
  }

  updateUploadButtonState(): void {
    this.isUploadPostDisabled = !this.postContent.trim() && !this.selectedImage;
  }

  submitPost(): void {
    if (!this.authService.isAuthenticated()) return;

    const token = this.authService.getToken();
    if (token) {
      const payload = {
        username: this.userName,
        content: this.postContent,
        image: this.selectedImage || '',
        token: token
      };

      this.http.post('http://localhost:8080/api/posts/upload', payload).subscribe(
        () => {
          this.posts.unshift(payload); // Add the new post to the top of the list
          this.closeAddPostModal();
        },
        (error) => {
          console.error('Error submitting post', error);
        }
      );
      
    }
  }
}

