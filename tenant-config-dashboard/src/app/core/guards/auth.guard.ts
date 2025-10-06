import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const AuthGuard: CanActivateFn = () => {
  const router = inject(Router);
  if (typeof window === 'undefined') return false;

  const token = localStorage.getItem('token');
  if (!token) {
    router.navigate(['/login']);
    return false;
  }
  return true;
};
