import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ship, MoveDTO, PossibleMovesDTO } from '../model/ship';
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

  // ============= GAME METHODS =============

  startRace(shipId: number, boardId: number, startX: number, startY: number): Observable<Ship> {
    const params = new HttpParams()
      .set('boardId', boardId.toString())
      .set('startX', startX.toString())
      .set('startY', startY.toString());

    return this.http.post<Ship>(`${this.baseUrl}/ship/${shipId}/start-race`, null, { params });
  }

  getPossibleMoves(shipId: number, boardId: number): Observable<PossibleMovesDTO> {
    const params = new HttpParams().set('boardId', boardId.toString());
    return this.http.get<PossibleMovesDTO>(`${this.baseUrl}/ship/${shipId}/possible-moves`, {
      params,
    });
  }

  executeMove(shipId: number, boardId: number, move: MoveDTO): Observable<Ship> {
    const params = new HttpParams().set('boardId', boardId.toString());
    return this.http.post<Ship>(`${this.baseUrl}/ship/${shipId}/move`, move, { params });
  }
}
