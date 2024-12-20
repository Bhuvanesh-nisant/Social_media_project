import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  // Retrieve token from localStorage
  const token = localStorage.getItem('authToken');

  if (token) {
    // Token exists, allow access to the route
    return true;
  }

  // Redirect to home if no valid token
  router.navigate(['/home']);
  return false;
};
