import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl = "http://localhost:8080";
  http = inject(HttpClient);

  findAll(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/user/search`);
  }
  
}
