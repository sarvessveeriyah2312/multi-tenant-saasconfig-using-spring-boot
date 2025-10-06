import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TenantConfigService, TenantConfig } from '../../core/services/tenant-config.service';

@Component({
  selector: 'app-tenant-config-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h3>Tenant Configurations</h3>
      <button class="btn btn-primary" (click)="add()">Add New</button>
    </div>

    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Tenant ID</th>
          <th>Description</th>
          <th>Config</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let t of tenants">
          <td>{{ t.id }}</td>
          <td>{{ t.tenantId }}</td>
          <td>{{ t.description }}</td>
          <td><pre>{{ t.config | json }}</pre></td>
          <td>
            <button class="btn btn-warning btn-sm me-2" (click)="edit(t.id)">Edit</button>
            <button class="btn btn-danger btn-sm" (click)="remove(t.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  `,
})
export class TenantConfigListComponent implements OnInit {
  tenants: TenantConfig[] = [];
  constructor(private service: TenantConfigService, private router: Router) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.service.getAll().subscribe((data) => (this.tenants = data));
  }

  add() {
    this.router.navigate(['/tenants/new']);
  }

  edit(id?: number) {
    this.router.navigate(['/tenants/edit', id]);
  }

  remove(id?: number) {
    if (!id) return;
    if (confirm('Delete this tenant config?')) {
      this.service.delete(id).subscribe(() => this.load());
    }
  }
}
