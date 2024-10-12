import { Component } from '@angular/core';
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
  onSubmit(form: NgForm) {
    if (this.password !== this.confirmPassword) {
      alert('Passwords do not match');
      return;
    }

}
}