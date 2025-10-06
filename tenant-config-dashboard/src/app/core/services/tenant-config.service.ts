import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface TenantConfig {
  id?: number;
  tenantId: string;
  description?: string;
  config: Record<string, any>;
}

@Injectable({ providedIn: 'root' })
export class TenantConfigService {
  private api = environment.apiBaseUrl;
  constructor(private http: HttpClient) {}

  getAll(): Observable<TenantConfig[]> {
    return this.http.get<TenantConfig[]>(`${this.api}/list`);
  }

  getById(id: number): Observable<TenantConfig> {
    return this.http.get<TenantConfig>(`${this.api}/${id}`);
  }

  create(payload: TenantConfig): Observable<TenantConfig> {
    return this.http.post<TenantConfig>(`${this.api}/create`, payload);
  }

  update(id: number, payload: TenantConfig): Observable<TenantConfig> {
    return this.http.put<TenantConfig>(`${this.api}/update/${id}`, payload);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/delete/${id}`);
  }
}
