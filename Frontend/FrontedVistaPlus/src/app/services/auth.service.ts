import { Injectable, signal } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';

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

const API_BASE = 'http://localhost:8080/api';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'vistaplus_token';
  private readonly USER_KEY = 'vistaplus_user';

  readonly isLoggedIn = signal(false);
  readonly currentUser = signal<Usuario | null>(null);

  constructor(private http: HttpClient, private router: Router) {
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
   * Login — POST http://localhost:8080/api/auth/login
   * Espera body: { email, password }
   * Responde con: { token, usuario }
   */
  async login(email: string, password: string): Promise<{ success: boolean; error?: string }> {
    try {
      const response = await firstValueFrom(
        this.http.post<AuthResponse>(`${API_BASE}/auth/login`, { email, password })
      );
      this.handleAuthSuccess(response);
      return { success: true };
    } catch (err) {
      const error = err as HttpErrorResponse;
      const message =
        error.error?.message ||
        error.error?.error ||
        'Credenciales incorrectas. Inténtalo de nuevo.';
      return { success: false, error: message };
    }
  }

  /**
   * Register — POST http://localhost:8080/api/auth/register
   * Espera body: { nombre, email, password }
   * Responde con: { token, usuario }
   */
  async register(nombre: string, email: string, password: string): Promise<{ success: boolean; error?: string }> {
    try {
      const response = await firstValueFrom(
        this.http.post<AuthResponse>(`${API_BASE}/auth/register`, { nombre, email, password })
      );
      this.handleAuthSuccess(response);
      return { success: true };
    } catch (err) {
      const error = err as HttpErrorResponse;
      const message =
        error.error?.message ||
        error.error?.error ||
        'Error al registrarse. El email puede que ya esté en uso.';
      return { success: false, error: message };
    }
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
