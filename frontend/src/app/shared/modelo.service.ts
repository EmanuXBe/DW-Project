import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Model } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class ModeloService {
  baseUrl = "http://localhost:8080";
  http = inject(HttpClient);

  findAll(): Observable<Model[]> {
    return this.http.get<Model[]>(`${this.baseUrl}/model/search`);
  }
}
