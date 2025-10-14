import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ship } from '../model/ship';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ShipService {
  private readonly baseUrl = environment.apiUrl;
  http = inject(HttpClient);

  findAll(): Observable<Ship[]> {
    return this.http.get<Ship[]>(`${this.baseUrl}/ship`);
  }

  findById(id: number): Observable<Ship> {
    return this.http.get<Ship>(`${this.baseUrl}/ship/${id}`);
  }

  create(ship: Ship): Observable<Ship> {
    return this.http.post<Ship>(`${this.baseUrl}/ship`, ship);
  }

  updateShip(id: number, ship: Ship): Observable<Ship> {
    return this.http.put<Ship>(`${this.baseUrl}/ship/${id}`, ship, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }

  deleteShip(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/ship/${id}`);
  }

  search(query: string): Observable<Ship[]> {
    return this.http.get<Ship[]>(`${this.baseUrl}/ship/search?searchText=${query}`);
  }
}
