import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgForm } from '@angular/forms';

interface SignupData {
  name: string;
  email: string;
  password: string;
  mobileNo: string;
}

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  name: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  mobileNo: string = '';

  passwordMismatch: boolean = false; 
  errorMessage: string = ''; // Variable for error messages
  successMessage: string = ''; // Variable for success messages
  isLoading: boolean = false; // State to handle loading

  constructor(private http: HttpClient) {}

  onSubmit(form: NgForm) {
    this.errorMessage = ''; // Reset error message on each submission
    this.successMessage = ''; // Reset success message on each submission
    this.isLoading = true; // Set loading to true while request is being processed

    // Check if passwords match
    if (this.password !== this.confirmPassword) {
      this.passwordMismatch = true;
      this.isLoading = false; // Stop loading if validation fails
      return; 
    } else {
      this.passwordMismatch = false;
    }

    // Validate mobile number (10 digits)
    const mobilePattern = /^[0-9]{10}$/;
    if (!mobilePattern.test(this.mobileNo)) {
      this.errorMessage = 'Mobile number must be exactly 10 digits';
      this.isLoading = false;
      return;
    }

    // Validate password length (min 6 characters)
    if (this.password.length < 6) {
      this.errorMessage = 'Password must be at least 6 characters long';
      this.isLoading = false;
      return;
    }

    // Define headers
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    // Data to send in the signup request
    const signupData: SignupData = {
      name: this.name,
      email: this.email,
      password: this.password,
      mobileNo: this.mobileNo
    };

    // Make POST request to backend
    this.http.post('http://localhost:8080/signup', signupData, { headers })
      .subscribe({
        next: (response) => {
          console.log('Signup successful', response);
          this.successMessage = 'Signup successful!';
          this.resetForm(form); // Reset the form after successful signup
          this.isLoading = false; // Stop loading after success
        },
        error: (err) => {
          console.log('Signup failed', err);

          // Handle specific 400 error for "User already exists"
          if (err.status === 400 && err.error.message === 'User already exists') {
            this.errorMessage = 'User already exists. Please use a different email.';
          } else if (err.error && err.error.message) {
            // Capture specific error message from the backend if available
            this.errorMessage = err.error.message; 
          } else {
            this.errorMessage = 'Signup failed. Please try again.'; // Default error message
          }

          this.isLoading = false; // Stop loading after error
        }
      });
  }

  resetForm(form: NgForm) {
    form.reset(); // Reset the form
    this.name = '';
    this.email = '';
    this.password = '';
    this.confirmPassword = '';
    this.mobileNo = '';
  }
}
