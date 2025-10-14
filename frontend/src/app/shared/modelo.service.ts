import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Model } from '../model/model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ModeloService {
  private readonly baseUrl = environment.apiUrl;
  http = inject(HttpClient);

  findAll(): Observable<Model[]> {
    return this.http.get<Model[]>(`${this.baseUrl}/model`);
  }

  findById(id: number): Observable<Model> {
    return this.http.get<Model>(`${this.baseUrl}/model/${id}`);
  }

  create(model: Model): Observable<Model> {
    return this.http.post<Model>(`${this.baseUrl}/model`, model);
  }

  update(id: number, model: Model): Observable<Model> {
    return this.http.put<Model>(`${this.baseUrl}/model/${id}`, model);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/model/${id}`);
  }

  search(query: string): Observable<Model[]> {
    return this.http.get<Model[]>(`${this.baseUrl}/model/search?searchText=${query}`);
  }
}
