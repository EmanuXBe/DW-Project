import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly baseUrl = environment.apiUrl;
  http = inject(HttpClient);

  findAll(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/user/list`);
  }

  findById(id: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/user/view/${id}`);
  }

  create(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/user`, user);
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/user`, user);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/user/${id}`);
  }

  search(query: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/user/search?searchText=${query}`);
  }
}
