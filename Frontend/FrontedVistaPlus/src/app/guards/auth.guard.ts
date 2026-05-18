import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {
  const router = inject(Router);

  if (typeof window !== 'undefined') {
    const token = localStorage.getItem('vistaplus_token');
    if (token) {
      return true;
    }
  } else {
    // Durante el SSR en el servidor, permitimos renderizar la estructura básica.
    // Una vez en el navegador, el cliente volverá a evaluar y redirigirá si no hay token.
    return true;
  }

  router.navigate(['/login']);
  return false;
};
