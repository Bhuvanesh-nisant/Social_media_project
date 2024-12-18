import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  // Retrieve token and timestamp from localStorage
  const token = localStorage.getItem('authToken');
  const tokenTimestamp = localStorage.getItem('tokenTimestamp');

  if (token && tokenTimestamp) {
    const currentTime = Date.now();
    const tokenAge = currentTime - parseInt(tokenTimestamp, 10);

    // Check if the token is within the 5-minute validity period
    if (tokenAge <= 5 * 60 * 1000) {
      return true; // Token is valid
    } else {
      // Token expired, clear storage and redirect to home
      localStorage.removeItem('authToken');
      localStorage.removeItem('tokenTimestamp');
    }
  }

  // Redirect to home if no valid token
  router.navigate(['/home']);
  return false;
};
