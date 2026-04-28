import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';

export interface Usuario {
  id?: number;
  nombre: string;
  email: string;
  password?: string;
}

export interface AuthResponse {
  token: string;
  usuario: Usuario;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'vistaplus_token';
  private readonly USER_KEY = 'vistaplus_user';

  readonly isLoggedIn = signal(false);
  readonly currentUser = signal<Usuario | null>(null);

  constructor(private router: Router) {
    this.checkStoredSession();
  }

  private checkStoredSession(): void {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem(this.TOKEN_KEY);
      const userJson = localStorage.getItem(this.USER_KEY);
      if (token && userJson) {
        this.isLoggedIn.set(true);
        this.currentUser.set(JSON.parse(userJson));
      }
    }
  }

  /**
   * Login - Preparado para conectar con la API.
   * Sustituir el contenido de este método por la llamada HTTP real.
   */
  async login(email: string, password: string): Promise<{ success: boolean; error?: string }> {
    // TODO: Reemplazar con llamada HTTP a la API
    // Ejemplo:
    // return this.http.post<AuthResponse>('http://tu-api/auth/login', { email, password }).pipe(
    //   tap(res => this.handleAuthSuccess(res)),
    //   map(() => ({ success: true })),
    //   catchError(err => of({ success: false, error: err.error?.message || 'Error al iniciar sesión' }))
    // ).toPromise();

    // Simulación temporal para desarrollo
    return new Promise((resolve) => {
      setTimeout(() => {
        // Simular login exitoso
        const fakeResponse: AuthResponse = {
          token: 'fake-jwt-token-' + Date.now(),
          usuario: { id: 1, nombre: email.split('@')[0], email }
        };
        this.handleAuthSuccess(fakeResponse);
        resolve({ success: true });
      }, 800);
    });
  }

  /**
   * Register - Preparado para conectar con la API.
   * Sustituir el contenido de este método por la llamada HTTP real.
   */
  async register(nombre: string, email: string, password: string): Promise<{ success: boolean; error?: string }> {
    // TODO: Reemplazar con llamada HTTP a la API
    // Ejemplo:
    // return this.http.post<AuthResponse>('http://tu-api/auth/register', { nombre, email, password }).pipe(
    //   tap(res => this.handleAuthSuccess(res)),
    //   map(() => ({ success: true })),
    //   catchError(err => of({ success: false, error: err.error?.message || 'Error al registrarse' }))
    // ).toPromise();

    // Simulación temporal para desarrollo
    return new Promise((resolve) => {
      setTimeout(() => {
        const fakeResponse: AuthResponse = {
          token: 'fake-jwt-token-' + Date.now(),
          usuario: { id: 1, nombre, email }
        };
        this.handleAuthSuccess(fakeResponse);
        resolve({ success: true });
      }, 800);
    });
  }

  private handleAuthSuccess(response: AuthResponse): void {
    if (typeof window !== 'undefined') {
      localStorage.setItem(this.TOKEN_KEY, response.token);
      localStorage.setItem(this.USER_KEY, JSON.stringify(response.usuario));
    }
    this.isLoggedIn.set(true);
    this.currentUser.set(response.usuario);
  }

  logout(): void {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.USER_KEY);
    }
    this.isLoggedIn.set(false);
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    if (typeof window !== 'undefined') {
      return localStorage.getItem(this.TOKEN_KEY);
    }
    return null;
  }
}
