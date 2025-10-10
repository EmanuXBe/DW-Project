import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ship } from '../model/ship'; 


@Injectable({
  providedIn: 'root'
})
export class ShipService {
  baseUrl = "http://localhost:8080";
  http = inject(HttpClient);

  findAll(): Observable<Ship[]> {
    return this.http.get<Ship[]>(`${this.baseUrl}/ship/search`);
  }
  
}
