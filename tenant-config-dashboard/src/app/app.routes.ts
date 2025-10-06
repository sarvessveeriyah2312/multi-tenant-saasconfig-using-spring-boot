import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login.component';
import { TenantConfigListComponent } from './features/tenant-config/tenant-config-list.component';
import { TenantConfigFormComponent } from './features/tenant-config/tenant-config-form.component';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'tenants', pathMatch: 'full' },
      { path: 'tenants', component: TenantConfigListComponent },
      { path: 'tenants/new', component: TenantConfigFormComponent },
      { path: 'tenants/edit/:id', component: TenantConfigFormComponent },
    ],
  },
  { path: '**', redirectTo: 'login' },
];
