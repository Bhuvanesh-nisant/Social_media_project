import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgForm } from '@angular/forms';

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

  constructor(private http: HttpClient) {}

  onSubmit(form: NgForm) {
    if (this.password !== this.confirmPassword) {
      this.passwordMismatch = true;
      return; 
    } else {
      this.passwordMismatch = false;
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    const signupData = {
      name: this.name,
      email: this.email,
      password: this.password,
      mobileNo: this.mobileNo
    };

    this.http.post('http://localhost:8080/signup', signupData, { headers })
      .subscribe({
        next: (response) => {
          console.log('Signup successful', response);
          alert('Signup successful!');
          this.resetForm(form);
        },
        error: (err) => {
          console.log('Signup failed', err);
        }
      });
  }

  resetForm(form: NgForm) {
    form.reset();
    this.name = '';
    this.email = '';
    this.password = '';
    this.confirmPassword = '';
    this.mobileNo = '';
  }
}
