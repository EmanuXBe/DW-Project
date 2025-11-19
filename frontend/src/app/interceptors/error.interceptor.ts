import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error('HTTP Error intercepted:', error);

      const isLoginRequest = req.url.includes('/auth/login');

      if (!isLoginRequest) {
        // Mostrar mensaje específico para errores de permisos
        if (error.status === 403) {
          alert('Acceso denegado: No tienes permisos para realizar esta operación.');
        } else if (error.status === 401) {
          alert('No autenticado: Por favor inicia sesión.');
        }
      }

      return throwError(() => error);
    })
  );
};
