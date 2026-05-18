import { Injectable, signal } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../environments/environment';

// Contrato real del backend: POST /auth/login → { access, refresh }
export interface LoginResponse {
  access: string;
  refresh: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  nacionalidad?: string;
  fechaNac?: string;
  rol?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'vistaplus_token';
  private readonly REFRESH_KEY = 'vistaplus_refresh';
  private readonly USERNAME_KEY = 'vistaplus_username';
  private readonly apiUrl = 'http://localhost:8080';

  readonly isLoggedIn = signal(false);
  readonly currentUsername = signal<string | null>(null);
  readonly currentUser = signal<User | null>(null);

  constructor(private http: HttpClient, private router: Router) {
    this.checkStoredSession();
  }

  private async checkStoredSession(): Promise<void> {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem(this.TOKEN_KEY);
      const username = localStorage.getItem(this.USERNAME_KEY);
      if (token) {
        this.isLoggedIn.set(true);
        if (username) {
          this.currentUsername.set(username);
          this.fetchUserProfile(username);
        }
      }
    }
  }

  async fetchUserProfile(username: string): Promise<void> {
    try {
      const user = await firstValueFrom(
        this.http.get<User>(`${this.apiUrl}/usuario/username/${username}`)
      );
      console.log('Perfil de usuario recuperado:', user);
      this.currentUser.set(user);
    } catch (err) {
      console.error('Error fetching user profile', err);
    }
  }

  /**
   * Login — POST http://localhost:8080/auth/login
   * Body: { username, password }
   * Responde con: { access, refresh }
   */
  async login(username: string, password: string): Promise<{ success: boolean; error?: string }> {
    try {
      const response = await firstValueFrom(
        this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, { username, password })
      );
      this.handleAuthSuccess(response, username);
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
   * Register — POST http://localhost:8080/auth/register
   * Body: { username, email, password, rol, nacionalidad, fechaNac }
   * Responde con token en header Authorization
   */
  async register(username: string, email: string, password: string, rol: string = 'USER', nacionalidad: string = '', fechaNac: string = ''): Promise<{ success: boolean; error?: string }> {
    try {
      const response = await firstValueFrom(
        this.http.post<LoginResponse>(`${this.apiUrl}/auth/register`, { username, email, password, rol, nacionalidad, fechaNac }, { observe: 'response' })
      );
      // El register puede devolver el token en el header o en el body
      const token = response.headers.get('Authorization')?.replace('Bearer ', '')
                    || response.body?.access;
      if (token) {
        localStorage.setItem(this.TOKEN_KEY, token);
        localStorage.setItem(this.USERNAME_KEY, username);
        this.isLoggedIn.set(true);
        this.currentUsername.set(username);
        this.fetchUserProfile(username);
      }
      return { success: true };
    } catch (err) {
      const errorResponse = err as HttpErrorResponse;
      console.error('Error en registro:', errorResponse);
      
      let mensaje = 'Error al registrarse. El usuario puede que ya exista.';
      if (typeof errorResponse.error === 'string') {
        mensaje = errorResponse.error;
      } else if (errorResponse.error?.message) {
        mensaje = errorResponse.error.message;
      }
      
      return { success: false, error: mensaje };
    }
  }

  private handleAuthSuccess(response: LoginResponse, username: string): void {
    if (typeof window !== 'undefined') {
      localStorage.setItem(this.TOKEN_KEY, response.access);
      localStorage.setItem(this.REFRESH_KEY, response.refresh);
      localStorage.setItem(this.USERNAME_KEY, username);
    }
    this.isLoggedIn.set(true);
    this.currentUsername.set(username);
    this.fetchUserProfile(username);
  }

  logout(): void {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.REFRESH_KEY);
      localStorage.removeItem(this.USERNAME_KEY);
    }
    this.isLoggedIn.set(false);
    this.currentUsername.set(null);
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
