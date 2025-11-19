import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LoginDto } from '../dto/login-dto';
import { map, Observable } from 'rxjs';
import { JwtAuthenticationResponse } from '../dto/jwt-authentication-response';
import { environment } from '../../environments/environment';

const JWT_TOKEN = 'jwt-token';
const USERNAME = 'user-name';
const ROLE = 'user-role';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  http = inject(HttpClient);

  login(loginDto: LoginDto): Observable<JwtAuthenticationResponse> {
    return this.http
      .post<JwtAuthenticationResponse>(`${environment.serverUrl}/auth/login`, loginDto)
      .pipe(
        map((jwt) => {
          // Importante: https://stackoverflow.com/questions/27067251/where-to-store-jwt-in-browser-how-to-protect-against-csrf
          sessionStorage.setItem(JWT_TOKEN, jwt.token); //localStorage
          sessionStorage.setItem(USERNAME, jwt.username);
          sessionStorage.setItem(ROLE, jwt.role);
          return jwt;
        })
      );
  }

  logout() {
    sessionStorage.removeItem(JWT_TOKEN);
    sessionStorage.removeItem(USERNAME);
    sessionStorage.removeItem(ROLE);
  }

  isAuthenticated() {
    return sessionStorage.getItem(JWT_TOKEN) != null;
  }

  token() {
    return sessionStorage.getItem(JWT_TOKEN);
  }

  username() {
    return sessionStorage.getItem(USERNAME);
  }

  role() {
    return sessionStorage.getItem(ROLE);
  }

  isAdmin() {
    return this.role() === 'admin';
  }
}
