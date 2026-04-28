import { Component, signal } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  imports: [RouterLink, FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  nombre = '';
  email = '';
  password = '';
  confirmPassword = '';
  loading = signal(false);
  error = signal('');
  showPassword = signal(false);
  showConfirmPassword = signal(false);

  constructor(private authService: AuthService, private router: Router) {}

  togglePassword(): void {
    this.showPassword.update(v => !v);
  }

  toggleConfirmPassword(): void {
    this.showConfirmPassword.update(v => !v);
  }

  get passwordStrength(): number {
    const p = this.password;
    if (!p) return 0;
    let score = 0;
    if (p.length >= 6) score++;
    if (p.length >= 10) score++;
    if (/[A-Z]/.test(p)) score++;
    if (/[0-9]/.test(p)) score++;
    if (/[^A-Za-z0-9]/.test(p)) score++;
    return Math.min(score, 5);
  }

  get strengthLabel(): string {
    const labels = ['', 'Muy débil', 'Débil', 'Media', 'Fuerte', 'Muy fuerte'];
    return labels[this.passwordStrength];
  }

  get strengthColor(): string {
    const colors = ['', '#ef4444', '#f97316', '#eab308', '#22c55e', '#10b981'];
    return colors[this.passwordStrength];
  }

  async onSubmit(): Promise<void> {
    this.error.set('');

    if (!this.nombre.trim() || !this.email.trim() || !this.password.trim() || !this.confirmPassword.trim()) {
      this.error.set('Por favor, completa todos los campos.');
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.error.set('Las contraseñas no coinciden.');
      return;
    }

    if (this.password.length < 6) {
      this.error.set('La contraseña debe tener al menos 6 caracteres.');
      return;
    }

    this.loading.set(true);

    try {
      const result = await this.authService.register(this.nombre, this.email, this.password);
      if (result.success) {
        this.router.navigate(['/']);
      } else {
        this.error.set(result.error || 'Error al registrarse.');
      }
    } catch {
      this.error.set('Error de conexión. Inténtalo de nuevo.');
    } finally {
      this.loading.set(false);
    }
  }
}
