import { Component, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  @Output() closeModal = new EventEmitter<void>();
  userId: string = '';
  password: string = '';
  showPassword: boolean = false;
  loginError: string = ''; // Add this to track login errors

  constructor(private http: HttpClient, private route: Router) {}

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
  
  navigateToSignup() {
    this.route.navigate(['/signup']);
  }
  fdPassword(){
    this.route.navigate(['/forgot-password']);
  }
  
  onSubmit(form: NgForm) {
    
    if (form.invalid) {
      return;
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });

    const body = new URLSearchParams();
    body.set('userid', this.userId);
    body.set('password', this.password);

    this.http.post('http://localhost:8080/login', body.toString(), { headers, responseType: 'json' })
      .subscribe({
        next: (response: any) => {
          console.log('Login successful', response);
          this.resetForm(form);
          this.closeModal.emit();
          this.loginError = ''; // Reset error on successful login
        },
        error: (err) => {
          console.log('Login failed', err);
          this.loginError = 'Incorrect password or username'; // Set error message if login fails
        }
        
      });
  }

  resetForm(form: NgForm) {
    form.reset();
    this.userId = '';
    this.password = '';
    this.loginError = ''; // Reset the error message on form reset
    
    this.route.navigate(['newsfeed'])
    
  }
}
