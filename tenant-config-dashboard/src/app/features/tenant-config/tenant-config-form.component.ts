import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TenantConfigService } from '../../core/services/tenant-config.service';

@Component({
  selector: 'app-tenant-config-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <h3>{{ isEdit ? 'Edit Tenant Config' : 'New Tenant Config' }}</h3>

    <form *ngIf="form" [formGroup]="form" (ngSubmit)="save()">
      <div class="mb-3">
        <label>Tenant ID</label>
        <input class="form-control" formControlName="tenantId" />
      </div>

      <div class="mb-3">
        <label>Description</label>
        <input class="form-control" formControlName="description" />
      </div>

      <div class="mb-3">
        <label>Config (JSON)</label>
        <textarea class="form-control" rows="6" formControlName="config"></textarea>
      </div>

      <button class="btn btn-success me-2" type="submit" [disabled]="form.invalid">
        Save
      </button>
      <button class="btn btn-secondary" type="button" (click)="back()">Cancel</button>
    </form>
  `,
})
export class TenantConfigFormComponent implements OnInit {
  form!: FormGroup;
  isEdit = false;
  id?: number;

  constructor(
    private fb: FormBuilder,
    private service: TenantConfigService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // ✅ Initialize form after FormBuilder is available
    this.form = this.fb.group({
      tenantId: ['', Validators.required],
      description: [''],
      config: ['{}', Validators.required],
    });

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = +idParam;
      this.isEdit = true;
      this.service.getById(this.id).subscribe((data) => {
        this.form.patchValue({
          tenantId: data.tenantId,
          description: data.description,
          config: JSON.stringify(data.config, null, 2),
        });
      });
    }
  }

  save(): void {
    if (this.form.invalid) return;

    let parsedConfig: any;
    try {
      parsedConfig = JSON.parse(this.form.value.config!);
    } catch (e) {
      alert('❌ Invalid JSON format in Config field.');
      return;
    }

    const payload = {
      tenantId: this.form.value.tenantId!,
      description: this.form.value.description!,
      config: parsedConfig,
    };

    const request = this.isEdit && this.id
      ? this.service.update(this.id, payload)
      : this.service.create(payload);

    request.subscribe({
      next: () => {
        alert('Tenant configuration saved successfully.');
        this.back();
      },
      error: (err) => {
        console.error('Save failed:', err);
        alert('Failed to save tenant configuration.');
      },
    });
  }


  back(): void {
    this.router.navigate(['/tenants']);
  }
}
