import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="row justify-content-center mt-5">
      <div class="col-md-4">
        <div class="card shadow-sm">
          <div class="card-body">
            <h4 class="mb-3 text-center">Login</h4>
            <form [formGroup]="form" (ngSubmit)="onSubmit()">
              <div class="mb-3">
                <label>Email</label>
                <input class="form-control" formControlName="email" />
              </div>
              <div class="mb-3">
                <label>Password</label>
                <input type="password" class="form-control" formControlName="password" />
              </div>
              <button type="submit" class="btn btn-primary w-100" [disabled]="form.invalid">
                Login
              </button>
              <div *ngIf="error" class="text-danger mt-2 small text-center">
                Invalid credentials
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class LoginComponent {
  form: FormGroup;
  error = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {
    // ✅ Make sure form is initialized properly
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const email = this.form.get('email')?.value;
    const password = this.form.get('password')?.value;

    // ✅ Ensure AuthService is defined
    if (this.auth && typeof this.auth.login === 'function') {
      const success = this.auth.login(email, password);
      if (success) {
        this.router.navigate(['/tenants']);
      } else {
        this.error = true;
      }
    } else {
      console.error('AuthService is not initialized properly.');
      this.error = true;
    }
  }
}
