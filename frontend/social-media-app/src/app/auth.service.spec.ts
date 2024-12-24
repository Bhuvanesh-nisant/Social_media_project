import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor() {}

  isAuthenticated(): boolean {
    // Check if the token exists and is valid
    const token = localStorage.getItem('authToken');
    return !!token;
  }

  login(token: string): void {
    // Save the token to localStorage
    localStorage.setItem('authToken', token);
  }

  logout(): void {
    // Remove the token from localStorage
    localStorage.removeItem('authToken');
  }

  getToken(): string | null {
    // Retrieve the token from localStorage
    return localStorage.getItem('authToken');
  }
}
