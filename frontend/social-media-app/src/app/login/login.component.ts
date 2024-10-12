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

  constructor(private http: HttpClient,private route:Router) {}


  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
  
  navigateToSignup(){
    this.route.navigate(['/signup'])
  }
  

  onSubmit(form: NgForm) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });

    const body = new URLSearchParams();
    body.set('userid', this.userId); // Fixed 'userid' to match backend
    body.set('password', this.password);

    this.http.post('http://localhost:8080/login', body.toString(), { headers, responseType: 'json' })
      .subscribe({
        next: (response: any) => {
          console.log('Login successful', response);
          // alert(`Token: ${response.token}`);
          this.resetForm(form); // Reset the form
          this.closeModal.emit(); // Close the modal
        },
        error: (err) => {
          console.log('Login failed', err);
        }
      });
  }

  resetForm(form: NgForm) {
    form.reset();
    this.userId = '';
    this.password = '';
  }
  


}
