import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand fw-bold" routerLink="/">SaaS Config</a>

        <div class="d-flex" *ngIf="isLoggedIn()">
          <button class="btn btn-outline-light btn-sm me-2" routerLink="/tenants">
            Dashboard
          </button>
          <button class="btn btn-danger btn-sm" (click)="logout()">Logout</button>
        </div>
      </div>
    </nav>
  `,
})
export class NavbarComponent {
  constructor(private router: Router) {}

  isLoggedIn(): boolean {
    if (typeof window === 'undefined') return false;
    return !!localStorage.getItem('token');
  }

  logout(): void {
    if (typeof window !== 'undefined') {
      localStorage.removeItem('token');
    }
    this.router.navigate(['/login']);
  }

}
