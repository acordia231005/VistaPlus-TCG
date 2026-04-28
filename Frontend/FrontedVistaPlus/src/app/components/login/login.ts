import { Component, signal } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  email = '';
  password = '';
  loading = signal(false);
  error = signal('');
  showPassword = signal(false);

  constructor(private authService: AuthService, private router: Router) {}

  togglePassword(): void {
    this.showPassword.update(v => !v);
  }

  async onSubmit(): Promise<void> {
    this.error.set('');

    if (!this.email.trim() || !this.password.trim()) {
      this.error.set('Por favor, completa todos los campos.');
      return;
    }

    this.loading.set(true);

    try {
      const result = await this.authService.login(this.email, this.password);
      if (result.success) {
        this.router.navigate(['/']);
      } else {
        this.error.set(result.error || 'Error al iniciar sesión.');
      }
    } catch {
      this.error.set('Error de conexión. Inténtalo de nuevo.');
    } finally {
      this.loading.set(false);
    }
  }
}
