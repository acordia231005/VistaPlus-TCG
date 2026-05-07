import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Para evitar dependencias circulares, leemos el token directamente de localStorage
  let token = null;
  if (typeof window !== 'undefined') {
    token = localStorage.getItem('vistaplus_token');
  }

  // Clonar la petición y añadir el header Authorization si hay token
  const authReq = token
    ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
    : req;

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      // Si el servidor responde 401 (no autorizado), podríamos redirigir a login
      // Pero no inyectamos AuthService aquí para evitar el bucle infinito
      if (error.status === 401 && typeof window !== 'undefined') {
        localStorage.removeItem('vistaplus_token');
        localStorage.removeItem('vistaplus_username');
        window.location.href = '/login';
      }
      return throwError(() => error);
    })
  );
};
