import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  name: string = '';
  userid: string = '';
  showResetForm: boolean = false;
  newPassword: string = '';
  confirmPassword: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  // Initial verification method
  onSubmit() {
    const fdPasswordRequest = { name: this.name, userid: this.userid };

    this.http.post<{ message: string }>('http://localhost:8080/forgot-password/verify', fdPasswordRequest)
      .subscribe({
        next: (response) => {
          if (response.message === 'User verified') {
            this.showResetForm = true;
            this.errorMessage = '';
          } else {
            this.errorMessage = 'User ID or Name mismatch.';
          }
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'An error occurred during verification';
          this.showResetForm = false;
        }
      });
  }

  // Password reset method after verification
  onResetPassword() {
    if (this.newPassword !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match!';
      return;
    }

    const resetPasswordRequest = {
      userid: this.userid,
      newPassword: this.newPassword
    };

    this.http.post<{ message: string }>('http://localhost:8080/forgot-password/reset', resetPasswordRequest)
      .subscribe({
        next: (response) => {
          alert(response.message);  // Show a success message
          this.newPassword = '';
          this.confirmPassword = '';
          this.showResetForm = false;
          this.errorMessage = '';

          // Redirect to home component after reset
          this.router.navigate(['/home']);
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'An error occurred during password reset';
        }
      });
  }
}
