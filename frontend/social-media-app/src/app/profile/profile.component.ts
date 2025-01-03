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

  // Add Post Modal Variables
  showAddPostModal: boolean = false; // Track modal visibility
  postContent: string = ''; // Store post content
  selectedImage: string | null = null; // Store selected image as Base64 string
  isUploadPostDisabled: boolean = true; // Track upload button state

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

  // Fetch profile and posts data
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

  // Tab switching
  switchTab(tab: string): void {
    this.activeTab = tab;
  }

  // Upload profile photo
  uploadProfilePhoto(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const reader = new FileReader();

      reader.onload = () => {
        const base64Image = reader.result as string;
        const token = this.authService.getToken();

        const payload = {
          token: token,
          profilePhoto: base64Image,
          coverPhoto: null
        };

        this.http.post('http://localhost:8080/api/profile/update', payload).subscribe(
          () => {
            this.profilePhotoUrl = base64Image;
          },
          (error) => {
            console.error('Error uploading profile photo', error);
            this.errorMessage = 'Failed to upload profile photo. Please try again later.';
          }
        );
      };

      reader.readAsDataURL(file);
    }
  }

  // Upload cover photo
  uploadCoverPhoto(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const reader = new FileReader();

      reader.onload = () => {
        const base64Image = reader.result as string;
        const token = this.authService.getToken();

        const payload = {
          token: token,
          profilePhoto: null,
          coverPhoto: base64Image
        };

        this.http.post('http://localhost:8080/api/profile/update', payload).subscribe(
          () => {
            this.coverPhotoUrl = base64Image;
          },
          (error) => {
            console.error('Error uploading cover photo', error);
            this.errorMessage = 'Failed to upload cover photo. Please try again later.';
          }
        );
      };

      reader.readAsDataURL(file);
    }
  }

  // Open Add Post modal
  openAddPostModal(): void {
    this.showAddPostModal = true;
  }

  // Close Add Post modal
  closeAddPostModal(): void {
    this.showAddPostModal = false;
    this.postContent = '';
    this.selectedImage = null;
    this.isUploadPostDisabled = true;

    // Remove leftover modal elements (in case of Bootstrap modals)
    document.body.classList.remove('modal-open');
    const backdrops = document.querySelectorAll('.modal-backdrop');
    backdrops.forEach((backdrop) => backdrop.remove());
  }

  // Handle file input for Add Post modal
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

  // Update the upload button state
  updateUploadButtonState(): void {
    this.isUploadPostDisabled = !this.postContent.trim() && !this.selectedImage;
  }

  // Submit a new post
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
  
      this.http.post<{ message: string }>('http://localhost:8080/api/posts/upload', payload).subscribe(
        (response) => {
          console.log(response.message); // Log success message
          this.posts.unshift(payload); // Add the new post to the list
          this.closeAddPostModal(); // Close the modal
          
          setTimeout(() => {
            this.router.navigate(['/newsfeed']); // Navigate to the News Feed
          }, 200);
        },
        (error) => {
          console.error('Error submitting post', error);
          this.errorMessage = 'Failed to upload post. Please try again later.';
        }
      );
    }
  }
  
}
