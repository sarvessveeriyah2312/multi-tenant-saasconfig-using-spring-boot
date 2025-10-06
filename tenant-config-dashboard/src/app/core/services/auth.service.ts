import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  login(email: string, password: string): boolean {
    if (typeof window === 'undefined') return false;
    if (email === 'admin@example.com' && password === 'admin123') {
      localStorage.setItem('token', 'mock-jwt-token');
      return true;
    }
    return false;
  }

  logout(): void {
    if (typeof window !== 'undefined') {
      localStorage.removeItem('token');
    }
  }
}
