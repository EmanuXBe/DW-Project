import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Board } from '../model/board';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class BoardService {
  private readonly baseUrl = environment.apiUrl;
  http = inject(HttpClient);

  findAll(): Observable<Board[]> {
    return this.http.get<Board[]>(`${this.baseUrl}/board/list`);
  }

  findById(id: number): Observable<Board> {
    return this.http.get<Board>(`${this.baseUrl}/board/view/${id}`);
  }

  create(board: Board): Observable<Board> {
    return this.http.post<Board>(`${this.baseUrl}/board`, board);
  }

  update(board: Board): Observable<Board> {
    return this.http.put<Board>(`${this.baseUrl}/board`, board);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/board/${id}`);
  }

  generateDefaultMap(width: number = 15, height: number = 11): Observable<Board> {
    return this.http.post<Board>(
      `${this.baseUrl}/board/generate?width=${width}&height=${height}`,
      {}
    );
  }
}
