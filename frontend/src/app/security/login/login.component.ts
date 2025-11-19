import { Component, inject, signal, OnInit } from '@angular/core';
import { LoginDto } from '../../dto/login-dto';
import { AuthService } from '../../shared/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  loginDto = signal(new LoginDto('', ''));
  errorMessage = signal('');

  private auth = inject(AuthService);
  private router = inject(Router);

  ngOnInit(): void {
    // Limpiar completamente sessionStorage para evitar datos corruptos
    sessionStorage.clear();
    this.auth.logout();
  }

  login() {
    this.errorMessage.set('');
    this.auth.login(this.loginDto()).subscribe({
      next: (jwt) => {
        console.log('Login successful:', jwt);
        // Redirigir según el rol del usuario
        if (jwt.role === 'admin') {
          this.router.navigate(['/users']);
        } else {
          this.router.navigate(['/game-setup']);
        }
      },
      error: (err) => {
        console.error('Login failed:', err);
        this.errorMessage.set('Usuario o contraseña incorrectos');
      },
    });
  }
}
